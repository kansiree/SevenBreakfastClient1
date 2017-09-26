package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

/**
 * Created by pongpanboo on 19/4/2560.
 */

public class BaseBean {
    int status;
    String message;
    boolean isSuccess;
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isSuccess() {
        return isSuccess;
    }
    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

}
