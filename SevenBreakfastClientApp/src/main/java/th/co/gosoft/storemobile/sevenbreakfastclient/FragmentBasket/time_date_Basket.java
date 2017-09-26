package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentBasket;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import th.co.gosoft.storemobile.sevenbreakfastclient.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class time_date_Basket extends Fragment {
    TextView Time,Date, sh_date, sh_time;
    Button next;
    static String  Hour,min,Day,Mon,Year;

    static String strTime,strDate;
    static String store;

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    time_date_Basket context ;

    public time_date_Basket() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        store = Map.callMap.Store();
        System.out.println("store: "+store +" onActivityCreated");
        next = (Button)getActivity().findViewById(R.id.next);
        Date = (TextView)getActivity().findViewById(R.id.Date);
        sh_date = (TextView)getActivity().findViewById(R.id.sh_date);
        Time = (TextView)getActivity().findViewById(R.id.Time);
        sh_time = (TextView)getActivity().findViewById(R.id.sh_time);
        // set icon button back
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
        // show button back
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //+++++++++++++++++++++++++++++++++  setDate   +++++++++++++++++++++++++++++++++++++++++++++
        Day = getDay();
        Mon = getMon();
        Year = getYear();
        if(Day != null&&Mon != null&&Year != null){
            strDate = Day+"/"+Mon+"/"+Year;
            sh_date.setText(strDate);
            System.out.println(Mon+"  mon");
        }
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transactionDate = getFragmentManager().beginTransaction();
                transactionDate.replace(R.id.time,new selectDateBasket());
                transactionDate.commit();
            }
        });
        //+++++++++++++++++++++++++++++++++  setTime   +++++++++++++++++++++++++++++++++++++++++++++
        Hour = getHour();
        min = getMin();
        if(Hour!=null&&min!=null){
            if(min.length()==1){
                min="0"+getMin();
            }
            strTime = getHour()+":"+getMin();
            sh_time.setText(strTime);
        }
        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transactionTime = getFragmentManager().beginTransaction();
                transactionTime.replace(R.id.time, new selectTimeBasket());
                transactionTime.commit();
            }
        });

        //+++++++++++++++++++++++++++++++++++  checkout  ++++++++++++++++++++++++++++++++++
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Hour!=null&&min!=null&&Day!=null&&Mon!=null&&Year!=null){

                    setStrDate(strDate);
                    setStrTime(strTime);
                    setStore(store);
                    FragmentTransaction transactionOrder = getFragmentManager().beginTransaction();
                    transactionOrder.replace(R.id.time,new ReviewOrderBasket());
                    transactionOrder.commit();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("sevenbreakfast");
                    builder.setCancelable(true);
                    builder.setMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                       }
                   });
                    builder.create().show();
                }

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.cancel,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // set click button back goto next page
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(getActivity(),new Map().getClass());
                startActivity(intent);
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_time_date__basket, container, false);
    }

    public void setHour (int Hour){
        this.Hour = Hour+"";
    }
    public void setMin(int min){
        this.min = min+"";
    }
    public void setYear(String year){
        this.Year = year;
    }
    public void setmon(String mon){
        this.Mon = mon;
    }
    public String getHour(){
        return Hour;
    }
    public void setDay(String day){
        this.Day = day;
    }

    public String getMin(){
        return min;
    }
    public String getDay(){
        return Day;
    }
    public String getMon(){
        return Mon;
    }
    public String getYear(){
        return Year;
    }
}
