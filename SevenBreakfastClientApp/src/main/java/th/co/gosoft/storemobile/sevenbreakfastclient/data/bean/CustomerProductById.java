package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

import java.util.List;

/**
 * Created by print on 4/27/2017.
 */

public class CustomerProductById extends BaseResponse {
    String status;
    private List<csProductByIdModel> customerProductId;

    public List<csProductByIdModel> getCustomerProductById(){return customerProductId;}

    public List<CustomerAuthen> setCustomerProductById(List<csProductByIdModel> customerProductID) {
        this.customerProductId = customerProductID ;
        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class csProductByIdModel{
        int picture;
        int price;
        String title;

        String numproduct;


        public int getPicture() {
            return picture;
        }

        public void setPicture(int picture) {
            this.picture = picture;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNumproduct() {
            return numproduct;
        }

        public void setNumproduct(String numproduct) {
            this.numproduct = numproduct;
        }
    }
}
