package th.co.gosoft.storemobile.sevenbreakfastclient.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import th.co.gosoft.storemobile.sevenbreakfastclient.model.ModelProfile;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;

/**
 * Created by print on 4/21/2017.
 */

public class Adapter_Profile extends RecyclerView.Adapter<Adapter_Profile.customProfile>  {

    Context context;
    List<ModelProfile> modelProfiles;
    public Adapter_Profile(Context context,List<ModelProfile> modelProfiles){
        this.context = context;
        this.modelProfiles = modelProfiles;
    }
    @Override
    public customProfile onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_profile,parent,false);
        Adapter_Profile.customProfile customProfile = new Adapter_Profile.customProfile(view);
        return customProfile;
    }

    @Override
    public void onBindViewHolder(customProfile holder, int position) {
        ModelProfile modelProfile = modelProfiles.get(position);
        holder.nostore.setText(modelProfile.getNoStore());
        holder.storelist.setText(modelProfile.getStoreList());
        holder.imageView.setImageResource(modelProfile.getImage());
    }

    @Override
    public int getItemCount() {
        return modelProfiles.size();
    }

    public static class customProfile extends RecyclerView.ViewHolder{
        TextView nostore,storelist;
        ImageView imageView;
        CardView cardView;

        public customProfile(View itemView) {
            super(itemView);
            nostore = (TextView)itemView.findViewById(R.id.txt_NoStore_acc);
            storelist = (TextView)itemView.findViewById(R.id.txt_storeList_acc);
            imageView = (ImageView)itemView.findViewById(R.id.image_store_acc);
            cardView = (CardView)itemView.findViewById(R.id.card_Profile);
        }
    }

}
