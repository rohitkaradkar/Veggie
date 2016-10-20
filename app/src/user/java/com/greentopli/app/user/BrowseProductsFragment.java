package com.greentopli.app.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greentopli.app.R;
import com.greentopli.core.presenter.BrowseProductsPresenter;
import com.greentopli.core.presenter.BrowseProductsView;
import com.greentopli.model.Product;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseProductsFragment extends Fragment implements BrowseProductsView {

	public BrowseProductsFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView =  inflater.inflate(R.layout.fragment_browse_product, container, false);
		new BrowseProductsPresenter().attachView(this);
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

	}

	@Override
	public void onProductDeleted(boolean deleted, String product_id) {

	}

	@Override
	public void showProgressbar(boolean show) {

	}
}
