package th.co.gosoft.storemobile.sevenbreakfastclient.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;

import hqmobileframework.common.utils.MFDateUtils;

/**
 * Created by pongpanboo on 19/4/2560.
 */

public abstract class DBManager {
    Context mContext;
    protected final SimpleDateFormat SQLITE_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", MFDateUtils.defaultLocale);
    protected final SimpleDateFormat SQLITE_DATE = new SimpleDateFormat("yyyy-MM-dd 00:00:00", MFDateUtils.defaultLocale);
    protected DBManager(Context context)
    {
        this.mContext =context;
    }
    abstract void parseData(Cursor cursor);
    protected SQLiteDatabase getReadableDatabase()
    {
        return SQLiteAdapterFactory.getOpenHelperInstance(mContext).getReadableDatabase();
    }
    protected SQLiteDatabase getWritableDatabase()
    {
        return SQLiteAdapterFactory.getOpenHelperInstance(mContext).getWritableDatabase();
    }
}