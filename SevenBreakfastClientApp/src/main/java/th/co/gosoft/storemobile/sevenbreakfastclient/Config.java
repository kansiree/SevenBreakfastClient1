package th.co.gosoft.storemobile.sevenbreakfastclient;

/**
 * Created by pongpanboo on 19/4/2560.
 */

public class Config {

    private static final String PROTOCAL = "http";
//    private static final String PORT = "8001";
//    private static final String HOST = "10.182.247.104";

    private static final String PORT = "9083";
    private static final String HOST = "10.1.1.186";

    private static final String SUB_API = "SevenBreakfastHQ";
    private static final String PREFIX = PROTOCAL.concat("://").concat(HOST).concat(":").concat(PORT).concat("/").concat(SUB_API);
    public static final String URL_AUTHEN =PREFIX.concat("/StoreAuthen");
    public static final String URL_ORDERLIST =PREFIX.concat("/StoreOrderList");
    // Customer
    public static final String URL_Customer_AUTHEN = PREFIX.concat("/CustomerAuthen");
    public static final String URL_Customer_ProductList = PREFIX.concat("/CustomerProductList");
    public static final String URL_Customer_ProductById = PREFIX.concat("/CustomerProductById");
    public static final String URL_Customer_Baket = PREFIX.concat("/CustomerBasket");
    public static final String URL_Customer_EditBasket = PREFIX.concat("/CustomerEditBasket");
    public static final String URL_Customer_History = PREFIX.concat("/CustomerHistory");
    public static final String URL_Receipt = PREFIX.concat("/Receipt");
    public static final String URL_Customer_OrderHistory = PREFIX.concat("/CustomerListOrderHistory");
    public static final String URL_Map = PREFIX.concat("/mapData");
}
