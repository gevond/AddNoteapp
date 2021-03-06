package com.bignerdranch.android.addnote;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by User on 12/2/2017.
 */


public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {


    private final ItemTouchHelperAdapter mAdapter;

    public ItemTouchHelperCallback(ItemTouchHelperAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                         int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    public interface ItemTouchHelperAdapter{
        boolean onItemMove(int fromPosition,int toPosition);
        void onItemDismiss(int position);
    };

}
