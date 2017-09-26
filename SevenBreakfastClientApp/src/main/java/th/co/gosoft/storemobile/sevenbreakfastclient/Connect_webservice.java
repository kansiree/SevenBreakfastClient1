package th.co.gosoft.storemobile.sevenbreakfastclient;

import android.os.Handler;
import android.os.Message;

import th.co.gosoft.storemobile.sevenbreakfastclient.FragmentBasket.ReviewOrderBasket;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.DataManager;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.AuthenResponse;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerAuthen;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerBasket;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerEditBasket;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerHistory;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerListOrderHistory;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerProductById;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerProductList;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.OrderListResponse;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.Receipt;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.mapData;

/**
 * Created by print on 5/30/2017.
 */

public class Connect_webservice {
    public void Basket(final Handler handler) {
        new Thread(new Runnable() {
            String imei = "45";
            String adress = "52x6";
            String UserId = "1";

            @Override
            public void run() {
                try {
                    CustomerBasket customerBasket = DataManager.getInstance().customerBasket(imei, adress, UserId);
                    Message message = new Message();
                    message.obj = customerBasket;
                    message.what = 0;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = e.toString();
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    public void ProductList(final Handler handler) {
        final String imei = "45";
        final String adress = "52x6";
        final String UserId = "1";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CustomerProductList res = DataManager.getInstance().customerProductList(imei, adress, UserId);
                    Message message = new Message();
                    message.obj = res;
                    message.what = 0;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Message message = new Message();
                    message.obj = e.toString();
                    message.what = 1;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void ProductByID(final Handler handler, final int productId) {
        final String imei = "45";
        final String adress = "52x6";
        final String UserId = "1";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CustomerProductById res = DataManager.getInstance().customerProductById(imei, adress, productId, UserId);
                    Message message = new Message();
                    message.obj = res;
                    message.what = 0;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Message message = new Message();
                    message.obj = e.toString();
                    message.what = 1;
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void EditBasket(final Handler handler, final int productId, final int value) {
        final String imei = "45";
        final String adress = "52x6";
        final String UserId = "1";
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CustomerEditBasket res = DataManager.getInstance().customerEditBasket(UserId, imei, adress, productId, value);
                    Message message = new Message();
                    message.obj = res;
                    message.what = 0;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Message message = new Message();
                    message.obj = e.toString();
                    message.what = 1;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void OrderList(final Handler handler) {
        final String imei = "45";
        final String adress = "52x6";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OrderListResponse res = DataManager.getInstance().OrderList(imei, adress);
                    Message message = new Message();
                    message.obj = res;
                    message.what = 0;
                    handler.sendMessage(message);
                } catch (Throwable t) {
                    Message message = new Message();
                    message.obj = t.toString();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    public void customerAuthen(final Handler handler) {
        final String imei = "45";
        final String adress = "52x6";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CustomerAuthen res = DataManager.getInstance().customerAuthen(imei, adress);
                    Message message = new Message();
                    message.obj = res;
                    message.what = 0;
                    handler.sendMessage(message);
                } catch (Throwable t) {
                    Message message = new Message();
                    message.obj = t.toString();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    public void authen(final Handler handler) {
        final String imei = "45";
        final String adress = "52x6";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AuthenResponse res = DataManager.getInstance().authen(imei, adress);
                    Message message = new Message();
                    message.obj = res;
                    message.what = 0;
                    handler.sendMessage(message);
                } catch (Throwable t) {
                    Message message = new Message();
                    message.obj = t.toString();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    public void Receipt(final Handler handler, final String time, final String Date, final String store, final String total, final String receipt) {
        final String userName = "print";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Receipt res = DataManager.getInstance().receipt(userName, Date, time, store, total, receipt);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = res;
                    new ReviewOrderBasket().setreceipt(res.getReceipt());
                    handler.sendMessage(message);
                } catch (Throwable t) {
                    Message message = new Message();
                    message.obj = t.toString();
                    message.what = 1;
                    handler.sendMessage(message);
                    t.printStackTrace();
                }

            }
        }).start();
    }

    public void History(final Handler handler, String user) {
        final String username = user;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CustomerHistory res = DataManager.getInstance().customerHistory(username);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = res;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = e.toString();
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void OrderHistory(final Handler handler, final String receipt) {
        final String username = "print";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CustomerListOrderHistory res = DataManager.getInstance().customerListOrderHistory(username, receipt);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = res;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = e.toString();
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void Map(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mapData res = DataManager.getInstance().mapDatasent();
                    Message message = new Message();
                    message.what = 0;
                    message.obj = res;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = e.toString();
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

