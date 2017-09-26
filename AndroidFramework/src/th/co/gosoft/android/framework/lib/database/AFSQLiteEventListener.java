package th.co.gosoft.android.framework.lib.database;

import java.util.EventListener;

import android.database.sqlite.SQLiteDatabase;

public interface AFSQLiteEventListener extends EventListener{
	public void onCreate(SQLiteDatabase db);
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
