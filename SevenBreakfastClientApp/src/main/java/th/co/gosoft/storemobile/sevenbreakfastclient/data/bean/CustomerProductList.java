package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

import java.util.List;

/**
 * Created by print on 4/27/2017.
 */

public class CustomerProductList extends BaseResponse {
    private List<ProductListModel> productList ;

    public List<ProductListModel> getProductList(){
        return productList;}

    public CustomerProductList setProductList(List<ProductListModel> productList){
        this.productList = productList;
        return null;
    }

    public static class ProductListModel{
        private String title;
        private String body;
        private String price;
        private String categoryId;
        private String categoryName;
        private int productId;
        private String icon;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
