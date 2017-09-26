package th.co.gosoft.storemobile.sevenbreakfastclient.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import th.co.gosoft.storemobile.sevenbreakfastclient.MainActivity;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.ModelOrder;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;

/**
 * Created by print on 4/21/2017.
 */

public class Aadapter_History_Order extends RecyclerView.Adapter<Aadapter_History_Order.customHistoryOrder> {

    Context context;
    List<ModelOrder> modelOrders;

    public Aadapter_History_Order(Context context, List<ModelOrder> modelOrders) {
        this.context = context;
        this.modelOrders = modelOrders;
    }
    // set connect layout
    @Override
    public customHistoryOrder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_history_order,parent,false);
        Aadapter_History_Order.customHistoryOrder adapter_history_order = new Aadapter_History_Order.customHistoryOrder(view);
        return adapter_history_order;
    }
    //  set Action in layout
    @Override
    public void onBindViewHolder(customHistoryOrder holder, int position) {
        final ModelOrder modelOrder = modelOrders.get(position);
        holder.no_order.setText(modelOrder.getNo_order());
        holder.status.setText(modelOrder.getStatus());
        holder.date.setText(modelOrder.getDate());
        switch (modelOrder.getStatus()) {
            case "Complete" :
                holder.status.setTextColor(Color.GRAY);
                break;
            case "Waiting" :
                holder.status.setTextColor(Color.GRAY);
                break;
            case "Preparing" :
                holder.status.setTextColor(Color.GRAY);
                break;
            case "Cancel - Out of stock" :
                holder.status.setTextColor(Color.RED);
                break;
            case "Ready" :
                holder.status.setTextColor(Color.GREEN);
                break;
            default: holder.status.setTextColor(Color.WHITE);
                break;
        }
        // set click go to CustomerHistory Order Detail Fragment
        holder.bt_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mainListener.onClickHistory2DetailOrder(modelOrder.getNo_order());
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelOrders.size();
    }

    // set connect parameter in layout
    public static class customHistoryOrder extends RecyclerView.ViewHolder{
        TextView no_order,status,date;
        Button bt_order;
        ImageView imageView;
        public customHistoryOrder(View itemView) {
            super(itemView);
            no_order = (TextView)itemView.findViewById(R.id.txt_no_order);
            status = (TextView)itemView.findViewById(R.id.txt_status_order);
            date = (TextView)itemView.findViewById(R.id.txt_date_order);
            bt_order = (Button)itemView.findViewById(R.id.btn_order);
            imageView = (ImageView)itemView.findViewById(R.id.imageView_order);
        }
    }
}
