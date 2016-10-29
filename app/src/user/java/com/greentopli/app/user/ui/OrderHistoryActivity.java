package com.greentopli.app.user.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.greentopli.app.R;
import com.greentopli.app.user.OrderHistoryAdapter;
import com.greentopli.core.presenter.OrderHistoryPresenter;
import com.greentopli.core.presenter.OrderHistoryView;
import com.greentopli.model.OrderHistory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryActivity extends AppCompatActivity implements OrderHistoryView {
	@BindView(R.id.orderHistory_recyclerView) RecyclerView mRecyclerView;
	@BindView(R.id.orderHistory_empty_message) TextView emptyMessage;
	private OrderHistoryPresenter mPresenter;
	private LinearLayoutManager mLayoutManager;
	private OrderHistoryAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_history);
		ButterKnife.bind(this);
		mAdapter = new OrderHistoryAdapter();
		mLayoutManager = new LinearLayoutManager(getApplicationContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setAdapter(mAdapter);
		mPresenter = OrderHistoryPresenter.bind(this,getApplicationContext());
		mPresenter.requestOrderHistory();
	}

	@Override
	public void onDataReceived(List<OrderHistory> orderHistoryList) {
		mAdapter.addNewData(orderHistoryList);
		onEmpty(false);
	}

	@Override
	public void onEmpty(boolean show) {
		emptyMessage.setVisibility(show? View.VISIBLE:View.GONE);
	}

	@Override
	public void showProgressbar(boolean show) {

	}
}
