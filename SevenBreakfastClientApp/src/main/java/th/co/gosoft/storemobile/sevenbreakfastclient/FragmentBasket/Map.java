package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentBasket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import th.co.gosoft.android.framework.lib.dialog.DialogDetail;
import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import th.co.gosoft.storemobile.sevenbreakfastclient.Adapter.Adapter_SelectStore;
import th.co.gosoft.storemobile.sevenbreakfastclient.CallMap;
import th.co.gosoft.storemobile.sevenbreakfastclient.Connect_webservice;
import th.co.gosoft.storemobile.sevenbreakfastclient.MainActivity;
import th.co.gosoft.storemobile.sevenbreakfastclient.Modules.DirectionFinder;
import th.co.gosoft.storemobile.sevenbreakfastclient.Modules.DirectionFinderListener;
import th.co.gosoft.storemobile.sevenbreakfastclient.Modules.Route;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.mapData;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.mapModel;


public class Map extends AppCompatActivity implements LocationListener, OnMapReadyCallback, DirectionFinderListener {

    GoogleMap map;
    LocationManager locationManager;
    Context context = Map.this;
    RecyclerView recyclerView;
    List<Marker> originMarkers = new ArrayList<>();
    List<Marker> destinationMarkers = new ArrayList<>();
    Adapter_SelectStore adapter_selectStore;
    String Time = "", Distance = "sca", txtLocation = "",LocationName = "";
    String deatination = "";
    mapModel data = new mapModel();
    List<String> name = new ArrayList<>();
    List<String> latitude = new ArrayList<>();
    List<String> longtitude = new ArrayList<>();
    int count = 1;
    public static CallMap callMap;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapfragment);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mapFragment.getMapAsync(this);

        recyclerView = (RecyclerView) findViewById(R.id.liststoreView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        mapData res = (mapData) msg.obj;
                        List<mapModel> maplistData = new ArrayList<>();
                        if (res != null) {
                            AFLogUtils.i("Return Code Map : " + res.getListMap().size());
                            if (res.getListMap() != null && res.getListMap().size() > 0) {

                                for (mapData.MapData item : res.getListMap()) {
                                    data = new mapModel();
                                    AFLogUtils.i("Name: " + item.getName() + "latiture: " + item.getLatiture() + "longtiture: " + item.getLongtiture());
                                    data.setImage(R.drawable.store);
                                    data.setOrigin(Double.parseDouble(item.getLatiture()));
                                    data.setDistination(Double.parseDouble(item.getLongtiture()));
                                    data.setName(item.getName());
                                    maplistData.add(data);
                                }
                                adapter_selectStore = new Adapter_SelectStore(getApplicationContext(), maplistData);
                                adapter_selectStore.setClickItem(new Adapter_SelectStore.onClickItem() {

                                    @Override
                                    public void onItemClick(View view, int position, double origin, double distine,String name) {
                                        if (ActivityCompat.checkSelfPermission(Map.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Map.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                            // TODO: Consider calling
                                            //    ActivityCompat#requestPermissions
                                            // here to request the missing permissions, and then overriding
                                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                            //                                          int[] grantResults)
                                            // to handle the case where the user grants the permission. See the documentation
                                            // for ActivityCompat#requestPermissions for more details.
                                            return;
                                        }
                                        System.out.println("name list: "+origin+" "+origin);
                                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                                        FindDis(location.getLatitude(),location.getLongitude());
                                        sendRequset(FindDis(location.getLatitude(),location.getLongitude()),FindDis(origin, distine));
                                        LocationName = name;
                                    }
                                });
                                recyclerView.setAdapter(adapter_selectStore);
                            }
                        }
                        break;
                    case 1:
                        System.out.println("print case2: ");
                        break;
                }
            }
        };
        new Connect_webservice().Map(handler);
        toolbar.setTitle("Select Receive Store");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bt_cancel:

                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                MainActivity.setPage("Basket");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(count==1){
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 10);
            map.animateCamera(cameraUpdate);
        }
        count=2;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // requesLocation now
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, this);
            // set posiotion Location in location
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // set Marker in Location
            map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())));
            // Show in position markker
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
            // zoom Camera
            map.moveCamera(CameraUpdateFactory.zoomTo(15));
            super.onResume();

        map.setMyLocationEnabled(true);
    }
    private void sendRequset(String Origin,String Distination) {
        try {
            new DirectionFinder(this,Origin,Distination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private String FindDis(double latiture,double longtiture){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> listAddress = geocoder.getFromLocation(latiture,longtiture,1);
            txtLocation = listAddress.get(0).getAddressLine(0);
            System.out.println("location name: "+txtLocation);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return txtLocation;
    }
    @Override
    public void onDirectionFinderStart() {

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        for (Route route : routes){
            data.setDistance(route.distance.text);
            Time = route.duration.text;
            Distance = route.distance.text;
            DialogDetail dialogDetail = new DialogDetail(Map.this,Distance,Time,null);
            dialogDetail.show();
            Button next = (Button) findViewById(R.id.next);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     callMap = new CallMap() {
                         @Override
                         public String Store() {
                             return LocationName;
                         }
                     };
                    Intent intent = new Intent(Map.this, TimeBasket.class);
                    intent.putExtra("Name",LocationName);
                    intent.putExtra("Time",Time);
                    intent.putExtra("Distance",Distance);
                    startActivity(intent);
                    finish();
                }
            });

            System.out.println("Distance: "+Distance+" Time: "+Time+" "+LocationName);
        }
    }
}

