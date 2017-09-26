package th.co.gosoft.android.framework.lib.calendar;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

public class AFCalendarManager {

	// calId=1 calendar in blue google account calendar
	// calId=2 calendar in red private calendar
	final static int CAL_1_BLUE = 1;
	final static int CAL_1_RED = 2;
	final static String TIME_ZONE = "Asia/Bangkok";

	public static long insertEvent(Context context, int colorCalId,
			long startMillis, long endMillis, String title, String description) {

		ContentResolver cr = context.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(Events.DTSTART, startMillis);
		values.put(Events.DTEND, endMillis);
		values.put(Events.TITLE, title);
		values.put(Events.DESCRIPTION, description);
		values.put(Events.CALENDAR_ID, colorCalId);
		// values.put(Events.VISIBLE, 1);
		values.put(Events.EVENT_TIMEZONE, TIME_ZONE);
		Uri uri = cr.insert(Events.CONTENT_URI, values);
		// get the event ID that is the last element in the Uri
		return Long.parseLong(uri.getLastPathSegment());

	}

	public static final String[] DEFAULT_EVENT_PROJECTION = new String[] {
			CalendarContract.Events.TITLE,
			CalendarContract.Events.DESCRIPTION,
			CalendarContract.Events.CALENDAR_ID 
	};

	public static Cursor queryCalendar(Context context) {
		ContentResolver cr = context.getContentResolver();
		Uri uri = CalendarContract.Events.CONTENT_URI;
		// Submit the query and get a Cursor object back.
		return cr.query(uri, DEFAULT_EVENT_PROJECTION, null, null, null);

	}

	public static Cursor queryCalendar(Context context, String[] calendarColumn) {
		ContentResolver cr = context.getContentResolver();
		Uri uri = CalendarContract.Events.CONTENT_URI;
		// Submit the query and get a Cursor object back.
		return cr.query(uri, calendarColumn, null, null, null);

	}
}
