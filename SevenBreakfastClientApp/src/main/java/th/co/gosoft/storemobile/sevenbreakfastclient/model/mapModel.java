package th.co.gosoft.storemobile.sevenbreakfastclient.model;

/**
 * Created by print on 4/24/2017.
 */
public class mapModel{
    int image;
    String name,distance;
    Double origin,distination;
    public mapModel(){

    }

    public Double getOrigin() {
        return origin;
    }

    public void setOrigin(Double origin) {
        this.origin = origin;
    }

    public Double getDistination() {
        return distination;
    }

    public void setDistination(Double distination) {
        this.distination = distination;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }
}
