package com.greentopli.app.user.ui.purchase;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.greentopli.Constants;
import com.greentopli.app.R;
import com.greentopli.app.user.adapter.ProductAdapter;
import com.greentopli.app.user.tool.ProductItemDecoration;
import com.greentopli.app.user.tool.ProductItemTouchHelper;
import com.greentopli.core.presenter.checkout.CartCheckoutPresenter;
import com.greentopli.core.presenter.checkout.CartView;
import com.greentopli.core.service.OrderHistoryService;
import com.greentopli.core.service.PurchasedItemObserver;
import com.greentopli.core.storage.purchaseditem.PurchasedItemColumns;
import com.greentopli.model.Product;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartCheckoutFragment extends Fragment implements CartView, PurchasedItemObserver.Listener,
		ProductItemTouchHelper.Callback {
	private static final String FORMAT_CART_OVERVIEW = "₹ %d for %d items";
	private static final String TAG = CartCheckoutFragment.class.getSimpleName();
	@BindView(R.id.cartItem_fragment_recyclerView)
	RecyclerView mRecyclerView;
	@BindView(R.id.progressbar_cartCheckout_fragment)
	ProgressBar progressBar;
	@BindView(R.id.toolbar_cartCheckout_fragment)
	Toolbar mToolbar;
	@BindInt(R.integer.product_list_columns)
	int mColumnCount;
	@BindView(R.id.fab_cart_items_fragment)
	FloatingActionButton mFab;
	private CartCheckoutPresenter mPresenter;
	private RecyclerView.LayoutManager mLayoutManager;
	private ProductAdapter mAdapter;
	private PurchasedItemObserver contentObserver;

	public CartCheckoutFragment() {
		// Required empty public constructor
	}


	public static CartCheckoutFragment newInstance() {
		CartCheckoutFragment fragment = new CartCheckoutFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_cart_checkout, container, false);
		ButterKnife.bind(this, rootView);

		//set toolbar as Actionbar
		((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
		ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
		if (actionBar != null) {
			//actionBar.setTitle(R.string.title_fragment_cartCheckout);
			mToolbar.setTitle(R.string.title_fragment_cartCheckout);
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		setHasOptionsMenu(true);
		initRecyclerView();
		mRecyclerView.addItemDecoration(new ProductItemDecoration(getContext()));
		return rootView;
	}

	private void initRecyclerView() {
		mAdapter = new ProductAdapter(ProductAdapter.Mode.CART, getContext());
		mLayoutManager = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setAdapter(mAdapter);

		ItemTouchHelper.Callback callback = new ProductItemTouchHelper(this);
		ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
		touchHelper.attachToRecyclerView(mRecyclerView);
	}

	@Override
	public void onResume() {
		super.onResume();
		//http://stackoverflow.com/a/16617831/2804351
		// Create handler on Background Thread
		HandlerThread thread = new HandlerThread(TAG);
		thread.start();
		Handler handler = new Handler(thread.getLooper());

		contentObserver = new PurchasedItemObserver(handler, getContext(), this);
		getContext().getContentResolver()
				.registerContentObserver(
						PurchasedItemColumns.CONTENT_URI,
						true,
						contentObserver
				);
		// force execute once
		contentObserver.updateCartInformation();

		// register presenter
		mPresenter = CartCheckoutPresenter.bind(this, getContext());
		mPresenter.getProductsFromCart();
	}

	@Override
	public void onPause() {
		super.onPause();
		getContext().getContentResolver()
				.unregisterContentObserver(contentObserver);

		mPresenter.detachView();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			goBack();
			return true;
		}
		return false;
	}

	@OnClick(R.id.fab_cart_items_fragment)
	void onFabClick() {
		mPresenter.checkOutOrders();
	}

	@Override
	public void onCartCheckoutSuccess(String user_id) {
		// update order history
		OrderHistoryService.start(getContext());
		Toast.makeText(getContext(), R.string.message_checkout_success, Toast.LENGTH_SHORT).show();
		// navigate to Main Screen
		goBack();
	}

	@Override
	public void onCartCheckoutFailed(List<String> failedProductIds) {
		Log.e(TAG, "Checkout Failed");
		Toast.makeText(getContext(), R.string.message_duplicate_orders, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCartCheckoutError(String error_message) {
		Log.e(TAG, "Checkout Error" + error_message);
		Toast.makeText(getContext(), R.string.message_checkout_error, Toast.LENGTH_SHORT).show();
		FirebaseCrash.log(Constants.ERROR_CART_CHECKOUT + error_message);
	}

	@Override
	public void onCartItemsReceived(List<Product> cartItems) {
		mAdapter.addNewProducts(cartItems);
	}

	@Override
	public void onCartItemsChanged(final int total_price, final int item_count) {
		/**
		 * This call is made from background thread EVERY TIME cart items are changed.
		 * Only the original thread that created a view hierarchy can touch its views.
		 */
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (total_price > 0 && item_count > 0)
					mToolbar.setSubtitle(String.format(FORMAT_CART_OVERVIEW, total_price, item_count));
				else {
					goBack();
				}
			}
		});
	}

	@Override
	public void showProgressbar(boolean show) {
		progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
		mFab.setEnabled(!show);
	}

	private void goBack() {
		try {
			FragmentManager manager = null;
			if (getActivity() != null)
				manager = getActivity().getSupportFragmentManager();

			if (manager != null && manager.getBackStackEntryCount() == 1) {
				manager.popBackStackImmediate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onItemSwiped(int position) {
		if (mAdapter != null && mAdapter.getItemCount() > position)
			mAdapter.removeProduct(position);
	}
}
