package th.co.gosoft.storemobile.sevenbreakfastclient.data;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

import th.co.gosoft.android.framework.lib.transfer.AFSimpleHTTPReader;
import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import th.co.gosoft.storemobile.sevenbreakfastclient.Config;
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
 * Created by pongpanboo on 19/4/2560.
 */

public class DataManager {
    final String TAG_LOG = DataManager.class.getName();
    final int READ_TIMEOUT = 2 * 60 * 1000;//2 min
    final Gson mGson;
    private static DataManager mDataManager = null;

    private DataManager() {
        mGson = new Gson();
    }

    public static DataManager getInstance() {
        if (mDataManager == null) mDataManager = new DataManager();
        return mDataManager;
    }

    public AuthenResponse authen(String imei, String macAddress) throws Exception {

        AFSimpleHTTPReader simple = new AFSimpleHTTPReader();
        HashMap posData = new HashMap<>();
        posData.put("imei", imei);
        posData.put("macAddress", macAddress);

        // chang object to json plat from
        JSONObject object = new JSONObject(posData);
        AFLogUtils.i("json : " + object.toString());

        simple.setMethod(AFSimpleHTTPReader.METHOD_POST);
        simple.setReadTimeOut(READ_TIMEOUT);

        // read data from web server to String object in json plat from
        String res = simple.readHTML(new URL(Config.URL_AUTHEN), object.toString()).toString();

        AFLogUtils.i("json return : " + res);

        // chang data from json plat to java object by Gson class and return to Class
        return mGson.fromJson(res, AuthenResponse.class);

    }

    public OrderListResponse OrderList(String imei, String macAddress) throws Exception {

        AFSimpleHTTPReader simple = new AFSimpleHTTPReader();
        HashMap posData = new HashMap<>();
        posData.put("imei", imei);
        posData.put("macAddress", macAddress);

        JSONObject object = new JSONObject(posData);
        AFLogUtils.i("json : " + object.toString());

        simple.setMethod(AFSimpleHTTPReader.METHOD_POST);
        simple.setReadTimeOut(READ_TIMEOUT);
        String res = simple.readHTML(new URL(Config.URL_ORDERLIST), object.toString()).toString();
        AFLogUtils.i("json return : " + res);

        return mGson.fromJson(res, OrderListResponse.class);
    }

    public CustomerProductList customerProductList(String imei, String macAddress, String userId) throws Exception {
        AFSimpleHTTPReader simple = new AFSimpleHTTPReader();
        HashMap hashMap = new HashMap<>();
        hashMap.put("imei", imei);
        hashMap.put("macAdress", macAddress);
        hashMap.put("userId", userId);

        JSONObject object = new JSONObject(hashMap);
        AFLogUtils.i("json " + object.toString());

        simple.setMethod(AFSimpleHTTPReader.METHOD_POST);
        simple.setReadTimeOut(READ_TIMEOUT);

        String res = simple.readHTML(new URL(Config.URL_Customer_ProductList), object.toString()).toString();
        AFLogUtils.i("json return" + res);

        return mGson.fromJson(res, CustomerProductList.class);
    }


    public CustomerAuthen customerAuthen(String imei, String macAddress) throws Exception {
        AFSimpleHTTPReader simple = new AFSimpleHTTPReader();
        HashMap posData = new HashMap<>();
        posData.put("imei", imei);
        posData.put("macAddress", macAddress);

        JSONObject object = new JSONObject(posData);
        AFLogUtils.i("json : " + object.toString());

        simple.setMethod(AFSimpleHTTPReader.METHOD_POST);
        simple.setReadTimeOut(READ_TIMEOUT);
        String res = simple.readHTML(new URL(Config.URL_Customer_AUTHEN), object.toString()).toString();
        AFLogUtils.i("json return : " + res);

        return mGson.fromJson(res, CustomerAuthen.class);
    }

    public CustomerProductById customerProductById(String imei, String macAddress, int productId, String UserId) throws Exception {
        HashMap hashMap = new HashMap<>();
        hashMap.put("imei", imei);
        hashMap.put("macAddress", macAddress);
        hashMap.put("productId", productId);
        hashMap.put("userId", UserId);

        JSONObject object = new JSONObject(hashMap);
        AFLogUtils.i(object.toString());

        AFSimpleHTTPReader simpleHTTPReader = new AFSimpleHTTPReader();
        simpleHTTPReader.setMethod(AFSimpleHTTPReader.METHOD_POST);
        simpleHTTPReader.setReadTimeOut(READ_TIMEOUT);

        String res = simpleHTTPReader.readHTML(new URL(Config.URL_Customer_ProductById), object.toString()).toString();
        AFLogUtils.i("json return: " + res);

        return mGson.fromJson(res, CustomerProductById.class);
    }

    public Receipt receipt(String userName, String date, String time, String store, String total, String receiptOld) throws Exception {
        HashMap hashMap = new HashMap<>();
        hashMap.put("userName", userName);
        hashMap.put("date", date);
        hashMap.put("time", time);
        hashMap.put("store", store);
        hashMap.put("total", total);
        hashMap.put("receiptOld", receiptOld);

        JSONObject object = new JSONObject(hashMap);
        AFLogUtils.i("json : " + object.toString());

        AFSimpleHTTPReader simpleHTTPReader = new AFSimpleHTTPReader();
        simpleHTTPReader.setReadTimeOut(READ_TIMEOUT);
        simpleHTTPReader.setMethod(AFSimpleHTTPReader.METHOD_POST);

        String res = simpleHTTPReader.readHTML(new URL(Config.URL_Receipt), object.toString()).toString();
        AFLogUtils.i("json return receipt: " + res);
        return mGson.fromJson(res, Receipt.class);
    }

    public CustomerBasket customerBasket(String userId, String imei, String macAdress) throws Exception {
        HashMap hashMap = new HashMap<>();
        hashMap.put("imei", imei);
        hashMap.put("macAdress", macAdress);
        hashMap.put("userId", userId);

        JSONObject object = new JSONObject(hashMap);
        AFLogUtils.i(object.toString());

        AFSimpleHTTPReader simpleHTTPReader = new AFSimpleHTTPReader();
        simpleHTTPReader.setMethod(AFSimpleHTTPReader.METHOD_POST);
        simpleHTTPReader.setReadTimeOut(READ_TIMEOUT);

        String res = simpleHTTPReader.readHTML(new URL(Config.URL_Customer_Baket), object.toString()).toString();
        AFLogUtils.i("json return : " + res);

        return mGson.fromJson(res, CustomerBasket.class);
    }

    public CustomerEditBasket customerEditBasket(String userId, String imei, String macAdress, int productId, int value) throws Exception {
        HashMap hashMap = new HashMap<>();
        hashMap.put("imei", imei);
        hashMap.put("macAdress", macAdress);
        hashMap.put("userId", userId);
        hashMap.put("productId", productId);
        hashMap.put("value", value);

        JSONObject object = new JSONObject(hashMap);
        AFLogUtils.i(object.toString());
        AFSimpleHTTPReader simpleHTTPReader = new AFSimpleHTTPReader();
        simpleHTTPReader.setMethod(AFSimpleHTTPReader.METHOD_POST);
        simpleHTTPReader.setReadTimeOut(READ_TIMEOUT);

        String res = simpleHTTPReader.readHTML(new URL(Config.URL_Customer_EditBasket), object.toString()).toString();
        AFLogUtils.i("json return : " + res);
        return mGson.fromJson(res, CustomerEditBasket.class);
    }

    public CustomerHistory customerHistory(String user) throws Exception {
        HashMap hashMap = new HashMap<>();
        hashMap.put("user", user);

        JSONObject object = new JSONObject(hashMap);
        AFLogUtils.i(object.toString());
        AFSimpleHTTPReader simpleHTTPReader = new AFSimpleHTTPReader();
        simpleHTTPReader.setMethod(AFSimpleHTTPReader.METHOD_POST);
        simpleHTTPReader.setReadTimeOut(READ_TIMEOUT);

        String res = simpleHTTPReader.readHTML(new URL(Config.URL_Customer_History), object.toString()).toString();
        AFLogUtils.i("json return: " + res);
        return mGson.fromJson(res, CustomerHistory.class);
    }

    public CustomerListOrderHistory customerListOrderHistory(String user, String receipt) throws Exception {
        HashMap hashMap = new HashMap<>();
        hashMap.put("userId", user);
        hashMap.put("receipt", receipt);

        JSONObject object = new JSONObject(hashMap);
        AFLogUtils.i(object.toString());
        AFSimpleHTTPReader simpleHTTPReader = new AFSimpleHTTPReader();
        simpleHTTPReader.setMethod(AFSimpleHTTPReader.METHOD_POST);
        simpleHTTPReader.setReadTimeOut(READ_TIMEOUT);

        String res = simpleHTTPReader.readHTML(new URL(Config.URL_Customer_OrderHistory), object.toString()).toString();
        AFLogUtils.i("json return: " + res);
        return mGson.fromJson(res, CustomerListOrderHistory.class);
    }

    public mapData mapDatasent() {
        HashMap hashMap = new HashMap<>();
//        hashMap.put("userId",user);
//        hashMap.put("receipt",receipt);

        JSONObject object = new JSONObject(hashMap);
        AFLogUtils.i(object.toString());
        AFSimpleHTTPReader simpleHTTPReader = new AFSimpleHTTPReader();
        simpleHTTPReader.setMethod(AFSimpleHTTPReader.METHOD_POST);
        simpleHTTPReader.setReadTimeOut(READ_TIMEOUT);

        String res = null;
        try {
            res = simpleHTTPReader.readHTML(new URL(Config.URL_Map), object.toString()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AFLogUtils.i("json return: " + res);
        return mGson.fromJson(res, mapData.class);
    }
}
