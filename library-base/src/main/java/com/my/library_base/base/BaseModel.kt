package com.my.library_base.base

import com.my.library_base.base.inf.IModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseModel : IModel {
    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    private var mCompositeDisposable: CompositeDisposable?
    fun addSubscribe(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable!!)
    }

    override fun onCleared() {
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
        }
    }

    init {
        mCompositeDisposable = CompositeDisposable()
    }
}