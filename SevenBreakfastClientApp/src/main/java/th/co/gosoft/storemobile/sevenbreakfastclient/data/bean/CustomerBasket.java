package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

import java.util.List;

/**
 * Created by print on 5/18/2017.
 */

public class CustomerBasket extends BaseResponse {

    private List<BasketListModel> basketLists;
    public List<BasketListModel> getBasketLists() {
        return basketLists;
    }

    public List<CustomerAuthen> setBasketLists(List<BasketListModel> basketLists) {
        this.basketLists = basketLists;
        return null;
    }

    public static class BasketListModel{
        private int price;
        private int value;
        private String title;
        private int productId;
        private String picture;

        public String getImage() {
            return picture;
        }

        public void setImage(String image) {
            this.picture = image;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
