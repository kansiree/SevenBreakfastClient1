package th.co.gosoft.storemobile.sevenbreakfastclient;

import java.util.EventListener;

/**
 * Created by print on 3/27/2017.
 */

public interface MainListener extends EventListener {

    public void  onClick2Sevenbreakfast();
    public void onClick2Basket();
    public void onClick2History();
    public void onClick2Profile();
    public void onClickHistory2DetailOrder(String receipt);
    public void loadbasket();
}

