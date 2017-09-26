package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

import java.util.List;

/**
 * Created by print on 5/22/2017.
 */

public class CustomerEditBasket extends BaseResponse {
    List<editBasket> editBaskets;
    public List<editBasket> getEditBaskets() {
        return editBaskets;
    }
    public List<CustomerAuthen> setEditBasket(List<editBasket> editBaskets){
        this.editBaskets=editBaskets;
        return null;
    }

public static class editBasket{
    private int value;
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
}
