package th.co.gosoft.storemobile.sevenbreakfastclient.Adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import th.co.gosoft.storemobile.sevenbreakfastclient.Connect_webservice;
import th.co.gosoft.storemobile.sevenbreakfastclient.MainActivity;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerProductById;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.Model;

/**
 * Created by print on 4/7/2017.
 */

public class Adapter_promotion extends RecyclerView.Adapter<Adapter_promotion.customRecycleView> {

    Context context;
    List<Model> data;
    onClickItem item;

    public Adapter_promotion(Context context, List<Model> Data) {
        this.context = context;
        this.data = Data;
    }

    public interface onClickItem{
        void onItemClick(View view,int position,int productID);
    }
    // set item click
    public void setItem(Adapter_promotion.onClickItem item){this.item = item;}
    // return click item give position and view
    public Adapter_promotion.onClickItem geItem(){return item; }
    // detec OnClick in Recycle view
    public void setClickItem(Adapter_promotion.onClickItem item){setItem(item);}

    @Override
    public customRecycleView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_promotion, parent, false);
        Adapter_promotion.customRecycleView view1 = new Adapter_promotion.customRecycleView(view);
        return view1;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(final customRecycleView holder, final int position) {
        final Model model = data.get(position);
        holder.txt_name.setText(model.getText_name());
        holder.txt_price.setText(model.getText_price());

        // load picture from HQ
        Glide.with(context)
                .load(model.getImageId()) // start load
                .centerCrop() // show picture center of layout
                .into(holder.imageView); // show picture in imageView

        AnimatorSet animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.feedin);
        animationSet.setTarget(holder.imageView);
        animationSet.start();

        // set show ProcessBar
        holder.load.setVisibility(View.VISIBLE);
        // set color ProcessBar
        holder.load.getIndeterminateDrawable().setColorFilter(0xF0BEBEBE, android.graphics.PorterDuff.Mode.MULTIPLY);
        // add product in basket
        holder.add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what){
                            case 0:
                                CustomerProductById res = (CustomerProductById) msg.obj;
                                System.out.println(" status : "+res.getStatus());
                                String status = res.getStatus();
                                if(status.equals("success")){
                                    MainActivity.mainListener.loadbasket();
                                    Toast.makeText(context, "Add success ", Toast.LENGTH_SHORT).show();
                                }
                                else if(status.equals("fail")){
                                    final Dialog dialog = new Dialog(context);
                                    dialog.setContentView(R.layout.custom_dialog_order);
                                    Button diButton = (Button) dialog.findViewById(R.id.bt_dialog);
                                    TextView dialogtext=(TextView)dialog.findViewById(R.id.text_dialog);
                                    dialogtext.setText("คุณได้เพิ่มสินค้าชิ้นนี้แล้ว");
                                    diButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();
                                }
                                break;
                            case 1 :
                                break;
                        }
                    }
                };
                new Connect_webservice().ProductByID(handler,model.getProductId());
            }
        });

        // detec OnClick tranfer view and position
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geItem().onItemClick(v,position,model.getProductId());
            }
        });

    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class customRecycleView extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txt_name, txt_price;
        ImageView imageView;
        Button add_menu;
        ProgressBar load;

        public customRecycleView(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.row);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name_pro);
            txt_price = (TextView) itemView.findViewById(R.id.txt_price_pro);
            imageView = (ImageView) itemView.findViewById(R.id.imageView_pro);
            add_menu = (Button) itemView.findViewById(R.id.btn_add_dt2);
            load = (ProgressBar) itemView.findViewById(R.id.progressBar2);
        }
    }
}




