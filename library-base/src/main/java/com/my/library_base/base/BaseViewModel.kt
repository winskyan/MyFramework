package com.my.library_base.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.my.library_base.base.inf.IBaseViewModel
import io.reactivex.disposables.Disposable

open class BaseViewModel<M : BaseModel?> : AndroidViewModel, IBaseViewModel {
    var model: M? = null
        protected set

    constructor(application: Application) : super(application) {}
    constructor(application: Application, model: M) : super(application) {
        this.model = model
    }

    protected fun addSubscribe(disposable: Disposable?) {
        model!!.addSubscribe(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        model?.onCleared()
    }

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {}
    override fun onCreate() {}
    override fun onDestroy() {}
    override fun onStart() {}
    override fun onStop() {}
    override fun onResume() {}
    override fun onPause() {}
    override fun registerRxBus() {}
    override fun removeRxBus() {}
}