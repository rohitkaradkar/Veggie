package com.greentopli.app.user.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.greentopli.app.R;
import com.greentopli.app.user.ProductAdapter;
import com.greentopli.core.handler.CartDbHandler;
import com.greentopli.core.handler.ProductDbHandler;
import com.greentopli.core.presenter.OrderHistoryPresenter;
import com.greentopli.core.presenter.OrderHistoryView;
import com.greentopli.model.OrderHistory;
import com.greentopli.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderHistoryActivity extends AppCompatActivity implements OrderHistoryView {
	OrderHistoryPresenter presenter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_history);
		ButterKnife.bind(this);
		presenter = OrderHistoryPresenter.bind(this,getApplicationContext());
		presenter.requestOrderHistory();
	}

	//TODO: remove this test code
	@OnClick(R.id.test_order_dialog_button)
	void onClickTestButton(){
		long dateRequested = 1477506600000L;
		CartDbHandler cartDbHandler = new CartDbHandler(getApplicationContext());

		List<Product> products = cartDbHandler.getProductsFromCart(true,dateRequested);

		ProductAdapter adapter = new ProductAdapter(ProductAdapter.Mode.HISTORY,dateRequested);
		adapter.addNewProducts(products);

		MaterialDialog dialog = new MaterialDialog.Builder(this)
				.title(R.string.title_order_history)
				.negativeText("Close")
				.adapter(adapter,new LinearLayoutManager(getApplicationContext()))
				.show();
	}

	@Override
	public void onDataReceived(List<OrderHistory> orderHistoryList) {
		//TODO: remove this test code
		for (OrderHistory order: orderHistoryList){
			ProductAdapter adapter = new ProductAdapter(ProductAdapter.Mode.HISTORY,order.getOrderDate());
			adapter.addNewProducts(order.getProducts());

			MaterialDialog dialog = new MaterialDialog.Builder(this)
					.title(R.string.title_order_history)
					.negativeText("Close")
					.adapter(adapter,new LinearLayoutManager(getApplicationContext()))
					.show();
		}

	}

	@Override
	public void showProgressbar(boolean show) {

	}
}
