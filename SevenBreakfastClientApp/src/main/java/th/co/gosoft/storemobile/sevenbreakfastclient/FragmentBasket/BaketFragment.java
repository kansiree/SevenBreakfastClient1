package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentBasket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.gosoft.android.framework.lib.dialog.AFInfoDialog;
import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import th.co.gosoft.storemobile.sevenbreakfastclient.Adapter.Adapter_Baket;
import th.co.gosoft.storemobile.sevenbreakfastclient.Connect_webservice;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerBasket;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.ModelData;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.mapModel;


public class BaketFragment extends Fragment {

    TextView totalprice ;
    TextView total;
    RecyclerView recyclerView;
    Adapter_Baket adapter_baket;
    List<ModelData> DataRow;
    boolean listbasket=false;
    mapModel data ;


    public int price = 0;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Button check = (Button)getActivity().findViewById(R.id.check);
        TextView textView = (TextView)getActivity().findViewById(R.id.txt_toolbar);
        total  = (TextView)getActivity().findViewById(R.id.txt_total_list);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        textView.setText("Basket");

// detec action ClickListener go to MapActivity

        final List<String> name = new ArrayList<String>();
        final List<String> Latitude = new ArrayList<>();
        final List<String> Longtitude = new ArrayList<>();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listbasket){
                    Intent intent = new Intent(getActivity(),Map.class);
                    startActivity(intent);
                    getActivity().finish();
                }else {
                    AFInfoDialog dialog = new AFInfoDialog(getActivity(), null);
                    dialog.setText("กรุณาเลือกสินค้า");
                    dialog.show();
                }
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view =inflater.inflate(R.layout.fragment_baket, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.List_Baket);
        recyclerView.setHasFixedSize(true);
        totalprice = (TextView)view.findViewById(R.id.total);

        // set LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case 0:
                        final CustomerBasket resOrder = (CustomerBasket) msg.obj;
                        if (resOrder != null) {
                            AFLogUtils.i("Return Code Basket : " + resOrder.getReturnCode() + ", Count : " );
                            if (resOrder.getBasketLists() != null && resOrder.getBasketLists().size() > 0) {
                                DataRow = new ArrayList<>();
                                ModelData name;
                                for (CustomerBasket.BasketListModel item : resOrder.getBasketLists()) {
                                    AFLogUtils.i("Title : " + item.getTitle() + ", Body : " + item.getTitle() + ", Icon88 : " + item.getValue());
                                    name = new ModelData();
                                    name.setName(item.getTitle());
                                    name.setPrice(item.getPrice());
                                    name.setValue(item.getValue());
                                    name.setImage(item.getImage());
                                    name.setProductId(item.getProductId());
                                    DataRow.add(name);
                                }
                                adapter_baket = new Adapter_Baket(getActivity(), DataRow, totalprice, total);
                                recyclerView.setAdapter(adapter_baket);
                                listbasket=true;
                            }
                            else {
                                listbasket=false;
                            }
                        }
                        break;
                    case 1:
                        break;
                }
            }
        };
        new Connect_webservice().Basket(handler);
        return view;
    }
}
