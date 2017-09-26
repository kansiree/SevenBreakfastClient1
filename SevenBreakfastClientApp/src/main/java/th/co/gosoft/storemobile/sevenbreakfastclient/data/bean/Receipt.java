package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

/**
 * Created by print on 6/6/2017.
 */

public class Receipt extends BaseResponse {
//    public static class Data{
        private String userName;
        private String date;
        private String time;
        private String store;
        private String receipt;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getReceipt() {return receipt;}

        public void setReceipt(String receipt) { this.receipt = receipt;}
//    }
}
