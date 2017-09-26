package th.co.gosoft.android.framework.lib.database;

import hqmobileframework.common.utils.MFObjectUtils;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class AFSQLiteOpenHelper extends SQLiteOpenHelper {
	private AFSQLiteEventListener afSQLiteeventListener;

	protected AFSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	public AFSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public SQLiteDatabase getReaderDatabase() {
		return super.getReadableDatabase();
	}

	public SQLiteDatabase getWriterDatabase() {
		return super.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		if (!MFObjectUtils.isNull(getAfSQLiteeventListener())) {
			getAfSQLiteeventListener().onCreate(db);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (!MFObjectUtils.isNull(getAfSQLiteeventListener())) {
			getAfSQLiteeventListener().onUpgrade(db, oldVersion, newVersion);
		}

	}

	public AFSQLiteEventListener getAfSQLiteeventListener() {
		return afSQLiteeventListener;
	}

	public void setAfSQLiteeventListener(
			AFSQLiteEventListener afSQLiteeventListener) {
		this.afSQLiteeventListener = afSQLiteeventListener;
	}

}
