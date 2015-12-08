package android.ait.hu.shoppinglist.adapter;

import android.ait.hu.shoppinglist.ApplicationContextProvider;
import android.ait.hu.shoppinglist.CreateItemActivity;
import android.ait.hu.shoppinglist.MainActivity;
import android.ait.hu.shoppinglist.R;
import android.ait.hu.shoppinglist.data.Item;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Wanchen on 10/30/2015.
 */
public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder> {

    public Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageIcon;
        public TextView tvItem;
        public TextView tvDescription;
        public TextView tvPrice;
        public CheckBox cbDone;
        public ImageButton btnDetails;
        public ImageButton btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            imageIcon = (ImageView) itemView.findViewById(R.id.imageIcon);
            tvItem = (TextView) itemView.findViewById(R.id.tvItem);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            cbDone = (CheckBox) itemView.findViewById(R.id.cbDone);
            btnDetails = (ImageButton) itemView.findViewById(R.id.btnDesc);
            btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);

        }


    }

    private List<Item> items;

    public ItemRecyclerAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final Item item = items.get(i);
        viewHolder.imageIcon.setImageResource(items.get(i).getItemType().getIconId());
        viewHolder.tvItem.setText(item.getItem());
        viewHolder.tvDescription.setText(item.getDescription());
        viewHolder.tvPrice.setText(String.format("$%.2f", item.getPrice()));
        viewHolder.cbDone.setChecked(item.isDone());
        viewHolder.cbDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                item.setDone(((CheckBox) v).isChecked());
                item.save();
            }
        });
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).showEditPlaceActivity(
                        items.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
            }
        });

        viewHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.tvDescription.getVisibility() == View.GONE){
                    viewHolder.tvDescription.setVisibility(View.VISIBLE);
                    item.save();
                }
                else if (viewHolder.tvDescription.getVisibility() == View.VISIBLE){
                    viewHolder.tvDescription.setVisibility(View.GONE);
                    item.save();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItem(int index, Item item) {
        items.set(index, item);
        item.save();
        notifyItemChanged(index);
    }

    public void removeItem(int index){
        items.get(index).delete();
        items.remove(index);
        notifyDataSetChanged();
    }

    public void addItem(Item item){
        item.save();
        items.add(item);
        notifyDataSetChanged();
    }

    public void swapItems(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(items, i, i + 1);

            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(items, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }


}
