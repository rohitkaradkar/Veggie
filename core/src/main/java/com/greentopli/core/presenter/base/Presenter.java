package com.greentopli.core.presenter.base;

/**
 * Created by rnztx on 8/10/16.
 */

public interface Presenter<V extends MvpView> {
	public void attachView(V mvpView);
	public void detachView();
}
