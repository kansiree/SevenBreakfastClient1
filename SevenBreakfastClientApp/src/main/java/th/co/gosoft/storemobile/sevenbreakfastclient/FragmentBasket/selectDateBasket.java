package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentBasket;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import th.co.gosoft.storemobile.sevenbreakfastclient.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class selectDateBasket extends Fragment {

    DatePicker Date;
    Button Date_bt;
    time_date_Basket data = new time_date_Basket();
    public selectDateBasket() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Date = (DatePicker) getActivity().findViewById(R.id.datePicker);

        Date_bt = (Button) getActivity().findViewById(R.id.Date_bt);
        Date_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setDay(Date.getDayOfMonth()+"");
                data.setmon(Date.getMonth()+1+"");
                data.setYear(Date.getYear()+"");
                getActivity().getSupportFragmentManager().beginTransaction().remove(selectDateBasket.this).commit();
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
        return inflater.inflate(R.layout.fragment_select_date, container, false);
    }

}
