package com.goulala.xiayun.common.mvp;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.goulala.xiayun.common.base.ApiService;
import com.goulala.xiayun.common.retrofit.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Presenter的基类
 */
public class BasePresenter<V> implements Presenter<V> {

    public V mvpView;
    protected Activity context;
    protected ApiService apiService;
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(V mvpView) {
        attachView(mvpView);
    }

    //RxJava取消注册，以避免内存泄露
    public void onUnSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public void attachView(V view) {
        this.mvpView = view;
        apiService = RetrofitFactory.getInstance();
        if (mvpView instanceof Activity) {
            this.context = (Activity) mvpView;
        } else if (mvpView instanceof Fragment) {
            //lazyLoad会先调用，这是Fragment还没初始化完成,getActivity是null
            context = ((Fragment) mvpView).getActivity();
        }
    }

    @Override
    public void detachView() {
        this.mvpView = null;
        onUnSubscribe();
    }


    public void addDisposableObserver(Observable observable, DisposableObserver observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observer);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
