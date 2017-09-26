
package th.co.gosoft.storemobile.sevenbreakfastclient.data.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import hqmobileframework.common.utils.MFFileUtils;
import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;


/**
 * Created by pongpanboo on 19/4/2560.
 */

public class SQLiteAdapterFactory {
    private static final String DATABASE_PREF_KEY="dbcreate";
    public static final String DATABASE_DEFAULT_NAME = "base.db";
    public static final int DB_VERSION = 1;
    private static MySqliteOpenHelper myHelper = null;

    public static SQLiteOpenHelper getOpenHelperInstance(Context context) {
        if (myHelper == null) {
            initialDatabase(context);
            myHelper = new MySqliteOpenHelper(context, DATABASE_DEFAULT_NAME,
                    null, DB_VERSION);
        }
        copyDataBaseForTest(context);
        return (SQLiteOpenHelper) myHelper;
    }
    public static SQLiteOpenHelper getOpenHelperInstance(Context context,
                                                         SQLiteDatabase.CursorFactory factory) {
        if (myHelper == null) {
            initialDatabase(context);
            myHelper = new MySqliteOpenHelper(context, DATABASE_DEFAULT_NAME,
                    factory, DB_VERSION);
        }

        return (SQLiteOpenHelper) myHelper;
    }
    private static void copyDataBaseForTest(Context mContext)
    {

        try{
            ContextWrapper cw = new ContextWrapper(mContext);
            File database = cw.getDatabasePath(DATABASE_DEFAULT_NAME);
            //MFFileUtils.makeDir(database.getParent());

            MFFileUtils.writeFile(new FileOutputStream(new File("/sdcard/"+DATABASE_DEFAULT_NAME)), new FileInputStream(database));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private static void initialDatabase(Context mContext){
        try{

            //copyDataBaseForTest(mContext);
            SharedPreferences pref = PreferenceManager
                    .getDefaultSharedPreferences(mContext);
            String keyPref = DATABASE_PREF_KEY;
            int create = pref.getInt(keyPref, 1);
            if (create == 1) {
                // Create dummy file in myapp//
                MySqliteOpenHelper m = new MySqliteOpenHelper(mContext, DATABASE_DEFAULT_NAME, null, DB_VERSION);
                m.getReadableDatabase();
                m.close();
                // Then copy my database to device//
                ContextWrapper cw = new ContextWrapper(mContext);
                File database = cw.getDatabasePath(DATABASE_DEFAULT_NAME);
                MFFileUtils.makeDir(database.getParent());
                MFFileUtils.writeFile(new FileOutputStream(database), mContext
                        .getResources().openRawResource(R.raw.base));

                SharedPreferences.Editor ed = pref.edit();
                ed.putInt(keyPref, 0);
                ed.commit();
                //super(mContext, name, factory, version);

            }
        }catch(Exception e)
        {
            AFLogUtils.e(e.toString(),e);
        }

    }
}
class MySqliteOpenHelper extends SQLiteOpenHelper {

    private Context mContext;

    public MySqliteOpenHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }


}
