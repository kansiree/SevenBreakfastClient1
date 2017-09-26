package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentHistory;


import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import th.co.gosoft.android.framework.lib.zxing.BarcodeFormat;
import th.co.gosoft.android.framework.lib.zxing.WriterException;
import th.co.gosoft.android.framework.lib.zxing.common.BitMatrix;
import th.co.gosoft.android.framework.lib.zxing.qrcode.QRCodeWriter;
import th.co.gosoft.storemobile.sevenbreakfastclient.Adapter.Adapter_ItemHistory;
import th.co.gosoft.storemobile.sevenbreakfastclient.Connect_webservice;
import th.co.gosoft.storemobile.sevenbreakfastclient.MainActivity;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerHistory;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerListOrderHistory;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.database.DatabaseManager;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.ModelItemHistory;

public class historyorderDetail extends Fragment{

    TextView textView,no,name_store,date,status,items,remain;
    Button bt_quuic;
    String receipt;
    ImageView barcode;
    RecyclerView recyclerView;
    Adapter_ItemHistory adapterItemHistory;
    public historyorderDetail(String receipt) {
        this.receipt=receipt;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content,new HistoryFragment()).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String calTime(int Time,int TimeSelect){
        int Hour = 0;
        int min = 0;

        String TimeReturn = "";
        Hour = Math.abs(Time/10000);
        min = Math.abs((Time/100)%100);

        switch (TimeSelect){
            case 1:
                Hour=Hour+1;
                break;
            case 15:
                min = min+15;
                if(min>=60){
                    Hour = Hour+1;
                    min = min%60;
                }

                break;
            case 30:
                min = min+30;
                if(min>=60){
                    Hour = Hour+1;
                    min = min%60;
                }
                break;
        }
        TimeReturn =Hour+":"+min;
        System.out.println("TimeReturn: "+TimeReturn);
        return TimeReturn;
    }
    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        items.setText("kmlmk");
        bt_quuic = (Button)getActivity().findViewById(R.id.btn_quick_do);
        bt_quuic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("เลือกเวลารับสินค้า");
                final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_box,null);
                final Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
                Button bt_OK = (Button)view.findViewById(R.id.bt_OK);
                Button bt_cancel = (Button)view.findViewById(R.id.bt_cancel);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,
                        getResources().getStringArray(R.array.time));
                spinner.setAdapter(adapter);
                builder.setView(view);
                builder.create();
                final AlertDialog alertDialog = builder.show();

                bt_OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                        MainActivity.mainListener.onClick2History();
                        String timeRecipt="";
                        SimpleDateFormat format = new SimpleDateFormat("HHmmss");
                        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                        String Date = format1.format(new Date());
                        int time = Integer.parseInt(format.format(new Date()));
                        switch (spinner.getSelectedItem().toString()){
                            case "1 hour":
                                timeRecipt=calTime(time,1);
                                break;
                            case "15 min":
                                timeRecipt=calTime(time,15);
                                break;
                            case "30 min":
                                timeRecipt=calTime(time,30);
                                break;
                        }
                        System.out.println("timeRecipt: "+timeRecipt+" Date: "+Date);
                        Handler handler =new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                            }
                        };
                        new Connect_webservice().Receipt(handler,timeRecipt,Date,name_store.getText().toString(),"",no.getText().toString());
                        alertDialog.dismiss();
                    }
                });
                bt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        CustomerHistory history = (CustomerHistory)msg.obj;
                        if(history!=null){
                            if(history.getReceiptList()!=null&&history.getReceiptList().size()>0){
                                for (CustomerHistory.DatafileReceipt.Data item: history.getReceiptList()){
                                    if(receipt.equals(item.getReceipt())){
                                        items.setText(item.getValue());
                                        name_store.setText(item.getStore());
                                        no.setText(item.getReceipt());
                                        date.setText(item.getDate()+"\t"+item.getTime());
                                        generateQRcode(item.getReceipt(),barcode);
                                    }

                                }
                            }
                        }

                        break;
                    case 1:break;
                }
            }
        };
        // putData list product History
        new Connect_webservice().History(handler,"print");
        Handler handler1 = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        CustomerListOrderHistory orderHistory = (CustomerListOrderHistory)msg.obj;
                        if(orderHistory!=null){
                            if(orderHistory.getOrderlist()!=null&&orderHistory.getOrderlist().size()>0){
                                List<ModelItemHistory> list = new ArrayList<>();
                                ModelItemHistory itemHistory;
                                for (CustomerListOrderHistory.listOrderHistory item:orderHistory.getOrderlist()){
                                    AFLogUtils.i("ProductID: "+item.getProductID()+" Vale: "+item.getValue());
                                    new DatabaseManager(getContext()).InsertData(item);
                                    itemHistory = new ModelItemHistory();
                                    itemHistory.setImage(item.getPicture());
                                    itemHistory.setName(item.getTitle());
                                    itemHistory.setPrice(item.getPrice()+"");
                                    itemHistory.setValue(item.getValue()+"");
                                    list.add(itemHistory);
                                }
                                status.setText(orderHistory.getStatusOrder());

                                System.out.println("list.size()"+orderHistory.getOrderlist().size());
                                items.setText(orderHistory.getOrderlist().size()+"");
                                adapterItemHistory = new Adapter_ItemHistory(list,getContext());
                                recyclerView.setAdapter(adapterItemHistory);

                            }
                        }
                        break;
                    case 1:break;
                }
            }
        };
        new Connect_webservice().OrderHistory(handler1,receipt);
    }
// Gen QR Code
    private void generateQRcode(String text,ImageView image){
        int width = 400;
        int height = 400;
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE,width,height);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            System.out.println(text.toString());
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK: Color.WHITE);
                }
            }
            image.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        status = (TextView) view.findViewById(R.id.txt_tus);
        items = (TextView) view.findViewById(R.id.txt_items);
        remain = (TextView) view.findViewById(R.id.txt_remain);
        name_store = (TextView) view.findViewById(R.id.txt_store);
        no = (TextView) view.findViewById(R.id.txt_no);
        date = (TextView) view.findViewById(R.id.txt_date);
        barcode = (ImageView) view.findViewById(R.id.imageView2);

        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.getTitle(); //--------------------------------------------------**
        textView = (TextView)getActivity().findViewById(R.id.txt_toolbar);
        textView.setText("Order");//--------------------------------------------------**
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }
    @Override
    public void onStop() {
        super.onStop();
        new DatabaseManager(getContext()).DeleteData();
    }
}
