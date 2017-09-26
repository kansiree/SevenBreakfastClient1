package th.co.gosoft.storemobile.sevenbreakfastclient.Adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.ModelReViewOrder;

/**
 * Created by print on 4/21/2017.
 */

public class Adaptor_ReviewOrder extends RecyclerView.Adapter<Adaptor_ReviewOrder.customReviewOrder> {
    Context context;
    List<ModelReViewOrder> modelReViewOrders ;
    int total=0;
    TextView txt_total;
    public Adaptor_ReviewOrder(Context context,List<ModelReViewOrder> modelReViewOrders,TextView txt_total){
        System.out.println("size : "+modelReViewOrders.size());
        this.context = context;
        this.modelReViewOrders = modelReViewOrders;
        this.txt_total = txt_total;

    }
    @Override
    public customReviewOrder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptor_revieworder,parent,false);
        Adaptor_ReviewOrder.customReviewOrder adaptor_reviewOrder = new Adaptor_ReviewOrder.customReviewOrder(view);
        return adaptor_reviewOrder;
    }

    @Override
    public void onBindViewHolder(customReviewOrder holder, int position) {
        ModelReViewOrder order = modelReViewOrders.get(position);
        String Url = "http://www.load2up.com/wp-content/uploads/2016/11/conan.movie_.20.jpg";
        Glide.with(context)
                .load(order.getImage()) // start load
                .centerCrop() // show picture center of layout
                .into(holder.imageView_reviewOrder); // show picture in imageView

        total += order.getPrice()*order.getValue();
        txt_total.setText(total+"");
        AnimatorSet animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.feedin);
        animationSet.setTarget(holder.imageView_reviewOrder);
        animationSet.start();
        // set show ProcessBar
        holder.load.setVisibility(View.VISIBLE);
        // set color ProcessBar
        holder.load.getIndeterminateDrawable().setColorFilter(0xF0BEBEBE, android.graphics.PorterDuff.Mode.MULTIPLY);

        holder.txt_order_review.setText(order.getOrder());
        holder.txt_price_review.setText(order.getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return modelReViewOrders.size();
    }

    public static class customReviewOrder extends RecyclerView.ViewHolder{
       TextView txt_order_review,txt_price_review;
       ImageView imageView_reviewOrder;
       ProgressBar load;
       public customReviewOrder(View itemView) {
           super(itemView);
           txt_order_review = (TextView)itemView.findViewById(R.id.txt_order_ro);
           txt_price_review = (TextView)itemView.findViewById(R.id.txt_price_ro);
           imageView_reviewOrder = (ImageView)itemView.findViewById(R.id.imageView_ro);
           load = (ProgressBar) itemView.findViewById(R.id.progressBar3);
       }
   }
}