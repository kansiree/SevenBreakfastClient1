package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentBasket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import th.co.gosoft.storemobile.sevenbreakfastclient.R;

public class TimeBasket extends AppCompatActivity {
    static String  Time,Distance,NameLocation;

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(),Map.class);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        Intent intent =  getIntent();
        NameLocation = intent.getStringExtra("Name");
        Time = intent.getStringExtra("Time");
        Distance = intent.getStringExtra("Distance");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Select TimeBasket");
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.time,new time_date_Basket());
        transaction.commit();


    }


}
