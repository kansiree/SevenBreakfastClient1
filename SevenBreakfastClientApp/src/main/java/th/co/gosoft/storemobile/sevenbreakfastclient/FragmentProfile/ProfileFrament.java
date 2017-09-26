package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentProfile;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import th.co.gosoft.storemobile.sevenbreakfastclient.Adapter.Adapter_Profile;
import th.co.gosoft.storemobile.sevenbreakfastclient.Connect_webservice;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.mapData;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.ModelProfile;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFrament extends Fragment {
    RecyclerView listShow_acc;
    TextView txt_ver;
    Button btn_logOut;
    ImageView image_acc;
    TextView user_name,store, how, terms, privacy;
    int count = 0;
    public ProfileFrament() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.setting,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        load();
        TextView textView = (TextView)getActivity().findViewById(R.id.txt_toolbar);
        textView.setText("Profile");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return inflater.inflate(R.layout.fragment_profile_frament, container, false);
    }




    void load(){
        image_acc = (ImageView) getActivity().findViewById(R.id.imageView_acc);
        image_acc.setBackgroundResource(R.drawable.ic_profile);
        user_name = (TextView) getActivity(). findViewById(R.id.txt_name_acc);
        user_name.setText("Mr.Newyear");
        btn_logOut = (Button) getActivity(). findViewById(R.id.btn_LogOut_acc);
        txt_ver = (TextView) getActivity(). findViewById(R.id.txt_ver_acc);
        txt_ver.setText("Seven Breakfast Version 1.0.0");

        listShow_acc = (RecyclerView) getActivity(). findViewById(R.id.listView_store_acc);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        listShow_acc.setHasFixedSize(true);
        listShow_acc.setLayoutManager(layoutManager);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        mapData res = (mapData) msg.obj;
                        List<ModelProfile> list = new ArrayList<>();
                        if(res!=null){
                            ModelProfile data ;
                            if(res.getListMap()!=null&&res.getListMap().size()>0){
                                for (mapData.MapData item : res.getListMap()){
                                    data = new ModelProfile();
                                    AFLogUtils.i("Name: " + item.getName() + "latiture: " + item.getLatiture() + "longtiture: " + item.getLongtiture());
                                    data.setImage(R.drawable.store);
                                    data.setStoreList(item.getName());
                                    System.out.println("item: "+item);

                                    list.add(data);
                                }
                                System.out.println("list "+list.size());
                                Adapter_Profile adapter_profile = new Adapter_Profile(getActivity(),list);
                                listShow_acc.setAdapter(adapter_profile);
                            }
                        }
                        break;
                    case 1:
                        break;
                }
            }
        };new Connect_webservice().Map(handler);

        store = (TextView) getActivity().findViewById(R.id.txt_store_acc);
        store.setText("7-Eleven Location");
        how = (TextView)getActivity(). findViewById(R.id.txt_how_to_acc);
        how.setText("How to use");
        terms = (TextView) getActivity().findViewById(R.id.txt_condition_acc);
        terms.setText("Terms and Condition of Use");
        privacy = (TextView) getActivity().findViewById(R.id.txt_policy_acc);
        privacy.setText("Privacy Policy");
    }
}
