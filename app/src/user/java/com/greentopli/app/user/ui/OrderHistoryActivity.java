package com.greentopli.app.user.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.greentopli.app.AuthenticatorActivity;

import com.greentopli.app.R;
import com.greentopli.app.user.tool.ListItemDecoration;
import com.greentopli.app.user.adapter.OrderHistoryAdapter;
import com.greentopli.core.storage.helper.UserDbHelper;
import com.greentopli.core.presenter.history.OrderHistoryPresenter;
import com.greentopli.core.presenter.history.OrderHistoryView;
import com.greentopli.model.OrderHistory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryActivity extends AppCompatActivity implements OrderHistoryView {
	@BindView(R.id.orderHistory_recyclerView) RecyclerView mRecyclerView;
	@BindView(R.id.orderHistory_empty_message) TextView emptyMessage;
	@BindView(R.id.progressbar_orderHistory_activity) ProgressBar progressBar;
	private OrderHistoryPresenter mPresenter;
	private LinearLayoutManager mLayoutManager;
	private OrderHistoryAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (new UserDbHelper(getApplicationContext()).getSignedUserInfo()==null){
			// user in not logged
			Intent signInIntent = new Intent(getApplicationContext(),AuthenticatorActivity.class);
			startActivityForResult(signInIntent,1000);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_history);
		ButterKnife.bind(this);
		mPresenter = OrderHistoryPresenter.bind(this,getApplicationContext());
		mRecyclerView.addItemDecoration(new ListItemDecoration(getApplicationContext()));
	}
	private void initRecyclerView(){
		mAdapter = new OrderHistoryAdapter();
		mRecyclerView.setAdapter(mAdapter);
		mLayoutManager = new LinearLayoutManager(getApplicationContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
	}

	@Override
	public void onHistoryReceived(List<OrderHistory> orderHistoryList) {
		initRecyclerView();
		mAdapter.addNewData(orderHistoryList);
	}

	@Override
	public void onEmpty(boolean show) {
		emptyMessage.setVisibility(show? View.VISIBLE:View.GONE);
	}

	@Override
	public void showProgressbar(boolean show) {
		progressBar.setVisibility(show? View.VISIBLE:View.GONE);
	}

	@Override
	protected void onDestroy() {
		mPresenter.detachView();
		super.onDestroy();
	}
}
