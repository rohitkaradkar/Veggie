package com.greentopli.core.presenter;

import com.greentopli.core.presenter.base.MvpView;
import com.greentopli.model.ProductInfo;

import java.util.List;

/**
 * Created by rnztx on 13/10/16.
 */

public interface BrowseProductsView extends MvpView{
	void showEmpty();
	void showError(String message);
	void showProducts(List<ProductInfo> list);
}
