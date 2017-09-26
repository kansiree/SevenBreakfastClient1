package th.co.gosoft.storemobile.sevenbreakfastclient.data.database;

import android.content.Context;
import android.database.Cursor;

import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerListOrderHistory;

/**
 * Created by print on 9/5/2017.
 */

public class DatabaseManager extends DBManager {
    final String TABLE_NAME = "History";
    final String COLUMN_PRODUCTID = "productID";
    final String COLUMN_NAME = "name";
    final String COLUMN_PRICE = "price";
    final String COLUMN_VALU = "value";
    final String COLUMN_PICTURE = "picture";
    public DatabaseManager(Context context) {
        super(context);
    }

    @Override
    void parseData(Cursor cursor) {

    }
    public void InsertData(CustomerListOrderHistory.listOrderHistory item){
        String sql = "INSERT INTO "+TABLE_NAME+" (" +
                COLUMN_NAME+", " +
                COLUMN_PRODUCTID+", "+
                COLUMN_PRICE+", "+
                COLUMN_PICTURE+", "+
                COLUMN_VALU+") VALUES ('"+
                item.getTitle()+"','"+
                item.getProductID()+"', "+
                item.getPrice()+", '"+
                item.getPicture()+"', "+
                item.getValue()+") ";
        getWritableDatabase().rawQuery(sql,null);
    }
    public void DeleteData(){
        String sql = "DELETE FROM "+TABLE_NAME;
        getWritableDatabase().rawQuery(sql,null);
    }
    public Cursor ReadData(){
        String sql = "SELECT * FROM "+TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }
}
