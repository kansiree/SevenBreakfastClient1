package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentSevenbereakfast;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import th.co.gosoft.storemobile.sevenbreakfastclient.MainActivity;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SevenBreakfastFragment extends Fragment {

    Activity activity;
    ViewPager Page1 ;
    public static int pos = 0;

    public SevenBreakfastFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initial();
    }
    private void initial(){
        Page1 = (ViewPager)getActivity().findViewById(R.id.Seven_view);
        MainAdapter ad = new MainAdapter(getFragmentManager(),3);
        ad.getItem(0);
        TabLayout tabTop = (TabLayout)getActivity().findViewById(R.id.maintab);

        Page1.setAdapter(ad);
        Page1.setCurrentItem(pos);
        tabTop.setupWithViewPager(Page1);
        TabLayout.Tab tab1 = tabTop.getTabAt(0);
        tab1.setText("Promotion");
        tab1 = tabTop.getTabAt(1);
        tab1.setText("Breakfast");
        tab1 = tabTop.getTabAt(2);
        tab1.setText("Cafe");

            }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public  void load(){

    }
    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.basket,menu);

        MenuItem menuItem = menu.findItem(R.id.icon_basket);
        MenuItemCompat.setActionView(menuItem,R.layout.badge);
//        RelativeLayout layout = (RelativeLayout)menu.findItem(R.id.icon_basket).getActionView();
        MainActivity.mainListener.loadbasket();

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_seven_breakfast, container, false);

        setHasOptionsMenu(true);
        return view ;

}
class MainAdapter extends FragmentStatePagerAdapter {

    Fragment[] nfragmentManager;
    int numpage;
    public MainAdapter(FragmentManager fm, int npage) {
        super(fm);

        this.numpage = npage;
        this.nfragmentManager = new Fragment[numpage];
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                nfragmentManager[position] = new Promotion(activity);

                break;
            case 1 :
                nfragmentManager[position] = new Breakfast(activity);
                break;
            case 2 :
               nfragmentManager[position] = new Cafe(activity);
                break;
        }
        return nfragmentManager[position];
    }
    @Override
    public int getCount() {
        return numpage;
    }
}
}
