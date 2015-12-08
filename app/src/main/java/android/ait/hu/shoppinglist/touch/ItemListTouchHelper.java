package android.ait.hu.shoppinglist.touch;

import android.ait.hu.shoppinglist.adapter.ItemRecyclerAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
/**
 * Created by Wanchen on 11/5/2015.
 */
public class ItemListTouchHelper extends ItemTouchHelper.Callback {
    private ItemRecyclerAdapter adapter;

    public ItemListTouchHelper(ItemRecyclerAdapter adapter){ this.adapter = adapter;}

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
        adapter.swapItems(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());

        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.removeItem(viewHolder.getAdapterPosition());
    }

}
