package th.co.gosoft.android.framework.lib.dialog;

import hqmobileframework.common.utils.MFObjectUtils;
import th.co.gosoft.android.framework.R;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class AFConfirmDialog extends Dialog {

	public Activity activity;
	public AFConfirmDialogEventListener dialogEvent;
	public Dialog d;
	public Button yes, no;
	public String text;
	private String bt_yes_name;
	private String bt_no_name;

	public AFConfirmDialog(Activity activity,
			AFConfirmDialogEventListener dialogEvent) {
		super(activity);
		// super(activity,android.R.style.Theme_Translucent);
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.dialogEvent = dialogEvent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setCanceledOnTouchOutside(false);
		setContentView(R.layout.dialog_custom_confirm);

		yes = (Button) findViewById(R.id.btn_yes);
		if (getBt_yes_name() != null) {
			yes.setText(getBt_yes_name());
		} else {
			yes.setText(activity.getResources().getString(
					R.string.af_dialog_msg_confirm));
		}

		no = (Button) findViewById(R.id.btn_no);
		if (getBt_no_name() != null) {
			no.setText(getBt_no_name());
		} else {
			no.setText(activity.getResources().getString(
					R.string.af_dialog_msg_cancel));
		}

		yes.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
				if(!MFObjectUtils.isNull(dialogEvent))dialogEvent.onConfirm();
			}
		});
		no.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
				if(!MFObjectUtils.isNull(dialogEvent))dialogEvent.onCancel();
			}
		});

		TextView tv = (TextView) findViewById(R.id.txt_dia);
		tv.setText(getText());

	}

	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBt_yes_name() {
		return bt_yes_name;
	}

	public void setBt_yes_name(String bt_yes_name) {
		this.bt_yes_name = bt_yes_name;
	}

	public String getBt_no_name() {
		return bt_no_name;
	}

	public void setBt_no_name(String bt_no_name) {
		this.bt_no_name = bt_no_name;
	}
}
