package th.co.gosoft.android.framework.lib.dialog;

import java.util.Date;
import java.util.EventListener;

public interface AFDatePickerDialogEventListerner extends EventListener{
  public void onDateSelected(Date date);
}
