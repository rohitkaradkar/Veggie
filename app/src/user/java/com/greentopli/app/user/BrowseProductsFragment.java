package com.greentopli.app.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.greentopli.app.R;
import com.greentopli.core.presenter.BrowseProductsPresenter;
import com.greentopli.core.presenter.BrowseProductsView;
import com.greentopli.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseProductsFragment extends Fragment implements BrowseProductsView {

	@BindView(R.id.browse_products_recyclerView) RecyclerView mRecyclerView;
	@BindView(R.id.default_progressbar) ProgressBar progressBar;
	private BrowseAdapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private BrowseProductsPresenter mPresenter;
	public BrowseProductsFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView =  inflater.inflate(R.layout.fragment_browse_product, container, false);
		ButterKnife.bind(this,rootView);
		mAdapter = new BrowseAdapter();
		mLayoutManager = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setAdapter(mAdapter);
		mPresenter = new BrowseProductsPresenter();
		mPresenter.attachView(this,getContext());
		mPresenter.getProductItems();
		return rootView;
	}

	@Override
	public void showEmpty() {

	}

	@Override
	public void showError(String message) {

	}

	@Override
	public void showProducts(List<Product> list) {
		mAdapter.addNewProducts(list);
	}

	@Override
	public void onProductDeleted(boolean deleted, String product_id) {

	}

	@Override
	public void showProgressbar(boolean show) {
		progressBar.setVisibility(show?View.VISIBLE:View.GONE);
	}
}
