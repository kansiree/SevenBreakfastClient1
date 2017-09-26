package th.co.gosoft.android.framework.lib.database;


import hqmobileframework.common.utils.MFObjectUtils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class AFSQLiteAdapter {
	private String mDatabaseName = "default.db";
	private int mDatabaseVersion = 1;
	private AFSQLiteEventListener afsqLiteEventListener;

	public AFSQLiteAdapter(String databaseName, int databaseVersion) {
		setDatabaseName(databaseName);
		setDatabaseVersion(databaseVersion);
	}

	private MySqliteOpenHelper myHelper = null;

	public AFSQLiteOpenHelper getOpenHelperInstance(Context context) {
		if (myHelper == null) {
			myHelper = new MySqliteOpenHelper(context, getDatabaseName(), null,
					getDatabaseVersion());
		}

		return (AFSQLiteOpenHelper) myHelper;
	}

	public AFSQLiteOpenHelper getOpenHelperInstance(Context context,
			CursorFactory factory) {
		if (myHelper == null) {
			myHelper = new MySqliteOpenHelper(context, getDatabaseName(),
					factory, getDatabaseVersion());
		}

		return (AFSQLiteOpenHelper) myHelper;
	}

	public int getDatabaseVersion() {
		return mDatabaseVersion;
	}

	public void setDatabaseVersion(int mDatabaseVersion) {
		this.mDatabaseVersion = mDatabaseVersion;
	}

	public String getDatabaseName() {
		return mDatabaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.mDatabaseName = databaseName;
	}

	/**
	 * @return the afsqLiteEventListener
	 */
	public AFSQLiteEventListener getAfsqLiteEventListener() {
		return afsqLiteEventListener;
	}

	/**
	 * @param afsqLiteEventListener the afsqLiteEventListener to set
	 */
	public void setAfsqLiteEventListener(AFSQLiteEventListener afsqLiteEventListener) {
		this.afsqLiteEventListener = afsqLiteEventListener;
	}

}

class MySqliteOpenHelper extends AFSQLiteOpenHelper {
	private Context _context;

	public MySqliteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		_context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// Create Directories//
		if(!MFObjectUtils.isNull(getAfSQLiteeventListener()))
		{
			getAfSQLiteeventListener().onCreate(db);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if(!MFObjectUtils.isNull(getAfSQLiteeventListener()))
		{
			getAfSQLiteeventListener().onUpgrade(db, oldVersion, newVersion);
		}
	}

}
