package th.co.gosoft.storemobile.sevenbreakfastclient.Adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import th.co.gosoft.storemobile.sevenbreakfastclient.model.mapModel;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;

/**
 * Created by print on 4/21/2017.
 */

public class Adapter_SelectStore extends RecyclerView.Adapter<Adapter_SelectStore.customAdapterMap> {

    Context context;
    List<mapModel> mapModelList;
    onClickItem item;

    public Adapter_SelectStore(Context applicationContext, List<mapModel> mapModels) {
        this.context = applicationContext;
        this.mapModelList = mapModels;
    }

    public interface onClickItem{
        void onItemClick(View view,int position,double origin,double distine,String name);
    }

    public Adapter_SelectStore.onClickItem getItem(){return item;}

    public void setItem(Adapter_SelectStore.onClickItem item){this.item = item;}
    public void setClickItem(Adapter_SelectStore.onClickItem item){setItem(item);}



    @Override
    public customAdapterMap onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_selecstore,parent,false);
        Adapter_SelectStore.customAdapterMap customAdapterMap = new Adapter_SelectStore.customAdapterMap(view);
        return customAdapterMap;
    }

    @Override
    public void onBindViewHolder(customAdapterMap holder, final int position) {
        final mapModel mapModel = mapModelList.get(position);
        System.out.println("name: "+mapModel.getName());
        holder.imageView.setImageResource(mapModel.getImage());
        AnimatorSet animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.feedin);
        animationSet.setTarget(holder.imageView);
        animationSet.start();
        holder.store.setText(mapModel.getName());
        holder.distance.setText(mapModel.getDistance());
        holder.card_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItem().onItemClick(v,position,mapModel.getOrigin(),mapModel.getDistination(),mapModel.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mapModelList.size();
    }

    public static class customAdapterMap extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView store,distance;
    LinearLayout card_select;
    public customAdapterMap(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.imageView_ss);
        store =  (TextView)itemView.findViewById(R.id.txt_store_ss);
        distance = (TextView)itemView.findViewById(R.id.txt_distance_ss);
        card_select = (LinearLayout)itemView.findViewById(R.id.card_selectStore);
    }
}
}