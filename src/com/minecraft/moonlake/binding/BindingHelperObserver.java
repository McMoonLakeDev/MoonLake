package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.api.Observable;
import com.minecraft.moonlake.value.InvalidationListener;

import java.lang.ref.WeakReference;

/**
 * Created by MoonLake on 2016/9/11.
 */
public class BindingHelperObserver implements InvalidationListener {

    private final WeakReference<Binding<?>> ref;

    public BindingHelperObserver(Binding<?> binding) {

        if(binding == null) {

            throw new NullPointerException("The binding is null");
        }
        this.ref = new WeakReference<Binding<?>>(binding);
    }

    /**
     * 监听该属性的值验证时触发
     *
     * @param observable 观察者
     */
    @Override
    public void invalidated(Observable observable) {

        final Binding<?> binding = ref.get();

        if(binding == null) {

            observable.removeListener(this);
        }
        else {

            binding.invalidate();
        }
    }
}
