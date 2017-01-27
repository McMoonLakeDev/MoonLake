/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.value.ChangeListener;
import com.minecraft.moonlake.value.InvalidationListener;
import com.minecraft.moonlake.value.ObservableValue;

import java.util.Arrays;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class ExpressionHelper<T> extends ExpressionHelperBase {

    public static <T> ExpressionHelper<T> addListener(ExpressionHelper<T> helper, ObservableValue<T> observable, InvalidationListener listener) {

        if(observable == null || listener == null) {

            throw new NullPointerException();
        }
        observable.getValue(); // validate observable
        return (helper == null) ? new SingleInvalidation<T>(observable, listener) : helper.addListener(listener);
    }

    public static <T> ExpressionHelper<T> removeListener(ExpressionHelper<T> helper, InvalidationListener listener) {

        if(listener == null) {

            throw new NullPointerException();
        }
        return (helper == null) ? null : helper.removeListener(listener);
    }

    public static <T> ExpressionHelper<T> addListener(ExpressionHelper<T> helper, ObservableValue<T> observable, ChangeListener<? super T> listener) {

        if(listener == null) {

            throw new NullPointerException();
        }
        return (helper == null) ? new SingleChange<T>(observable, listener) : helper.addListener(listener);
    }

    public static <T> ExpressionHelper<T> removeListener(ExpressionHelper<T> helper, ChangeListener<? super T> listener) {

        if(listener == null) {

            throw new NullPointerException();
        }
        return (helper == null) ? null : helper.removeListener(listener);
    }

    public static <T> void fireValueChangedEvent(ExpressionHelper<T> helper) {

        if(helper != null) {

            helper.fireValueChangedEvent();
        }
    }

    protected final ObservableValue<T> observable;

    private ExpressionHelper(ObservableValue<T> observableValue) {

        this.observable = observableValue;
    }

    protected abstract ExpressionHelper<T> addListener(InvalidationListener listener);
    protected abstract ExpressionHelper<T> removeListener(InvalidationListener listener);

    protected abstract ExpressionHelper<T> addListener(ChangeListener<? super T> listener);
    protected abstract ExpressionHelper<T> removeListener(ChangeListener<? super T> listener);

    protected abstract void fireValueChangedEvent();

    private static class SingleInvalidation<T> extends ExpressionHelper<T> {

        private final InvalidationListener listener;

        public SingleInvalidation(ObservableValue<T> expression, InvalidationListener listener) {

            super(expression);

            this.listener = listener;
        }

        @Override
        protected ExpressionHelper<T> addListener(InvalidationListener listener) {

            return new Generic<T>(observable, this.listener, listener);
        }

        @Override
        protected ExpressionHelper<T> removeListener(InvalidationListener listener) {

            return listener.equals(this.listener) ? null : this;
        }

        @Override
        protected ExpressionHelper<T> addListener(ChangeListener<? super T> listener) {

            return new Generic<T>(observable, this.listener, listener);
        }

        @Override
        protected ExpressionHelper<T> removeListener(ChangeListener<? super T> listener) {

            return this;
        }

        @Override
        protected void fireValueChangedEvent() {

            try {

                listener.invalidated(observable);
            }
            catch (Exception e) {

                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
            }
        }
    }

    private static class SingleChange<T> extends ExpressionHelper<T> {

        private final ChangeListener<? super T> listener;
        private T currentValue;

        public SingleChange(ObservableValue<T> observable, ChangeListener<? super T> listener) {

            super(observable);

            this.listener = listener;
            this.currentValue = observable.getValue();
        }

        @Override
        protected ExpressionHelper<T> addListener(InvalidationListener listener) {

            return new Generic<T>(observable, listener, this.listener);
        }

        @Override
        protected ExpressionHelper<T> removeListener(InvalidationListener listener) {

            return this;
        }

        @Override
        protected ExpressionHelper<T> addListener(ChangeListener<? super T> listener) {

            return new Generic<T>(observable, this.listener, listener);
        }

        @Override
        protected ExpressionHelper<T> removeListener(ChangeListener<? super T> listener) {

            return (listener.equals(this.listener)) ? null : this;
        }

        @Override
        protected void fireValueChangedEvent() {

            final T oldValue = currentValue;
            currentValue = observable.getValue();
            final boolean changed = (currentValue == null) ? (oldValue != null) : !currentValue.equals(oldValue);

            if(changed) {

                try {

                    listener.changed(observable, oldValue, currentValue);
                }
                catch (Exception e) {

                    Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        }
    }

    private static class Generic<T> extends ExpressionHelper<T> {

        private InvalidationListener[] invalidationListeners;
        private ChangeListener<? super T>[] changeListeners;
        private int invalidationSize;
        private int changeSize;
        private boolean locked;
        private T currentValue;

        private Generic(ObservableValue<T> observable, InvalidationListener listener0, InvalidationListener listener1) {

            super(observable);

            this.invalidationListeners = new InvalidationListener[] { listener0, listener1 };
            this.invalidationSize = 2;
        }

        private Generic(ObservableValue<T> observableValue, InvalidationListener invalidationListener, ChangeListener<? super T> changeListener) {

            super(observableValue);

            this.invalidationListeners = new InvalidationListener[] { invalidationListener };
            this.invalidationSize = 1;
            this.currentValue = observableValue.getValue();
            this.changeListeners = new ChangeListener[] { changeListener };
            this.changeSize = 1;
        }

        private Generic(ObservableValue<T> observableValue, ChangeListener<? super T> listener0, ChangeListener<? super T> listener1) {

            super(observableValue);

            this.currentValue = observableValue.getValue();
            this.changeListeners = new ChangeListener[] { listener0, listener1 };
            this.changeSize = 2;
        }

        @Override
        protected ExpressionHelper<T> addListener(InvalidationListener listener) {

            if(invalidationListeners == null) {

                invalidationListeners = new InvalidationListener[] { listener };
                invalidationSize = 1;
            }
            else {

                final int oldCapacity = invalidationListeners.length;

                if(locked) {

                    final int newCapacity = (invalidationSize < oldCapacity) ? oldCapacity : (oldCapacity * 3) / 2 + 1;
                    invalidationListeners = Arrays.copyOf(invalidationListeners, newCapacity);
                }
                else if(invalidationSize == oldCapacity) {

                    invalidationSize = trim(invalidationSize, invalidationListeners);

                    if(invalidationSize == oldCapacity) {

                        final int newCapacity = (oldCapacity * 3 ) / 2 + 1;
                        invalidationListeners = Arrays.copyOf(invalidationListeners, newCapacity);
                    }
                }
                invalidationListeners[invalidationSize++] = listener;
            }
            return this;
        }

        @Override
        protected ExpressionHelper<T> removeListener(InvalidationListener listener) {

            if (invalidationListeners != null) {

                for (int index = 0; index < invalidationSize; index++) {

                    if (listener.equals(invalidationListeners[index])) {

                        if (invalidationSize == 1) {

                            if (changeSize == 1) {

                                return new SingleChange<T>(observable, changeListeners[0]);
                            }
                            invalidationListeners = null;
                            invalidationSize = 0;
                        }
                        else if (invalidationSize == 2 && changeSize == 0) {

                            return new SingleInvalidation<T>(observable, invalidationListeners[1-index]);
                        }
                        else {

                            final int numMoved = invalidationSize - index - 1;
                            final InvalidationListener[] oldListeners = invalidationListeners;

                            if (locked) {

                                invalidationListeners = new InvalidationListener[invalidationListeners.length];
                                System.arraycopy(oldListeners, 0, invalidationListeners, 0, index);
                            }
                            if (numMoved > 0) {

                                System.arraycopy(oldListeners, index+1, invalidationListeners, index, numMoved);
                            }
                            invalidationSize--;

                            if (!locked) {

                                invalidationListeners[invalidationSize] = null;     // gc listener
                            }
                        }
                        break;
                    }
                }
            }
            return this;
        }

        @Override
        protected ExpressionHelper<T> addListener(ChangeListener<? super T> listener) {

            if(changeListeners == null) {

                changeListeners = new ChangeListener[] { listener };
                changeSize = 1;
            }
            else {

                final int oldCapacity = changeListeners.length;

                if(locked) {

                    final int newCapacity = (changeSize < oldCapacity) ? oldCapacity : (oldCapacity * 3) / 2 + 1;
                    changeListeners = Arrays.copyOf(changeListeners, newCapacity);
                }
                else if(changeSize == oldCapacity) {

                    changeSize = trim(changeSize, changeListeners);

                    if(changeSize == oldCapacity) {

                        final int newCapacity = (oldCapacity * 3) / 2 + 1;
                        changeListeners = Arrays.copyOf(changeListeners, newCapacity);
                    }
                }
                changeListeners[changeSize++] = listener;
            }
            if(changeSize == 1) {

                currentValue = observable.getValue();
            }
            return this;
        }

        @Override
        protected ExpressionHelper<T> removeListener(ChangeListener<? super T> listener) {

            if(changeListeners != null) {

                for(int index = 0; index < changeSize; index++) {

                    if(listener.equals(changeListeners[index])) {

                        if(changeSize == 1) {

                            if(invalidationSize == 1) {

                                return new SingleInvalidation<T>(observable, invalidationListeners[0]);
                            }
                            changeListeners = null;
                            changeSize = 0;
                        }
                        else if(changeSize == 2 && invalidationSize == 0) {

                            return new SingleChange<T>(observable, changeListeners[1 - index]);
                        }
                        else {

                            final int numMoved = changeSize - index - 1;
                            final ChangeListener<? super T>[] oldListeners = changeListeners;

                            if(locked) {

                                changeListeners = new ChangeListener[changeListeners.length];
                                System.arraycopy(oldListeners, 0, changeListeners, 0, index);
                            }
                            if(numMoved > 0) {

                                System.arraycopy(oldListeners, index + 1, changeListeners, index, numMoved);
                            }
                            changeSize--;

                            if(!locked) {

                                changeListeners[changeSize] = null;     // gc listener
                            }
                        }
                        break;
                    }
                }
            }
            return this;
        }

        @Override
        protected void fireValueChangedEvent() {

            final ChangeListener<? super T>[] curChangeList = changeListeners;
            final int curChangeSize = changeSize;

            try {

                locked = true;

                if(curChangeSize > 0) {

                    final T oldValue = currentValue;
                    currentValue = observable.getValue();
                    final boolean changed = (currentValue == null) ? (oldValue != null) : !currentValue.equals(oldValue);

                    if(changed) {

                        for(int i = 0; i < curChangeSize; i++) {

                            try {

                                curChangeList[i].changed(observable, oldValue, currentValue);
                            }
                            catch (Exception e) {

                                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                            }
                        }
                    }
                }
            }
            finally {

                locked = false;
            }
        }
    }
}
