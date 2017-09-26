package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

import java.util.List;

/**
 * Created by print on 8/1/2017.
 */

public class CustomerHistory extends BaseResponse {
    List<DatafileReceipt.Data> receiptList;

    public List<DatafileReceipt.Data> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<DatafileReceipt.Data> receiptList) {
        this.receiptList = receiptList;
    }

    public static class DatafileReceipt{
    public class Data{
        private String userName;
        private String Date;
        private String Time;
        private String Store;
        private String receipt;
        private String total;
        private String value;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public String getStore() {
            return Store;
        }

        public void setStore(String store) {
            Store = store;
        }

        public String getReceipt() {
            return receipt;
        }

        public void setReceipt(String receipt) {
            this.receipt = receipt;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
}
