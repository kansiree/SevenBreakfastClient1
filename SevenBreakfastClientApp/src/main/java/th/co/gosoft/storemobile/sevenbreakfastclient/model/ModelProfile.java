package th.co.gosoft.storemobile.sevenbreakfastclient.model;

/**
 * Created by print on 4/24/2017.
 */
public class ModelProfile{

    String storeList,noStore;
    int image;
    public ModelProfile(){
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNoStore() {
        return noStore;
    }

    public String getStoreList() {
        return storeList;
    }

    public void setNoStore(String noStore) {
        this.noStore = noStore;
    }

    public void setStoreList(String storeList) {
        this.storeList = storeList;
    }
}
