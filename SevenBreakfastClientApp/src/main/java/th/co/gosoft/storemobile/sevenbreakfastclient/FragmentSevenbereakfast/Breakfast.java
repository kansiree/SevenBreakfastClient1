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
public class Breakfast extends Fragment {

    RecyclerView list_Brekfast;
    Adapter_promotion adapter;
    Activity activity;
    public Breakfast(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_breakfast, container, false);
        TextView textView = (TextView)getActivity().findViewById(R.id.txt_toolbar);
        textView.setText("SevenBreakfast");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        list_Brekfast = (RecyclerView)view.findViewById(R.id.list_breakfast);
        list_Brekfast.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        list_Brekfast.setLayoutManager(mLayoutManager);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        final CustomerProductList resOrder = (CustomerProductList)msg.obj;
                        if(resOrder!=null){
                            if(resOrder.getProductList() != null&& resOrder.getProductList().size()>0){
                                if (resOrder.getProductList()!= null && resOrder.getProductList().size()>0){
                                    List<Model> DataRow = new ArrayList<>();
                                    Model name ;
                                    for (CustomerProductList.ProductListModel item : resOrder.getProductList()){
                                        if (item.getCategoryId().equals("2")){
                                            AFLogUtils.i("Title1 : "+item.getTitle()+", Body1 : "+item.getBody()+", Icon1 : "+item.getIcon());
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
                                            SevenBreakfastFragment.pos=1;
                                            transaction.replace(R.id.content,new Detail(productID));
                                            transaction.commit();
                                        }
                                    });
                                    list_Brekfast.setAdapter(adapter);
                                }
                            }
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
                Message message = new Message();
                try {

                    String imei = "78926";
                    String macadress = "523";
                    String userId = "1";

                    CustomerProductList res = DataManager.getInstance().customerProductList(imei,macadress,userId);
                    message.what = 0;
                    message.obj = res;
                    handler.sendMessage(message);
                }
                catch (Throwable er){
                    message.obj = er.toString();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        }).start();
        return view;
    }

}
