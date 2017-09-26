package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentBasket;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import th.co.gosoft.storemobile.sevenbreakfastclient.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class selectTimeBasket extends Fragment {

    TimePicker Time;
    Button button;
    public selectTimeBasket() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Time = (TimePicker)getActivity().findViewById(R.id.timepicker) ;
        button = (Button)getActivity().findViewById(R.id.select) ;
        Time.setIs24HourView(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new time_date_Basket().setHour(Time.getCurrentHour());
                new time_date_Basket().setMin(Time.getCurrentMinute());
                getActivity().getSupportFragmentManager().beginTransaction().remove(selectTimeBasket.this).commit();
                Intent intent = new Intent(getActivity(),TimeBasket.class);
                startActivity(intent);
               getActivity().finish();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmentselecttime, container, false);
    }

}
