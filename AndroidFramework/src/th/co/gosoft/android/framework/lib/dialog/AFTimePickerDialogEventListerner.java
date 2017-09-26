package th.co.gosoft.android.framework.lib.dialog;

import java.util.Date;
import java.util.EventListener;

public interface AFTimePickerDialogEventListerner extends EventListener{
  public void onTimeSelected(Date date);
}
