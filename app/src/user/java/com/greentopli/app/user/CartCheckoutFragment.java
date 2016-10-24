package com.greentopli.app.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.greentopli.core.presenter.cart.CartCheckoutPresenter;
import com.greentopli.core.presenter.cart.CartView;
import com.greentopli.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartCheckoutFragment extends Fragment implements CartView{
	@BindView(R.id.cartItem_fragment_recyclerView) RecyclerView mRecyclerView;
	@BindView(R.id.progressbar_cartCheckout_fragment) ProgressBar progressBar;
	private CartCheckoutPresenter mPresenter;
	private RecyclerView.LayoutManager mLayoutManager;
	private BrowseAdapter mAdapter;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView =  inflater.inflate(R.layout.fragment_cart_checkout, container, false);
		ButterKnife.bind(this,rootView);
		mAdapter = new BrowseAdapter(true);
		mLayoutManager = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setAdapter(mAdapter);
		mPresenter.getProductsFromCart();
		return rootView;
	}

	@OnClick(R.id.fab_cart_items_fragment)
	void onFabClick(){
		mPresenter.checkOutOrders();
	}

	@Override
	public void onCartCheckoutSuccess() {
		Log.e(TAG,"Checkout Success");
		Toast.makeText(getContext(),R.string.message_checkout_success,Toast.LENGTH_SHORT).show();
		startActivity(new Intent(getContext(),PurchaseManagerActivity.class));
	}

	@Override
	public void onCartCheckoutFailed(List<String> failedProductIds) {
		Log.e(TAG,"Checkout Failed");
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
	public void showProgressbar(boolean show) {
		progressBar.setVisibility(show?View.VISIBLE:View.GONE);
	}
}
