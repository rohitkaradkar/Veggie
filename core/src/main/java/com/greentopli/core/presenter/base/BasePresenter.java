package com.greentopli.core.presenter.base;

import android.content.Context;

/**
 * Created by rnztx on 8/10/16.
 */

public class BasePresenter<T extends MvpView> implements Presenter<T> {
	private T mMvpView;
	private Context context;

	@Override
	public void attachView(T mvpView, Context context) {
		this.mMvpView = mvpView;
		this.context = context;
	}

	@Override
	public void detachView() {
		this.mMvpView = null;
		this.context = null;
	}

	public boolean isViewAttached() {
		return this.mMvpView != null;
	}

	public T getmMvpView() {
		return this.mMvpView;
	}

	public Context getContext() {
		return context;
	}
}
