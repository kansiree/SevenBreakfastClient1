package th.co.gosoft.storemobile.sevenbreakfastclient.model;

/**
 * Created by print on 4/24/2017.
 */
public class ModelOrder{
    String no_order,status,date;
    int image;

    public ModelOrder() {
    }

    public String getNo_order() {
        return no_order;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public int getImage() {
        return image;
    }

    public void setNo_order(String no_order) {

        this.no_order = no_order;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
