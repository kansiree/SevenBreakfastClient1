package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

/**
 * Created by pongpanboo on 19/4/2560.
 */

public class BaseResponse {
    String returnCode;
    String returnDescTh;
    String returnDescEn;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDescTh() {
        return returnDescTh;
    }

    public void setReturnDescTh(String returnDescTh) {
        this.returnDescTh = returnDescTh;
    }

    public String getReturnDescEn() {
        return returnDescEn;
    }

    public void setReturnDescEn(String returnDescEn) {
        this.returnDescEn = returnDescEn;
    }

}