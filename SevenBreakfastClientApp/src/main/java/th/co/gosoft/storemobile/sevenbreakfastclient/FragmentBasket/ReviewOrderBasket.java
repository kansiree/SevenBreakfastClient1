package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentBasket;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import th.co.gosoft.storemobile.sevenbreakfastclient.Adapter.Adaptor_ReviewOrder;
import th.co.gosoft.storemobile.sevenbreakfastclient.Connect_webservice;
import th.co.gosoft.storemobile.sevenbreakfastclient.MainActivity;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerBasket;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.Receipt;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.ModelReViewOrder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewOrderBasket extends Fragment {


    public ReviewOrderBasket() {
        // Required empty public constructor
    }
    RecyclerView listView;
    Adaptor_ReviewOrder adaptor_reviewOrder;
    public static String time,date,store,receipt;
    time_date_Basket data;
    static FragmentTransaction transaction;

    public static String getTime() {
        return time;
    }

    public static void setTime(String time) {
        ReviewOrderBasket.time = time;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        ReviewOrderBasket.date = date;
    }

    public static String getStore() {
        return store;
    }

    public static void setStore(String store) {
        ReviewOrderBasket.store = store;
    }

    public static String getreceipt() {
        return receipt;
    }

    public static void setreceipt(String receipt) {
        ReviewOrderBasket.receipt = receipt;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Review Order");
        // set icon button back
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
        // show button back
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (RecyclerView) getActivity().findViewById(R.id.list_ro);
        listView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(manager);
        listView.setAdapter(adaptor_reviewOrder);
        data = new time_date_Basket();
        setTime(data.getStrTime());
        setDate(data.getStrDate());
        setStore(data.getStore());

        final Connect_webservice obj = new Connect_webservice();
        TextView store = (TextView)getActivity().findViewById(R.id.txt_order_ro);
        TextView time = (TextView)getActivity().findViewById(R.id.txt_time_ro);
        final TextView total = (TextView)getActivity().findViewById(R.id.txt_sum_ro);

        store.setText(getStore());
        time.setText("Date : "+getDate()+"\n\nTime : "+getTime());
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        CustomerBasket res = (CustomerBasket) msg.obj;
                        if(res!=null){
                            List<ModelReViewOrder> list = new ArrayList<>();
                            ModelReViewOrder order;

                            if(res.getBasketLists()!=null&&res.getBasketLists().size()!=0){
                                for (CustomerBasket.BasketListModel item : res.getBasketLists()){
                                    AFLogUtils.i("Title : " + item.getTitle() + ", Body : " + item.getTitle() + ", Icon : " + item.getValue());

                                    order = new ModelReViewOrder();
                                    order.setPrice(item.getPrice());
                                    order.setOrder(item.getTitle());
                                    order.setImage(item.getImage());
                                    order.setValue(item.getValue());
                                    list.add(order);
                                }
                                adaptor_reviewOrder = new Adaptor_ReviewOrder(getActivity(),list,total);
                                listView.setAdapter(adaptor_reviewOrder);
                            }
                        }
                        break;
                    case 1:
                        break;
                }
            }
        };
        obj.Basket(handler);
        final Button confrim = (Button)getActivity().findViewById(R.id.btn_confirm_ro);
        transaction = getFragmentManager().beginTransaction();
        // detec Onclick go to Your Order  Basket Fragment
        confrim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Handler handler1 = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what){
                            case 0:
                                Receipt res = (Receipt) msg.obj;
                                setreceipt(res.getReceipt());
                                AFLogUtils.i("Reciept: "+getreceipt());
                                transaction.replace(R.id.time,new yourOrderBasket());
                                transaction.commit();
                                break;
                            case 1:break;
                        }
                    }
                };
                obj.Receipt(handler1,getTime(),getDate(),getStore(), String.valueOf(total.getText()),null);
                getActivity().getSupportFragmentManager().beginTransaction().remove(ReviewOrderBasket.this).commit();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cancel,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // set click button back goto next page
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(getActivity(),new TimeBasket().getClass());
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.bt_cancel:
                Intent intent1 = new Intent(getActivity(),new MainActivity().getClass());
                startActivity(intent1);
                getActivity().finish();
                MainActivity.setPage("Basket");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_review_order_basket, container, false);
    }
}
