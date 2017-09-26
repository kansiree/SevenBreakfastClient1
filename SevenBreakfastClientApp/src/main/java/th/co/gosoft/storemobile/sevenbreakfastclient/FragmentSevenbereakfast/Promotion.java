package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentSevenbereakfast;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.gosoft.android.framework.lib.dialog.AFInfoDialog;
import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import th.co.gosoft.storemobile.sevenbreakfastclient.Adapter.Adapter_promotion;
import th.co.gosoft.storemobile.sevenbreakfastclient.Connect_webservice;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerProductList;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class Promotion extends Fragment {

    RecyclerView listShow_pro ;
    Adapter_promotion adapter_promotion ;
    Activity activity;

    public Promotion(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promotion, container, false);

        final TextView textView = (TextView)getActivity().findViewById(R.id.txt_toolbar);
        textView.setText("SevenBreakfast");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        listShow_pro = (RecyclerView)view.findViewById(R.id.list_pro);
        listShow_pro.setHasFixedSize(true);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listShow_pro.setLayoutManager(mLayoutManager);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        final CustomerProductList resOrder = (CustomerProductList) msg.obj;
                        if(resOrder!=null){
                         //  AFLogUtils.i("Return Code : "+resOrder.getReturnCode()+", Count : "+resOrder.getCsProductList().size());
                            if(resOrder.getProductList() != null&& resOrder.getProductList().size()>0){
                                    if (resOrder.getProductList()!= null && resOrder.getProductList().size()>0){
                                        List<Model> DataRow = new ArrayList<>();
                                    Model name ;
                                    for (CustomerProductList.ProductListModel item : resOrder.getProductList()){
                                        if(item.getCategoryId().equals("1")){
                                            AFLogUtils.i("Title : "+item.getTitle()+", Body : "+item.getBody()+", Icon : "+item.getIcon()+" ProductId : "+item.getProductId());
                                            name = new Model();
                                            name.setText_name(item.getTitle());
                                            name.setText_price(item.getPrice());
                                            name.setProductId(item.getProductId());

                                            name.setImageId(item.getIcon());
                                            DataRow.add(name);
                                        }

                                    }
                                        adapter_promotion = new Adapter_promotion(getActivity(),DataRow);
                                        adapter_promotion.setClickItem(new Adapter_promotion.onClickItem() {
                                            @Override
                                            public void onItemClick(View view, int position, int productID) {
                                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                                transaction.replace(R.id.content,new Detail(productID));
                                                SevenBreakfastFragment.pos=0;
                                                transaction.commit();
                                            }
                                        });
                                        listShow_pro.setAdapter(adapter_promotion);
                                    }
                                }
                            }

                        break;
                    case 1:
                        AFInfoDialog dialog = new AFInfoDialog(activity,null);
                        dialog.setText("ไม่สามารถเชื่อมต่อ sever ได้   "+msg.obj.toString());

                        dialog.setBt_yes_name("ตกลง");
                        dialog.show();
                        break;
                }
            }
        };
        new Connect_webservice().ProductList(handler);
        return view;
    }

}


