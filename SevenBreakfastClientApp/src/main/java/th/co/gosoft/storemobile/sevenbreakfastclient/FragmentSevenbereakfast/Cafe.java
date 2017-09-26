package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentSevenbereakfast;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.DataManager;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerProductList;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cafe extends Fragment {

    RecyclerView List_Cafe;
    Adapter_promotion adapter;
    Activity activity;
    public Cafe(Activity activity) {
        // Required empty public constructor
        this.activity=activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cafe, container, false);
        TextView txt_toolBar = (TextView)getActivity().findViewById(R.id.txt_toolbar);
        txt_toolBar.setText("SevenBreakfast");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        List_Cafe = (RecyclerView)view.findViewById(R.id.List_cafe);
        List_Cafe.setHasFixedSize(true);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        List_Cafe.setLayoutManager(mLayoutManager);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        final CustomerProductList resOrder = (CustomerProductList) msg.obj;
                        if(resOrder!=null){
                            if(resOrder.getProductList() != null&& resOrder.getProductList().size()>0){
                                if (resOrder.getProductList()!= null && resOrder.getProductList().size()>0){
                                    List<Model> DataRow = new ArrayList<>();
                                    Model name ;
                                    for (CustomerProductList.ProductListModel item : resOrder.getProductList()){
                                        if(item.getCategoryId().equals("3")){
                                            AFLogUtils.i("Title2 : "+item.getTitle()+", Body2 : "+item.getBody()+", Icon2 : "+item.getIcon());
                                            name = new Model();
                                            name.setText_name(item.getTitle());
                                            name.setText_price(item.getPrice());
                                            name.setProductId(item.getProductId());

                                            String Url = "https://cartoonth12.com/wp-content/uploads/2016/11/Conan-%E0%B9%82%E0%B8%84%E0%B8%99%E0%B8%B1%E0%B8%99.jpg";
                                            name.setImageId(item.getIcon());
                                            DataRow.add(name);
                                        }
                                    }
                                    adapter = new Adapter_promotion(getActivity(),DataRow);
                                    adapter.setClickItem(new Adapter_promotion.onClickItem() {
                                        @Override
                                        public void onItemClick(View view, int position, int productID) {
                                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                            SevenBreakfastFragment.pos=2;
                                            transaction.replace(R.id.content,new Detail(productID));
                                            transaction.commit();
                                        }
                                    });
                                    List_Cafe.setAdapter(adapter);
                                }
                            }
                        }
                        else {
                            AFInfoDialog dialog = new AFInfoDialog(activity,null);
                            dialog.setText("ไม่มีข้อมูล");
                            dialog.setBt_yes_name("ตกลง");
                            dialog.show();
                        }

                        break;
                    case 1:
                        AFInfoDialog dialog = new AFInfoDialog(activity,null);
                        dialog.setText("ไม่สามารถเชื่อมต่อ sever ได้");
                        dialog.setBt_yes_name("ตกลง");
                        dialog.show();
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String imei = "222";
                    String macAddress = "x1x1";
                    String userId = "1";

                    CustomerProductList res = DataManager.getInstance().customerProductList(imei,macAddress,userId);

                    Message message = new Message();
                    message.obj = res;
                    message.what =0;
                    handler.sendMessage(message);
                }
                catch (Throwable er){
                    Message message = new Message();
                    message.what = 1;
                    message.obj = er.toString();
                    handler.sendMessage(message);
                }
            }
        }).start();
        return view;
    }

}
