package com.goulala.xiayun.common.mvp;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
