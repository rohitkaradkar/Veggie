package com.greentopli.core.presenter.history;

import com.greentopli.core.presenter.base.MvpView;
import com.greentopli.model.OrderHistory;
import com.greentopli.model.Product;

import java.util.List;

/**
 * Created by rnztx on 28/10/16.
 */

public interface OrderHistoryView extends MvpView{
	void onHistoryReceived(List<OrderHistory> orderHistoryList);
	void onEmpty(boolean show);
}
