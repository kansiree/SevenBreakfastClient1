package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

import java.util.List;

/**
 * Created by pongpanboo on 19/4/2560.
 */

public class OrderListResponse extends BaseResponse {
    private List<OrderModel> orderList;

    public List<OrderModel> getOrderList() {
        return orderList;
    }

    public OrderListResponse setOrderList(List<OrderModel> orderList) {
        this.orderList = orderList;
        return null;
    }

    public static class OrderModel {
        private String orderId;
        private String title;
        private String body;
        private String price;
        private String time;
        private String status;
        private String statusText;
        private int icon;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusText() {
            return statusText;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }

}
