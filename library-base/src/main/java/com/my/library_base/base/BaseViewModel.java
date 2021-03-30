package com.my.library_base.base;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.my.library_base.base.inf.IBaseViewModel;


import io.reactivex.disposables.Disposable;

public class BaseViewModel<M extends BaseModel> extends AndroidViewModel implements IBaseViewModel {
    protected M model;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        this.model = model;
    }

    protected void addSubscribe(Disposable disposable) {
        model.addSubscribe(disposable);
    }

    public M getModel() {
        return model;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.onCleared();
        }
    }


    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void removeRxBus() {

    }
}
