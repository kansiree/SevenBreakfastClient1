package th.co.gosoft.android.framework.lib.calendar.custom;

import java.util.Date;

import android.database.Cursor;

public class CalendarObject {
	public static final String TABLE_NAME = "CALENDAR_TABLE";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TYPE = "event_type";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_START = "start_date";
	public static final String COLUMN_END = "end_date";
	public static final String COLUMN_REMIND = "remind_time";

	long id;
	int type;
	String title;
	private String description;
	Date startDate;
	Date endDate;
	private long remind;

	public void parse(Cursor cursor) {
		setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
		setType(cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE)));
		setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
		setStartDate(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_START))));
		setEndDate(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_END))));
		setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
		setRemind(cursor.getLong(cursor.getColumnIndex(COLUMN_REMIND)));
	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getRemind() {
		return remind;
	}

	public void setRemind(long remind) {
		this.remind = remind;
	}
}
