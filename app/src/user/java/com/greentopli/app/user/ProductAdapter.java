package com.greentopli.app.user;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.greentopli.CommonUtils;
import com.greentopli.app.R;
import com.greentopli.core.handler.CartDbHandler;
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
	CartDbHandler cartDbHandler;
	private static final String FORMAT_PRICE_PER_VOLUME = "Rs %d / %s";
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
		@BindView(R.id.subtract_image_button)ImageButton subtractButton;
		@BindView(R.id.add_image_button)ImageButton addButton;
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
			if (v.getId()==R.id.item_product_view && adapterMode.equals(Mode.BROWSE))
				updateToCart();
		}

		@OnClick(R.id.add_image_button)
		void onVolumeAdded(){
			int newVolume = cartItem.getVolume() + product.getVolumeSet();
			if (newVolume <= product.getMaximumVolume()) {
				cartDbHandler.updateVolume(product.getId(), newVolume);
				notifyItemChanged(getAdapterPosition());
			}
		}
		@OnClick(R.id.subtract_image_button)
		void onVolumeSubtracted(){
			int newVolume = cartItem.getVolume() - product.getVolumeSet();
			if (newVolume >= product.getMinimumVolume()) {
				cartDbHandler.updateVolume(product.getId(), newVolume);
				notifyItemChanged(getAdapterPosition());
			}
		}

		private void updateToCart(){
			if (checkBox.isChecked()){
				cartDbHandler.removeProductFromCart(product.getId());
				checkBox.setChecked(false);
			}else {
				cartDbHandler.addProductToCart(product.getId(),product.getMinimumVolume());
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

	public ProductAdapter(Mode adapterMode){
		this(adapterMode,0);
	}
	public ProductAdapter(Mode adapterMode, long dateOfRequest){
		this(new ArrayList<Product>());
		this.adapterMode = adapterMode;
		this.dateOfRequest = dateOfRequest;
	}
	private ProductAdapter(List<Product> products) {
		this.mProducts = products;
		notifyDataSetChanged();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (cartDbHandler == null)
			cartDbHandler = new CartDbHandler(parent.getContext());

		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_product_view,parent,false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Product product = mProducts.get(position);
		holder.setProduct(product);
		String productName = product.getName_english();
		holder.name.setText(String.format(Locale.ENGLISH,"%s",
				String.valueOf(productName.charAt(0)).toUpperCase()+
				productName.substring(1).toLowerCase()));

		Glide.with(holder.image.getContext())
				.load(product.getImageUrl())
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.into(holder.image);

		String formattedPrice = "";

		// When Browsing through Items & adding them to carts
		if (adapterMode.equals(Mode.BROWSE)){
			formattedPrice = String.format(Locale.ENGLISH,
					FORMAT_PRICE_PER_VOLUME,product.getPrice(),
					CommonUtils.getVolumeExtension(product.getMinimumVolume(),product.getVolume()));
			holder.checkBox.setVisibility(View.VISIBLE);
			if (cartDbHandler.isProductAddedToCart(product.getId())) {
				holder.checkBox.setChecked(true);
			}
		}
		// when managing Items present in cart
		else if (adapterMode.equals(Mode.CART)){
			PurchasedItem item = cartDbHandler.getCartItem(product.getId(),false);
			formattedPrice = String.format(Locale.ENGLISH,
					FORMAT_PRICE_PER_VOLUME,item.getTotalPrice(),
					CommonUtils.getVolumeExtension(item.getVolume(),product.getVolume()));
			holder.setCartItem(item);
			holder.addButton.setVisibility(View.VISIBLE);
			holder.subtractButton.setVisibility(View.VISIBLE);
		}
		// just checking order history
		else if(adapterMode.equals(Mode.HISTORY)){
			PurchasedItem item = cartDbHandler.getCartItem(product.getId(),true,dateOfRequest);
			formattedPrice = String.format(Locale.ENGLISH,
					FORMAT_PRICE_PER_VOLUME,item.getTotalPrice(),
					CommonUtils.getVolumeExtension(item.getVolume(),product.getVolume()));
		}
		holder.price.setText(formattedPrice);
	}

	@Override
	public int getItemCount() {
		return mProducts.size();
	}

	public int getCartItemCount(){
		if (cartDbHandler==null)
			return 0;
		return cartDbHandler.getProductIdsFromCart(false).size();
	}

	public void addNewProducts(List<Product> list){
		mProducts.clear();
		mProducts.addAll(list);
		notifyDataSetChanged();
	}

}
