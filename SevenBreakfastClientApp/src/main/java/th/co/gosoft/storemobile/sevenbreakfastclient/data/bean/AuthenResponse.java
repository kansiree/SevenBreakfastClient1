package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

/**
 * Created by pongpanboo on 19/4/2560.
 */

public class AuthenResponse extends BaseResponse {
    private String memberId;
    private String name;
    private String imei;
    private String macAddress;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
