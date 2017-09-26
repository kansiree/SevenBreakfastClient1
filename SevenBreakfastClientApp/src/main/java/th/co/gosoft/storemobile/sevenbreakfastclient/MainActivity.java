package th.co.gosoft.storemobile.sevenbreakfastclient;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import th.co.gosoft.android.framework.lib.dialog.AFInfoDialog;
import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import th.co.gosoft.storemobile.sevenbreakfastclient.FragmentBasket.BaketFragment;
import th.co.gosoft.storemobile.sevenbreakfastclient.FragmentHistory.HistoryFragment;
import th.co.gosoft.storemobile.sevenbreakfastclient.FragmentHistory.historyorderDetail;
import th.co.gosoft.storemobile.sevenbreakfastclient.FragmentProfile.ProfileFrament;
import th.co.gosoft.storemobile.sevenbreakfastclient.FragmentSevenbereakfast.SevenBreakfastFragment;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.DataManager;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerBasket;

public class MainActivity extends AppCompatActivity {
public static MainListener mainListener ;
public static String numproduct = "";
static FragmentTransaction transaction;
public Button bt_test;
    BottomNavigationView navigation;
    private static int count;
static String Page;
Activity activity=this;
    public MainActivity() {
        if(count==0){
            setPage("Sevenbreakfast");
            count=1;
        }
    }

    Context context = this;
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            display(item.getItemId());

            return true;
        }

    };

     public void display(int viewId){

        String title = getString(R.string.app_name);
        switch (viewId){
            case R.id.navigation_sevenbreakfast:
                mainListener.onClick2Sevenbreakfast();
                title = "SevenBreakfast";
                setbutton(title);
                break;
            case R.id.navigation_basket:
                mainListener.onClick2Basket();
                title = "Basket";
                setbutton(title);
                break;
            case R.id.navigation_history:
                mainListener.onClick2History();
                title = "CustomerHistory";
                setbutton(title);
                break;
            case R.id.navigation_profile:
                mainListener.onClick2Profile();
                title = "Profile";
                setbutton(title);
                break;
        }
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }
    public static String getPage() {
        return Page;
    }

    public static void setPage(String page) {
        Page = page;
    }

    TextView num_basket ;
     private void setbutton(String name){
         if(name.equalsIgnoreCase("Sevenbreakfast")){
             navigation.getMenu().findItem(R.id.navigation_sevenbreakfast).setChecked(true);
             navigation.getMenu().findItem(R.id.navigation_basket).setChecked(false);
             navigation.getMenu().findItem(R.id.navigation_history).setChecked(false);
             navigation.getMenu().findItem(R.id.navigation_profile).setChecked(false);
         }
         else if(name.equalsIgnoreCase("Basket")){
             navigation.getMenu().findItem(R.id.navigation_sevenbreakfast).setChecked(false);
             navigation.getMenu().findItem(R.id.navigation_basket).setChecked(true);
             navigation.getMenu().findItem(R.id.navigation_history).setChecked(false);
             navigation.getMenu().findItem(R.id.navigation_profile).setChecked(false);
         }
         else if(name.equalsIgnoreCase("CustomerHistory")){
             navigation.getMenu().findItem(R.id.navigation_sevenbreakfast).setChecked(false);
             navigation.getMenu().findItem(R.id.navigation_basket).setChecked(false);
             navigation.getMenu().findItem(R.id.navigation_history).setChecked(true);
             navigation.getMenu().findItem(R.id.navigation_profile).setChecked(false);}
         else if (name.equalsIgnoreCase("Profile")){
             navigation.getMenu().findItem(R.id.navigation_sevenbreakfast).setChecked(false);
             navigation.getMenu().findItem(R.id.navigation_basket).setChecked(false);
             navigation.getMenu().findItem(R.id.navigation_history).setChecked(false);
             navigation.getMenu().findItem(R.id.navigation_profile).setChecked(true);
         }
     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        switch (getPage()){
            case "Sevenbreakfast":
                Page(new SevenBreakfastFragment(activity));
                setbutton("Sevenbreakfast");
                break;
            case "Basket":
                Page(new BaketFragment());
                setbutton("Basket");
                break;
            case "CustomerHistory":
                Page(new HistoryFragment());
                setbutton("CustomerHistory");
                break;
            case "Profile":
                Page(new ProfileFrament());
                setbutton("Profile");
                break;
        }
        CheckPremision();
           mainListener = new MainListener() {
               @Override
               public void loadbasket() {
                   Handler handler = new Handler(){
                       @Override
                       public void handleMessage(Message msg) {
                           super.handleMessage(msg);
                           switch (msg.what){
                               case 0:
                                   CustomerBasket res = (CustomerBasket)msg.obj;
                                   TextView textView = (TextView) findViewById(R.id.tx_badge);
                                   numproduct = res.getBasketLists().size()+"";
                                   textView.setText(numproduct);
                                   break;
                               case 1: break;
                           }

                       }
                   };
                   new Connect_webservice().Basket(handler);
               }

               @Override
               public void onClick2Sevenbreakfast() {
                   Page(new SevenBreakfastFragment(activity));
               }

               @Override
               public void onClick2Basket() {
                   Page(new BaketFragment());
               }

               @Override
               public void onClick2History() {
                   Page(new HistoryFragment());
               }

               @Override
               public void onClick2Profile() {
                   Page(new ProfileFrament());
               }

               @Override
            public void onClickHistory2DetailOrder(String receipt) {
                Page(new historyorderDetail(receipt));
            }

        };

    }
    public void Page(Fragment fm){
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fm);
        transaction.commit();
    }
    int productId=0;
    public int checkBasket(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        CustomerBasket resobj =(CustomerBasket)msg.obj;
                        if(resobj!=null){
                            AFLogUtils.i("Return Code Basket : " + resobj.getReturnCode() + ", Count : " );
                            if(resobj.getBasketLists()!=null&&resobj.getBasketLists().size()>0){

                                for (CustomerBasket.BasketListModel item : resobj.getBasketLists()){
                                    AFLogUtils.i("Item : "+item.getProductId());
                                    productId = item.getProductId();
                                }
                            }
                        }

                        break;
                    case 1:
                        AFInfoDialog dialog = new AFInfoDialog(getParent(),null);
                        dialog.setText("ไม่สามารถเชื่อมต่อ sever ได้   "+msg.obj.toString());
                        dialog.setBt_yes_name("ตกลง");
                        dialog.show();

                        break;
                }
            }
        } ;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String imei = "2";
                String userId = "1";
                String macAdress = "54";

                try {
                    Message message = new Message();
                    CustomerBasket customerBasket = DataManager.getInstance().customerBasket(userId,imei,macAdress);
                    message.obj = customerBasket;
                    message.what = 0;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    Message message = new Message();
                    message.obj = e.toString();
                    message.what = 1;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        });

    return productId;
    }

    public void CheckPremision(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,Manifest.permission.ACCESS_FINE_LOCATION)){
                //ขอแล้วไม่ทำไร
            }
            else {//ยังไม่ได้ทำการขอหรือไม่ได้รับอนุญาติเข้าใช้ให้ขอสิทธ์ โดยจะขึ้น Dialogbox ขึ้นมา
                ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
            }
            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,Manifest.permission.ACCESS_COARSE_LOCATION)){
                //ขอแล้วไม่ทำไร
            }
            else {//ยังไม่ได้ทำการขอหรือไม่ได้รับอนุญาติเข้าใช้ให้ขอสิทธ์ โดยจะขึ้น Dialogbox ขึ้นมา
                ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},0);
            }

            return;
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //ขอแล้วไม่ทำไร
            } else {//ยังไม่ได้ทำการขอหรือไม่ได้รับอนุญาติเข้าใช้ให้ขอสิทธ์ โดยจะขึ้น Dialogbox ขึ้นมา
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
            return;
        }

    }
}

