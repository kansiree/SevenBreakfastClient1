package th.co.gosoft.android.framework.lib.calendar.custom;

import java.util.Date;
import java.util.EventListener;

public interface CalendarChangeEvent extends EventListener{
	public void onSelectedDate(Date date);
}
