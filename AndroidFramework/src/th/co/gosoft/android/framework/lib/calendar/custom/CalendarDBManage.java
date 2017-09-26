package th.co.gosoft.android.framework.lib.calendar.custom;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class CalendarDBManage {
	public static void addCalendarEvent(String title, int customType,
			Date startDate, Date endDate, String description, long remind,
			Context context)

	{
		ContentValues c = new ContentValues();
		c.put(CalendarObject.COLUMN_ID, (getMaxCalendarId(context) + 1));
		c.put(CalendarObject.COLUMN_TYPE, customType);
		c.put(CalendarObject.COLUMN_TITLE, title);
		c.put(CalendarObject.COLUMN_START, startDate.getTime());
		c.put(CalendarObject.COLUMN_END, endDate.getTime());
		c.put(CalendarObject.COLUMN_DESCRIPTION, description);
		c.put(CalendarObject.COLUMN_REMIND, remind);
		DatabaseAdapter.openWriteDatabase(context).insert(
				CalendarObject.TABLE_NAME, null, c);
	}



	public static Cursor getAllCalendarEvent(Context context) {

		String sql = "select * from " + CalendarObject.TABLE_NAME
				+ " order by " + CalendarObject.COLUMN_TYPE + ","
				+ CalendarObject.COLUMN_ID;
		return DatabaseAdapter.openReadableDatabase(context)
				.rawQuery(sql, null);

	}

	public static CalendarObject getCalendarEventById(long eventId,
			Context context) {

		String sql = "select * from " + CalendarObject.TABLE_NAME + " where "
				+ CalendarObject.COLUMN_ID + "=" + eventId;

		Cursor c = DatabaseAdapter.openReadableDatabase(context).rawQuery(sql,
				null);
		CalendarObject cObj = null;
		if (c.moveToFirst()) {
			cObj = new CalendarObject();
			cObj.parse(c);
		}

		return cObj;

	}

	public static void deleteCalendarEventById(long eventId, Context context) {

		String sql = "delete from " + CalendarObject.TABLE_NAME + " where "
				+ CalendarObject.COLUMN_ID + "=" + eventId;

		DatabaseAdapter.openWriteDatabase(context).execSQL(sql);

	}

	public static Cursor getCalendarEvent(Context context, int customType) {
		String sql = "select " + CalendarObject.COLUMN_DESCRIPTION + " from "
				+ CalendarObject.TABLE_NAME + " where "
				+ CalendarObject.COLUMN_TYPE + "=" + customType + " group by "
				+ CalendarObject.COLUMN_DESCRIPTION + " order by "
				+ CalendarObject.COLUMN_ID;
		return DatabaseAdapter.openReadableDatabase(context)
				.rawQuery(sql, null);

	}

	public static int getMaxCalendarId(Context context) {
		String sql = "select max(" + CalendarObject.COLUMN_ID + ") as m from "
				+ CalendarObject.TABLE_NAME;
		Cursor c = DatabaseAdapter.openReadableDatabase(context).rawQuery(sql,
				null);
		if (c.moveToFirst()) {
			return c.getInt(0);
		} else {
			return 0;
		}
	}
}
