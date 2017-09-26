package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentSevenbereakfast;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import th.co.gosoft.storemobile.sevenbreakfastclient.Adapter.Adapter_promotion_Detail;
import th.co.gosoft.storemobile.sevenbreakfastclient.Connect_webservice;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerProductById;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.modelDetail;


/**
 * A simple {@link Fragment} subclass.
 */
public class Detail extends Fragment {

    RecyclerView list;
    Adapter_promotion_Detail adapter_promotion_detail;
    ImageView picture;
    ProgressBar progressBar ;
    int count = 0;
    int ProductID;
    public Detail(int productID) {
        this.ProductID = productID;
        System.out.println("ProductID " +ProductID);
    }

    private List<modelDetail> Data() {
        String[] leftSide = {"รายละเอียดสินค้า", "ส่วนประกอบ", "ปริมาณที่ควรบริโภค", "โปรโมชั่น ณ ตอนนี้", "00", "00","รายละเอียดสินค้า", "ส่วนประกอบ", "ปริมาณที่ควรบริโภค", "โปรโมชั่น ณ ตอนนี้", "00", "00","รายละเอียดสินค้า", "ส่วนประกอบ", "ปริมาณที่ควรบริโภค", "โปรโมชั่น ณ ตอนนี้", "00", "00","รายละเอียดสินค้า", "ส่วนประกอบ", "ปริมาณที่ควรบริโภค", "โปรโมชั่น ณ ตอนนี้", "00", "00","รายละเอียดสินค้า", "ส่วนประกอบ", "ปริมาณที่ควรบริโภค", "โปรโมชั่น ณ ตอนนี้", "00", "00"};
        String[] rightSide = {"", "ไก่ ไข่ ปลา แมว", "1 ชิ้นต่อวันก็พอแล้ว", "-", "00", "00","", "ไก่ ไข่ ปลา แมว", "1 ชิ้นต่อวันก็พอแล้ว", "-", "00", "00","", "ไก่ ไข่ ปลา แมว", "1 ชิ้นต่อวันก็พอแล้ว", "-", "00", "00","", "ไก่ ไข่ ปลา แมว", "1 ชิ้นต่อวันก็พอแล้ว", "-", "00", "00","", "ไก่ ไข่ ปลา แมว", "1 ชิ้นต่อวันก็พอแล้ว", "-", "00", "00"};
        List<modelDetail> list = new ArrayList<modelDetail>();
        while (leftSide.length!=count){
            modelDetail modelDetail = new modelDetail(leftSide[count],rightSide[count]);
            list.add(modelDetail);
            count++;
        }

        return list;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content,new SevenBreakfastFragment(getActivity())).commit();
                System.out.println("Back");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textView = (TextView)getActivity().findViewById(R.id.txt_toolbar);
        textView.setText("Detail Product");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        picture = (ImageView)getActivity().findViewById(R.id.imageView);
       // picture.setImageResource(R.drawable.pizza);
        progressBar = (ProgressBar)getActivity().findViewById(R.id.loadpicture_detail);
        String Url = "http://www.load2up.com/wp-content/uploads/2016/11/conan.movie_.20.jpg";

        Glide.with(getActivity()).load(Url).centerCrop().into(picture);

        progressBar.setVisibility(View.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(0xF0BEBEBE, android.graphics.PorterDuff.Mode.MULTIPLY);
        list = (RecyclerView)getActivity().findViewById(R.id.pro_detail_listview);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter_promotion_detail = new Adapter_promotion_Detail(getActivity(),Data());
        list.setAdapter(adapter_promotion_detail);
        Button button = (Button)getActivity().findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what){
                            case 0:
                                CustomerProductById res = (CustomerProductById)msg.obj;
                                String status = res.getStatus();
                                System.out.println(res.getStatus()+"  status   "+ProductID);
                                if(status.equalsIgnoreCase("success")){
                                    Toast.makeText(getContext(),"Add success",Toast.LENGTH_SHORT).show();
                                }
                                else if(status.equalsIgnoreCase("fail")){
                                    final Dialog dialog = new Dialog(getContext());
                                    dialog.setContentView(R.layout.custom_dialog_order);
                                    Button diButton = (Button) dialog.findViewById(R.id.bt_dialog);
                                    TextView dialogtext=(TextView)dialog.findViewById(R.id.text_dialog);
                                    dialogtext.setText("คุณได้เพิ่มสินค้าชิ้นนี้แล้ว");
                                    diButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();

                                        }
                                    });
                                    dialog.show();
                                }
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content,new SevenBreakfastFragment(getActivity())).commit();

                                break;
                            case 1:
                                break;
                        }
                    }
                };
                new Connect_webservice().ProductByID(handler,ProductID);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

}
