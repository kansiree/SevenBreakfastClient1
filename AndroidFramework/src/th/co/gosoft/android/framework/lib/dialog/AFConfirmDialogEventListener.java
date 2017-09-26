package th.co.gosoft.android.framework.lib.dialog;

import java.util.EventListener;

public interface AFConfirmDialogEventListener extends EventListener {
	public void onConfirm();
	public void onCancel();
}
