package com.minecraft.moonlake.value;

import com.minecraft.moonlake.binding.Observable;
import com.minecraft.moonlake.binding.WeakListener;

import java.lang.ref.WeakReference;

/**
 * Created by MoonLake on 2016/9/11.
 */
public final class WeakInvalidationListener implements InvalidationListener, WeakListener {

    private final WeakReference<InvalidationListener> ref;

    public WeakInvalidationListener(InvalidationListener listener) {

        if(listener == null) {

            throw new NullPointerException("The invalidation listener is null.");
        }
        this.ref = new WeakReference<>(listener);
    }

    @Override
    public boolean wasGarbageCollected() {

        return ref.get() == null;
    }

    @Override
    public void invalidated(Observable observable) {

        InvalidationListener listener = ref.get();

        if(listener != null) {

            listener.invalidated(observable);
        }
        else {

            observable.removeListener(this);    // gc listener
        }
    }
}
