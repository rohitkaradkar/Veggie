package com.greentopli.app.user.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.greentopli.app.AuthenticatorActivity;
import com.greentopli.app.R;
import com.greentopli.app.user.OrderHistoryAdapter;
import com.greentopli.core.OrderHistoryService;
import com.greentopli.core.presenter.OrderHistoryPresenter;
import com.greentopli.core.presenter.OrderHistoryView;
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
	private IntentFilter intentFilter = new IntentFilter();
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
		initService();
	}
	private void initService(){
		if (AuthenticatorActivity.isUserSignedIn()){
			// create filters
			intentFilter.addAction(OrderHistoryService.ACTION_PROCESSING);
			intentFilter.addAction(OrderHistoryService.ACTION_PROCESSING_COMPLETE);
			intentFilter.addAction(OrderHistoryService.ACTION_PROCESSING_FAILED);

			// Start service to request user orders
			String user_id = FirebaseAuth.getInstance().getCurrentUser().getEmail();
			Intent orderHistoryService = new Intent(getApplicationContext(), OrderHistoryService.class);
			orderHistoryService.setData(Uri.parse(user_id));
			startService(orderHistoryService);
		}
	}
	@Override
	public void onDataReceived(List<OrderHistory> orderHistoryList) {
		mAdapter.addNewData(orderHistoryList);
	}
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
//			Toast.makeText(context,intent.getAction(),Toast.LENGTH_SHORT).show();
			switch (intent.getAction()){
				case OrderHistoryService.ACTION_PROCESSING:
					showProgressbar(true);
					break;
				case OrderHistoryService.ACTION_PROCESSING_COMPLETE:
					mPresenter.requestOrderHistory();
					break;
				case OrderHistoryService.ACTION_PROCESSING_FAILED:
					// retry
					break;
			}
		}
	};

	@Override // Register Broadcast receiver
	protected void onResume() {
		super.onResume();
		registerReceiver(broadcastReceiver,intentFilter);
	}

	@Override// UnRegister Broadcast receiver
	protected void onPause() {
		super.onPause();
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	public void onEmpty(boolean show) {
		emptyMessage.setVisibility(show? View.VISIBLE:View.GONE);
	}

	@Override
	public void showProgressbar(boolean show) {
		progressBar.setVisibility(show? View.VISIBLE:View.GONE);
	}
}
