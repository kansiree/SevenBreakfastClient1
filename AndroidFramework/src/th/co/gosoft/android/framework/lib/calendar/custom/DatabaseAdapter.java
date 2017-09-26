package th.co.gosoft.android.framework.lib.calendar.custom;

import hqmobileframework.common.utils.MFObjectUtils;
import th.co.gosoft.android.framework.lib.database.AFSQLiteAdapter;
import th.co.gosoft.android.framework.lib.database.AFSQLiteEventListener;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {
	final static String DATABASE_NAME = "AFCALENDAR";
	final static int DATABASE_VERSION = 1;
	static AFSQLiteAdapter afsqLiteAdapter;

	public static SQLiteDatabase openReadableDatabase(Context context) {
		if (MFObjectUtils.isNull(afsqLiteAdapter)) {
			init();
		}
		return afsqLiteAdapter.getOpenHelperInstance(context)
				.getReadableDatabase();
	}

	public static SQLiteDatabase openWriteDatabase(Context context) {
		if (MFObjectUtils.isNull(afsqLiteAdapter)) {
			init();
		}
		return afsqLiteAdapter.getOpenHelperInstance(context)
				.getWritableDatabase();
	}

	private static void init() {
		afsqLiteAdapter = new AFSQLiteAdapter(DATABASE_NAME, DATABASE_VERSION);
		afsqLiteAdapter.setAfsqLiteEventListener(new AFSQLiteEventListener() {

			public void onUpgrade(SQLiteDatabase db, int oldVersion,
					int newVersion) {
				// TODO Auto-generated method stub

			}

			public void onCreate(SQLiteDatabase db) {
				// TODO Auto-generated method stub
				final String CREATE_CALENDAR = "CREATE TABLE "
						+ CalendarObject.TABLE_NAME + " ("
						+ CalendarObject.COLUMN_ID + " INTEGER PRIMARY KEY,"
						+ CalendarObject.COLUMN_TYPE + " INTEGER,"
						+ CalendarObject.COLUMN_TITLE + " TEXT,"
						+ CalendarObject.COLUMN_DESCRIPTION + " TEXT,"
						+ CalendarObject.COLUMN_START + " INTEGER,"
						+ CalendarObject.COLUMN_END + " INTEGER,"
						+ CalendarObject.COLUMN_REMIND + " INTEGER)";
				db.execSQL(CREATE_CALENDAR);
			}
		});
	}
}
