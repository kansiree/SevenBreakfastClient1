package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

import java.util.List;

/**
 * Created by print on 8/3/2017.
 */

public class CustomerListOrderHistory extends BaseBean {
    private String returnCode;
    private String returnDescription;
    private String StatusOrder;
    private List<listOrderHistory> orderlist;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDescription() {
        return returnDescription;
    }

    public void setReturnDescription(String returnDescription) {
        this.returnDescription = returnDescription;
    }

    public String getStatusOrder() {
        return StatusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        StatusOrder = statusOrder;
    }

    public List<listOrderHistory> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<listOrderHistory> orderlist) {
        this.orderlist = orderlist;
    }

    public static class listOrderHistory{
    private String title;
    private int price;
    private int value;
    private String picture;
    private int productID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
}

