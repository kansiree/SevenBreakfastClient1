package th.co.gosoft.storemobile.sevenbreakfastclient.model;

/**
 * Created by print on 4/24/2017.
 */
public class ModelData{
    String picture;
    String name;
    int value,price;
    int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    public void setImage(String picture) {
        this.picture = picture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getImage() {
        return picture;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getValue() {
        return value;
    }
}
