package io.github.karadkar.veggie.user.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.greentopli.app.R;
import io.github.karadkar.veggie.user.adapter.OrderHistoryAdapter;
import io.github.karadkar.veggie.user.tool.ProductItemDecoration;
import com.greentopli.core.presenter.history.OrderHistoryPresenter;
import com.greentopli.core.presenter.history.OrderHistoryView;
import com.greentopli.core.service.OrderHistoryService;
import com.greentopli.model.OrderHistory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryActivity extends AppCompatActivity implements OrderHistoryView, SwipeRefreshLayout.OnRefreshListener {
	@BindView(R.id.orderHistory_recyclerView)
	RecyclerView mRecyclerView;
	@BindView(R.id.orderHistory_empty_message)
	TextView emptyMessage;
	@BindView(R.id.progressbar_orderHistory_activity)
	ProgressBar progressBar;
	@BindView(R.id.order_history_swipeRefreshLayout)
	SwipeRefreshLayout mSwipeRefreshLayout;
	private OrderHistoryPresenter mPresenter;
	private LinearLayoutManager mLayoutManager;
	private OrderHistoryAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_history);
		ButterKnife.bind(this);
		mPresenter = OrderHistoryPresenter.bind(this, getApplicationContext());
		mRecyclerView.addItemDecoration(new ProductItemDecoration(getApplicationContext()));
		mSwipeRefreshLayout.setOnRefreshListener(this);
	}

	private void initRecyclerView() {
		mAdapter = new OrderHistoryAdapter();
		mRecyclerView.setAdapter(mAdapter);
		mLayoutManager = new LinearLayoutManager(getApplicationContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
	}

	@Override
	public void onHistoryReceived(List<OrderHistory> orderHistoryList) {
		initRecyclerView();
		mAdapter.addNewData(orderHistoryList);
		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void onEmpty(boolean show) {
		emptyMessage.setVisibility(show ? View.VISIBLE : View.GONE);
		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void showProgressbar(boolean show) {
		mSwipeRefreshLayout.setRefreshing(false);
		progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onRefresh() {
		OrderHistoryService.start(getApplicationContext());
	}

	@Override
	protected void onDestroy() {
		mPresenter.detachView();
		super.onDestroy();
	}
}
