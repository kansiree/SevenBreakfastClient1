package th.co.gosoft.storemobile.sevenbreakfastclient.model;

/**
 * Created by print on 4/24/2017.
 */
public class modelDetail{
    private String title,data;
   public modelDetail(String title,String data){
        this.title = title;
        this.data = data;
    }

    public void setdata(String data) {
        this.data = data;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getdata() {
        return data;
    }

    public String getTitle() {
        return title;
    }
}
