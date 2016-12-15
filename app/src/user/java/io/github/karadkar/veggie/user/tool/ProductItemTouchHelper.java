package io.github.karadkar.veggie.user.tool;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by rnztx on 11/12/16.
 * refer: https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf#.z53lbt2jh
 */

public class ProductItemTouchHelper extends ItemTouchHelper.Callback {
    private ProductItemTouchHelper.Callback listener;

    public ProductItemTouchHelper(Callback listener) {
        this.listener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onItemSwiped(viewHolder.getAdapterPosition());
    }

    public interface Callback {
        void onItemSwiped(int position);
    }
}