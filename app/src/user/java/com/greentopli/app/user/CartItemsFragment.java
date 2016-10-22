package com.greentopli.app.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greentopli.app.R;

public class CartItemsFragment extends Fragment {

	public CartItemsFragment() {
		// Required empty public constructor
	}


	public static CartItemsFragment newInstance() {
		CartItemsFragment fragment = new CartItemsFragment();
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_cart_items, container, false);
	}


}
