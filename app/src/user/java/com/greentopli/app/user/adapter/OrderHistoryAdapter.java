package com.greentopli.app.user.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.greentopli.app.R;
import com.greentopli.model.OrderHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rnztx on 29/10/16.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
	private static final String SUBHEAD_FORMAT = "₹ %d for %d items";
	private static final String DATE_FORMAT = "EEE, dd MMM yy";
	private static Calendar todayDate = Calendar.getInstance();
	private List<OrderHistory> mOrderHistoryList;

	public OrderHistoryAdapter() {
		this.mOrderHistoryList = new ArrayList<>();
	}

	public void addNewData(List<OrderHistory> newList) {
		mOrderHistoryList.clear();
		mOrderHistoryList.addAll(newList);
		notifyDataSetChanged();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_order_history_view, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		OrderHistory order = mOrderHistoryList.get(position);
		todayDate.setTimeInMillis(order.getOrderDate());
		// format date - Sun, 20 Dec 92
		String orderDateText = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
				.format(todayDate.getTime());

		// Rs %d for %d items
		String orderDetailText = String.format(Locale.ENGLISH, SUBHEAD_FORMAT, order.getTotalPrice(), order.getTotalItems());

		holder.headingTextView.setText(orderDateText);
		holder.subheadingTextView.setText(orderDetailText);
		holder.setOrderHistory(order);
	}

	@Override
	public int getItemCount() {
		return mOrderHistoryList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		// date, total 78 Rs.
		private static final String TITLE_FORMAT = "%s - ₹ %d";
		@BindView(R.id.item_orderHistory_heading)
		TextView headingTextView;
		@BindView(R.id.item_orderHistory_subHeading)
		TextView subheadingTextView;
		private OrderHistory order;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(this);
		}

		public void setOrderHistory(OrderHistory order) {
			this.order = order;
		}

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.item_orderHistory_view_container) {

				ProductAdapter adapter = new ProductAdapter(ProductAdapter.Mode.HISTORY, order.getOrderDate(), v.getContext());
				adapter.addNewProducts(order.getProducts());

				// format date - Sun, 20 Dec 92
				todayDate.setTimeInMillis(order.getOrderDate());
				String dateFormatted = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
						.format(todayDate.getTime()).trim();
				String titleText = String.format(Locale.ENGLISH, TITLE_FORMAT, dateFormatted, order.getTotalPrice());

				MaterialDialog dialog = new MaterialDialog.Builder(v.getContext())
						.title(titleText)
						.negativeText(R.string.text_order_dialog_close)
						.adapter(adapter, new LinearLayoutManager(v.getContext()))
						.show();
			}
		}
	}
}
