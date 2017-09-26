package th.co.gosoft.storemobile.sevenbreakfastclient.Adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.ModelItemHistory;

/**
 * Created by print on 8/4/2017.
 */

public class Adapter_ItemHistory extends RecyclerView.Adapter<Adapter_ItemHistory.itemHistory> {
List<ModelItemHistory> itemHistories;
Context context;

    public Adapter_ItemHistory(List<ModelItemHistory> itemHistories, Context context) {
        this.itemHistories = itemHistories;
        this.context = context;
    }

    @Override
    public itemHistory onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_itemhistory,null);
        Adapter_ItemHistory.itemHistory itemHistory = new Adapter_ItemHistory.itemHistory(view);
        return itemHistory;
    }

    @Override
    public void onBindViewHolder(itemHistory holder, int position) {
        ModelItemHistory itemHistory = itemHistories.get(position);
        holder.name.setText(itemHistory.getName());
        holder.price.setText(itemHistory.getPrice());
        holder.value.setText(itemHistory.getValue());
        Glide.with(context)
                .load(itemHistory.getImage()) // start load
                .centerCrop() // show picture center of layout
                .into(holder.product_image); // show picture in imageView

        AnimatorSet animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.feedin);
        animationSet.setTarget(holder.product_image);
        animationSet.start();
    }

    @Override
    public int getItemCount() {
        return itemHistories.size();
    }

    public static class itemHistory extends RecyclerView.ViewHolder{
        ImageView product_image;
        TextView name,value,price;

        public itemHistory(View itemView) {
            super(itemView);
            product_image = (ImageView) itemView.findViewById(R.id.his_image_product);
            name = (TextView) itemView.findViewById(R.id.his_nameProduct);
            value = (TextView) itemView.findViewById(R.id.his_value);
            price = (TextView) itemView.findViewById(R.id.his_price);
        }
    }
}
