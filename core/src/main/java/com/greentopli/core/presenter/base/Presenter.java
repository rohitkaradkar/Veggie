package com.greentopli.core.presenter.base;

import android.content.Context;

/**
 * Created by rnztx on 8/10/16.
 */

public interface Presenter<V extends MvpView> {
	void attachView(V mvpView, Context context);
	void detachView();
}
