package com.greentopli.app.user.tool;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.greentopli.app.R;

/**
 * Created by rnztx on 15/11/16.
 * refer: http://stackoverflow.com/a/28533234/2804351
 */

public class ProductItemDecoration extends RecyclerView.ItemDecoration {
	private int margin;

	public ProductItemDecoration(Context context) {
		this.margin = context.getResources().getDimensionPixelSize(R.dimen.product_item_view_margin);
	}

	@Override
	public void getItemOffsets(Rect outRect, View view,
	                           RecyclerView parent, RecyclerView.State state) {
		int position = parent.getChildLayoutPosition(view);
		outRect.set(margin, position == 0 ? margin : 0, margin, margin);
	}
}
