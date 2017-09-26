package th.co.gosoft.storemobile.sevenbreakfastclient.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import th.co.gosoft.storemobile.sevenbreakfastclient.model.modelDetail;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;

/**
 * Created by print on 4/18/2017.
 */

public class Adapter_promotion_Detail extends RecyclerView.Adapter<Adapter_promotion_Detail.CustomDetail> {

    Context context;
    List<modelDetail> modeldetails;

    public Adapter_promotion_Detail(Context context, List<modelDetail> modelDetails){
        this.context = context;
        this.modeldetails = modelDetails;

    }
    @Override
    public CustomDetail onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_promotion_detail,parent,false);
       Adapter_promotion_Detail.CustomDetail detail = new Adapter_promotion_Detail.CustomDetail(view);
        return detail;
    }

    @Override
    public void onBindViewHolder(CustomDetail holder, int position) {
        modelDetail modelDetail = modeldetails.get(position);
        holder.titeldetail.setText(modelDetail.getTitle());
        holder.datadetail.setText(modelDetail.getdata());
    }

    @Override
    public int getItemCount() {
        return modeldetails.size();
    }

    public static class CustomDetail extends RecyclerView.ViewHolder {
        TextView titeldetail,datadetail;
        public CustomDetail(View itemView) {
            super(itemView);
            titeldetail = (TextView)itemView.findViewById(R.id.titel_detail);
            datadetail = (TextView)itemView.findViewById(R.id.data_detail);
        }
    }
}

