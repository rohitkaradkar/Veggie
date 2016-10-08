package com.greentopli.core.presenter.base;

/**
 * Created by rnztx on 8/10/16.
 */

public class BasePresenter<T extends MvpView> implements Presenter<T> {
	private T mMvpView;
	@Override
	public void attachView(T mvpView) {
		this.mMvpView=mvpView;
	}

	@Override
	public void detachView() {
		this.mMvpView=null;
	}

	public boolean isViewAttached(){
		return this.mMvpView!=null;
	}
	public T getmMvpView(){
		return this.mMvpView;
	}
}
