package com.greentopli.app.user.ui.purchase;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.greentopli.app.R;
import com.greentopli.app.user.ProductAdapter;
import com.greentopli.app.user.ui.OrderHistoryActivity;
import com.greentopli.core.OrderHistoryService;
import com.greentopli.core.presenter.cart.CartCheckoutPresenter;
import com.greentopli.core.presenter.cart.CartView;
import com.greentopli.core.storage.purchaseditem.PurchasedItemColumns;
import com.greentopli.core.storage.purchaseditem.PurchasedItemObserver;
import com.greentopli.model.OrderHistory;
import com.greentopli.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartCheckoutFragment extends Fragment implements CartView,PurchasedItemObserver.Listener{
	@BindView(R.id.cartItem_fragment_recyclerView) RecyclerView mRecyclerView;
	@BindView(R.id.progressbar_cartCheckout_fragment) ProgressBar progressBar;
	private CartCheckoutPresenter mPresenter;
	private RecyclerView.LayoutManager mLayoutManager;
	private ProductAdapter mAdapter;
	private PurchasedItemObserver contentObserver;

	private static final String TAG = CartCheckoutFragment.class.getSimpleName();
	public CartCheckoutFragment() {
		// Required empty public constructor
	}


	public static CartCheckoutFragment newInstance() {
		CartCheckoutFragment fragment = new CartCheckoutFragment();
		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mPresenter = CartCheckoutPresenter.bind(this,getContext());
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mPresenter.detachView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView =  inflater.inflate(R.layout.fragment_cart_checkout, container, false);
		ButterKnife.bind(this,rootView);
		mAdapter = new ProductAdapter(ProductAdapter.Mode.CART);
		mLayoutManager = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setAdapter(mAdapter);
		mPresenter.getProductsFromCart();
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		// Create handler on Background Thread
		HandlerThread thread = new HandlerThread(TAG);
		thread.start();
		Handler handler = new Handler(thread.getLooper());

		contentObserver = new PurchasedItemObserver(handler,getContext(),this);
		getContext().getContentResolver()
				.registerContentObserver(
						PurchasedItemColumns.CONTENT_URI,
						true,
						contentObserver
				);
		// force execute once
		contentObserver.updateCartInformation();
	}

	@Override
	public void onPause() {
		super.onPause();
		getContext().getContentResolver()
				.unregisterContentObserver(contentObserver);
	}

	@OnClick(R.id.fab_cart_items_fragment)
	void onFabClick(){
		mPresenter.checkOutOrders();
	}

	@Override
	public void onCartCheckoutSuccess(String user_id) {
		Log.e(TAG,"Checkout Success");
		Toast.makeText(getContext(),R.string.message_checkout_success,Toast.LENGTH_SHORT).show();
		// open activity to list orders
		startActivity(new Intent(getContext(),OrderHistoryActivity.class));
	}

	@Override
	public void onCartCheckoutFailed(List<String> failedProductIds) {
		Log.e(TAG,"Checkout Failed");
		Toast.makeText(getContext(),R.string.message_duplicate_orders,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCartCheckoutError(String error_message) {
		Log.e(TAG,"Checkout Error"+error_message);
		Toast.makeText(getContext(),R.string.message_checkout_error,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCartItemsReceived(List<Product> cartItems) {
		mAdapter.addNewProducts(cartItems);
	}

	@Override
	public void onCartItemsChanged(int total_price, int item_count) {
		Log.e(TAG,"Cart Total: "+total_price+" Count: "+item_count);
	}

	@Override
	public void showProgressbar(boolean show) {
		progressBar.setVisibility(show?View.VISIBLE:View.GONE);
	}
}
