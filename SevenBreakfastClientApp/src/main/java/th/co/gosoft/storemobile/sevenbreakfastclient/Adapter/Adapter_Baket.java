package th.co.gosoft.storemobile.sevenbreakfastclient.Adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
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

import com.bumptech.glide.Glide;

import java.util.List;

import th.co.gosoft.storemobile.sevenbreakfastclient.Connect_webservice;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.ModelData;

/**
 * Created by print on 4/19/2017.
 */

public class Adapter_Baket extends RecyclerView.Adapter<Adapter_Baket.customAapter> {

    int num_product = 0 ,price=0;
    Context context;
    TextView pricetotal;
    TextView listProduct;
    static List<ModelData> model;
    int []productId ;

    public Adapter_Baket(Context context,List<ModelData> model,TextView textView,TextView listProduct){
        this.context=context;
        this.model = model;
        this.pricetotal = textView;
        this.listProduct = listProduct;
        productId = new int [model.size()];
    }
    public  Adapter_Baket(){}
    @Override
    public customAapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_basket,parent, false);
        Adapter_Baket.customAapter customAapter = new Adapter_Baket.customAapter(view);
        return customAapter;
    }

    @Override
    public void onBindViewHolder(final customAapter holder, final int position) {

        final ModelData modelData = model.get(position);
        num_product = modelData.getValue();
        Glide.with(context).load(modelData.getImage()).into(holder.imageView);
        AnimatorSet animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.feedin);
        animationSet.setTarget(holder.imageView);
        animationSet.start();
        // set show ProcessBar
        holder.load.setVisibility(View.VISIBLE);
        // set color ProcessBar
        holder.load.getIndeterminateDrawable().setColorFilter(0xF0BEBEBE, android.graphics.PorterDuff.Mode.MULTIPLY);

        holder.name_product.setText(modelData.getName());
        holder.value_product.setText(modelData.getValue()+"");
        holder.price_product.setText(modelData.getPrice()+"");
        if(modelData.getProductId()!=0){
            productId[position] = modelData.getProductId();
        }

        // calculat total price product
        price += modelData.getValue()*modelData.getPrice();

        // set price
        pricetotal.setText(price+"");
        listProduct.setText(getItemCount()+"");

        // set Positive Product
        holder.add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setnumProduct(modelData.getValue());
                price=0;
                num_product += 1 ;
                edit(productId[position],num_product,position,modelData);
            }
        });

        // set Nagative Product
        holder.nagtive_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setnumProduct(modelData.getValue());
                price=0;
                if(modelData.getValue()>1){
                    num_product -= 1;
                    edit(productId[position],num_product,position,modelData);
                }
                // show dialog when num product same one
                else {
                    final Dialog dialog = new Dialog(context);
                    num_product = 1;

                    dialog.setContentView(R.layout.custom_dialog_order);
                    dialog.show();
                    Button diButton = (Button) dialog.findViewById(R.id.bt_dialog);
                    TextView dialogtext=(TextView)dialog.findViewById(R.id.text_dialog);
                    dialogtext.setText("ไม่สามารถลดการสั่งสินค้าได้");
                    diButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });

        //  clear Product
        holder.bt_clear_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price = 0;
                edit(productId[position],0,position,modelData);
            }
        });
    }

    private int setnumProduct(int value){
        num_product = value;
        return num_product;
    }

    @Override
    public int getItemCount() {
        num_product=model.size();
        return model.size();
    }

    private void remove(int position){
        model.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        if (model.isEmpty()){
            pricetotal.setText(String.valueOf(price));
            listProduct.setText("0");
        }
    }

    public void chang(int position,ModelData modelData){
        ModelData data = new ModelData();
        data.setName(modelData.getName());
        data.setImage(modelData.getImage());
        data.setPrice(modelData.getPrice());
        data.setValue(num_product);
        model.set(position,data);
    }

    private void edit(final int Product, final int Value , final int position , final ModelData modelData ){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:{
                        if(Value>0){
                            chang(position,modelData);
                            notifyDataSetChanged();

                        }
                        else if(Value==0){
                            remove(position);
                        }
                    }
                    break;
                    case 1:{

                    }
                    break;
                }
            }
        };
        new Connect_webservice().EditBasket(handler,Product,Value);
    }


    public static class customAapter extends RecyclerView.ViewHolder{
        ImageView imageView;
        Button bt_clear_product,add_product,nagtive_product;
        TextView name_product,value_product,price_product;
        CardView rowdata;
        ProgressBar load;
        public customAapter(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView_bk);
            name_product = (TextView)itemView.findViewById(R.id.txt_name_bk);
            value_product = (TextView)itemView.findViewById(R.id.txt_value_bk);
            price_product = (TextView)itemView.findViewById(R.id.txt_price_bk);
            bt_clear_product = (Button)itemView.findViewById(R.id.btn_clear_bk);
            add_product = (Button)itemView.findViewById(R.id.btn_add_bk);
            nagtive_product = (Button)itemView.findViewById(R.id.btn_notadd_bk);
            rowdata = (CardView)itemView.findViewById(R.id.row);
            load = (ProgressBar)itemView.findViewById(R.id.progressBar4);
        }
    }
}