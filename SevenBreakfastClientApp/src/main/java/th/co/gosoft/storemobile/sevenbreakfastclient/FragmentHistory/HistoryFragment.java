package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentHistory;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.gosoft.storemobile.sevenbreakfastclient.Adapter.Aadapter_History_Order;
import th.co.gosoft.storemobile.sevenbreakfastclient.Connect_webservice;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerHistory;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.ModelOrder;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
     RecyclerView recyclerViewHistory;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        final Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Order CustomerHistory");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        TextView textView = (TextView)getActivity().findViewById(R.id.txt_toolbar);
        textView.setText("Order CustomerHistory");
        recyclerViewHistory = (RecyclerView)view.findViewById(R.id.List_history);
        recyclerViewHistory.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewHistory.setLayoutManager(mLayoutManager);


        Handler handler =new Handler(){
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what){
                    case 0:
                        CustomerHistory history = (CustomerHistory)msg.obj;
                        if(history!=null){
                            if(history.getReceiptList()!=null&&history.getReceiptList().size()>0){
                                List<ModelOrder> orders = new ArrayList<>();
                                ModelOrder modelOrder;
                                for (CustomerHistory.DatafileReceipt.Data item:history.getReceiptList()){
                                    modelOrder = new ModelOrder();
                                    modelOrder.setDate(item.getDate());
                                    modelOrder.setNo_order(item.getReceipt());
                                    modelOrder.setStatus("Complete");
                                    orders.add(modelOrder);
                                }
                                Aadapter_History_Order adapter_history_order = new Aadapter_History_Order(getActivity(),orders);
                                recyclerViewHistory.setAdapter(adapter_history_order);
                            }
                        }
                        break;
                    case 1:
                        break;
                }
            }
        };
        new Connect_webservice().History(handler,"print");
        return view;
    }


}
