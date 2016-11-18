package com.greentopli.app.user;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.greentopli.CommonUtils;
import com.greentopli.Constants;
import com.greentopli.app.R;
import com.greentopli.core.dbhandler.CartDbHandler;
import com.greentopli.model.Product;
import com.greentopli.model.PurchasedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rnztx on 20/10/16.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
	private List<Product> mProducts;
	private Context mContext;
	private CartDbHandler mCartDbHandler;
	private FirebaseAnalytics mFirebaseAnalytics;

	private static final String FORMAT_PRICE_AND_VOLUME = "%s is ₹ %d";
	private static final String FORMAT_PRICE = "₹ %d";
	private static final String FORMAT_VOLUME = "%s";
	public enum Mode {
		BROWSE, CART, HISTORY
	}
	private Mode adapterMode;
	private long dateOfRequest;

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		@BindView(R.id.item_product_image) ImageView image;
		@BindView(R.id.item_product_checkbox) CheckBox checkBox;
		@BindView(R.id.item_product_name) TextView name;
		@BindView(R.id.item_product_price) TextView price;
		@BindView(R.id.item_product_volume) TextView volume;
		@BindView(R.id.item_product_volume_controls) RelativeLayout volumeControls;
		@BindView(R.id.item_product_remove_button) ImageButton removeCartItemButton;
		private Product product;
		private PurchasedItem cartItem;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this,itemView);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			/**
			 * In Browse mode Direct click to checkbox is DISABLED,
			 * so we manually handle the tick & correspondingly addButton / remove item from cart
			 */
			if (v.getId()==R.id.item_product_view_container && adapterMode.equals(Mode.BROWSE))
				updateToCart();
		}

		@OnClick(R.id.item_product_volume_add_button)
		void onVolumeAdded(){
			int newVolume = cartItem.getVolume() + product.getVolumeSet();
			if (newVolume <= product.getMaximumVolume()) {
				mCartDbHandler.updateVolume(product.getId(), newVolume);
				notifyItemChanged(getAdapterPosition());
			}
		}

		@OnClick(R.id.item_product_volume_subtract_button)
		void onVolumeSubtracted(){
			int newVolume = cartItem.getVolume() - product.getVolumeSet();
			if (newVolume >= product.getMinimumVolume()) {
				mCartDbHandler.updateVolume(product.getId(), newVolume);
				notifyItemChanged(getAdapterPosition());
			}
		}

		@OnClick(R.id.item_product_remove_button)
		void onCartItemRemoved(){
			if (mCartDbHandler.removeProductFromCart(product.getId())>0){
				for (int i=0; i<mProducts.size();i++){
					Product item = mProducts.get(i);
					if (item.getId().equals(product.getId())) {
						mProducts.remove(i);
						notifyItemRemoved(getAdapterPosition());
					}
				}
			}
		}

		private void updateToCart(){
			/**
			 * using analytics we track which items are removed from Cart.
			 */
			Bundle analyticsData = new Bundle();
			analyticsData.putString(FirebaseAnalytics.Param.ITEM_NAME,product.getName_english());
			analyticsData.putInt(Constants.ITEM_PRICE,product.getPrice());
			analyticsData.putInt(Constants.ITEM_VOLUME,product.getMinimumVolume());

			if (mCartDbHandler.isProductAddedToCart(product.getId())){
				mCartDbHandler.removeProductFromCart(product.getId());
				checkBox.setChecked(false);
				mFirebaseAnalytics.logEvent(Constants.EVENT_CART_ITEM_REMOVED,analyticsData);
			}else {
				mCartDbHandler.addProductToCart(product.getId(),product.getMinimumVolume());
				checkBox.setChecked(true);
			}
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		public void setCartItem(PurchasedItem cartItem) {
			this.cartItem = cartItem;
		}
	}

	public ProductAdapter(Mode adapterMode, Context context){
		this(adapterMode,0,context);
	}

	public ProductAdapter(Mode adapterMode, long dateOfRequest, Context context){
		this(new ArrayList<Product>(),context);
		this.adapterMode = adapterMode;
		this.dateOfRequest = dateOfRequest;
	}
	private ProductAdapter(List<Product> products, Context context) {
		this.mProducts = products;
		this.mContext = context;
		mCartDbHandler = new CartDbHandler(context);
		mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// while displaying history - don't use CardView
		int resourceId = (this.adapterMode.equals(Mode.HISTORY)?
				R.layout.item_product_view_content:R.layout.item_product_view);
		View view = LayoutInflater.from(parent.getContext())
				.inflate(resourceId,parent,false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Product product = mProducts.get(position);
		holder.setProduct(product);
		holder.name.setText(product.getName_english());

		Glide.with(holder.image.getContext())
				.load(product.getImageUrl())
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.into(holder.image);

		String formattedPrice="", formattedVolume="";
		// When Browsing through Items & adding them to carts
		if (adapterMode.equals(Mode.BROWSE)){
			formattedPrice = String.format(Locale.ENGLISH,
					FORMAT_PRICE_AND_VOLUME,
					CommonUtils.getVolumeExtension(product.getMinimumVolume(),product.getVolume()),
					product.getPrice());
			holder.checkBox.setVisibility(View.VISIBLE);
			holder.checkBox.setChecked(
					mCartDbHandler.isProductAddedToCart(holder.product.getId())
			);
		}
		// when managing Items present in cart
		else if (adapterMode.equals(Mode.CART)){
			PurchasedItem item = mCartDbHandler.getCartItem(product.getId(),false);
			formattedVolume = String.format(Locale.ENGLISH,
					FORMAT_VOLUME,
					CommonUtils.getVolumeExtension(item.getVolume(),product.getVolume()));
			formattedPrice = String.format(Locale.ENGLISH,
					FORMAT_PRICE,
					item.getTotalPrice()
					);
			holder.setCartItem(item);
			holder.volumeControls.setVisibility(View.VISIBLE);
			holder.volume.setText(formattedVolume);
			holder.removeCartItemButton.setVisibility(View.VISIBLE);
		}
		// just checking order history
		else if(adapterMode.equals(Mode.HISTORY)){
			PurchasedItem item = mCartDbHandler.getCartItem(product.getId(),true,dateOfRequest);
			formattedPrice = String.format(Locale.ENGLISH,
					FORMAT_PRICE_AND_VOLUME,
					CommonUtils.getVolumeExtension(item.getVolume(),product.getVolume()),
					item.getTotalPrice()
			);
		}
		holder.price.setText(formattedPrice);
	}

	@Override
	public int getItemCount() {
		return mProducts.size();
	}

	public int getCartItemCount(){
		return mCartDbHandler.getProductIdsFromCart(false).size();
	}

	public void addNewProducts(List<Product> list){
		mProducts.clear();
		mProducts.addAll(list);
		notifyDataSetChanged();
	}

}
