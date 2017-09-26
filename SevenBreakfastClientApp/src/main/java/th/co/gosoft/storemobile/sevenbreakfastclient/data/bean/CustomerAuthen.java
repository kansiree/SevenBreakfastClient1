package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

import java.util.List;

/**
 * Created by print on 4/27/2017.
 */

public class CustomerAuthen extends BaseResponse {
    private List<csAuthenModel> orderList;

    public List<csAuthenModel> getOrderList() {
        return orderList;
    }

    public CustomerAuthen setOrderList(List<csAuthenModel> orderList) {
        this.orderList = orderList;
        return null;
    }
public static class csAuthenModel{

}
}
