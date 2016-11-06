package com.greentopli.app.user.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.greentopli.CommonUtils;
import com.greentopli.app.R;
import com.greentopli.core.handler.CartDbHandler;
import com.greentopli.model.Product;

import java.util.List;
import java.util.Locale;

/**
 * Created by rnztx on 4/11/16.
 */

public class WidgetRemoteViewsService extends RemoteViewsService {
	private static final String TAG = WidgetItemRemoteView.class.getSimpleName();
	public WidgetRemoteViewsService() {}

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new WidgetItemRemoteView(this.getApplicationContext(),intent);
	}
	class WidgetItemRemoteView implements RemoteViewsService.RemoteViewsFactory{
		Context mContext;
		Intent mIntent;
		List<Product> mProducts;
		public WidgetItemRemoteView(Context mContext, Intent mIntent) {
			this.mContext = mContext;
			this.mIntent = mIntent;
		}

		@Override
		public void onCreate() {
			updateDataSet();
		}

		@Override
		public void onDataSetChanged() {
			updateDataSet();
		}

		@Override
		public void onDestroy() {

		}

		@Override
		public int getCount() {
			return (mProducts==null?0:mProducts.size());
		}

		@Override
		public RemoteViews getViewAt(int position) {
			if (position >= 0 && mProducts.size()>0){
				RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.item_widget_view);
				final Product product = mProducts.get(position);
				remoteView.setTextViewText(R.id.item_widget_product_name,product.getName_english());

				try {
					// As we have already Cached images while using Glide in App
					Bitmap bitmap = Glide.with(mContext)
							.load(product.getImageUrl())
							.asBitmap()
							.diskCacheStrategy(DiskCacheStrategy.SOURCE)
							.into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
							.get();

					if (bitmap!=null)
						remoteView.setBitmap(R.id.item_widget_product_image,"setImageBitmap",bitmap);

				}catch (Exception e){
					e.printStackTrace();
				}
				return remoteView;
			}
			return null;
		}

		@Override
		public RemoteViews getLoadingView() {
			return null;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public long getItemId(int position) {
			return (position<mProducts.size()?position:-1);
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		private void updateDataSet(){
			CartDbHandler cartDbHandler = new CartDbHandler(mContext);
			long date = CommonUtils.getDateExcludingTime();
			mProducts = cartDbHandler.getProductsFromCart(true,date);
		}
	}
}
