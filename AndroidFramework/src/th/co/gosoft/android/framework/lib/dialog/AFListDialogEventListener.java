package th.co.gosoft.android.framework.lib.dialog;

import java.util.EventListener;

public interface AFListDialogEventListener extends EventListener {
	public void onItemClickListener(int index, Object item);
	public void onCancel();
}
