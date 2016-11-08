package com.greentopli.app.user.ui.purchase;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.greentopli.CommonUtils;
import com.greentopli.app.R;
import com.greentopli.app.user.ProductAdapter;
import com.greentopli.app.user.OnFragmentInteractionListener;
import com.greentopli.core.presenter.browse.BrowseProductsPresenter;
import com.greentopli.core.presenter.browse.BrowseProductsView;
import com.greentopli.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseProductsFragment extends Fragment implements BrowseProductsView, SearchView.OnQueryTextListener,
SearchView.OnCloseListener{

	@BindView(R.id.browse_products_recyclerView) RecyclerView mRecyclerView;
	@BindView(R.id.default_progressbar) ProgressBar mProgressBar;
	@BindView(R.id.toolbar_browseProduct_fragment)Toolbar mToolbar;
	@BindView(R.id.spinner_browse_fragment) Spinner mSpinnerVegitableType;
	private ProductAdapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private BrowseProductsPresenter mPresenter;
	SearchView mSearchView;
	OnFragmentInteractionListener listener;

	public BrowseProductsFragment() {
		// Required empty public constructor
	}
	public static BrowseProductsFragment getInstance(){
		BrowseProductsFragment fragment = new BrowseProductsFragment();
		// set parameters
		return fragment;
	}
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnFragmentInteractionListener){
			listener = (OnFragmentInteractionListener)context;
		}else {
			throw new RuntimeException(context.toString()+" Must implement "+
			OnFragmentInteractionListener.class.getSimpleName());
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView =  inflater.inflate(R.layout.fragment_browse_product, container, false);
		ButterKnife.bind(this,rootView);
		//set toolbar as Actionbar
		((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
		ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
		if (actionBar!=null){
			actionBar.setTitle("");
		}
		// enable menu options
		setHasOptionsMenu(true);
		mSearchView = new SearchView(getContext());
		mSearchView.setOnQueryTextListener(this);
		mSearchView.setOnCloseListener(this);
		mAdapter = new ProductAdapter(ProductAdapter.Mode.BROWSE);
		mLayoutManager = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setAdapter(mAdapter);
		mPresenter = BrowseProductsPresenter.bind(this,getContext());
		return rootView;
	}

	@Override
	public void onDestroy() {
		mPresenter.detachView();
		super.onDestroy();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.product_option_menu,menu);
		// add search bar
		MenuItem itemSearch = menu.findItem(R.id.menu_search_browse_product);
		MenuItemCompat.setActionView(itemSearch, mSearchView);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
				R.layout.spinner_row, CommonUtils.getFoodCategories());
		mSpinnerVegitableType.setAdapter(adapter);
//		spinner.setOnItemClickListener(null); // TODO: create category sort
	}

	@Override
	public void showEmpty() {

	}

	@Override
	public void showError(String message) {
		Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
	}

	@Override
	public void showProducts(List<Product> list) {
		mAdapter.addNewProducts(list);
	}

	@Override
	public void onProductDeleted(boolean deleted, String product_id) {}

	@OnClick(R.id.fab_browse_product_fragment)
	void onFabClick(){
		if (mAdapter.getCartItemCount()>0){
			listener.onFragmentInteraction();
		}else {
			Toast.makeText(getContext(),R.string.message_empty_cart,Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void showProgressbar(boolean show) {
		mProgressBar.setVisibility(show?View.VISIBLE:View.GONE);
	}

	// Search Query Handler
	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		mPresenter.searchProduct(newText);
		return false;
	}

	// when user finishes using searchBar
	@Override
	public boolean onClose() {
		mPresenter.getProductItems();
		return false;
	}
}
