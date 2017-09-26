package th.co.gosoft.android.framework.lib.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import th.co.gosoft.android.framework.R;

public class AFInfoDialog extends Dialog {

	public Activity activity;
	public AFInfoDialogEventListener dialogEvent;
	public Dialog d;
	public Button yes ;
	public String text;
	private String bt_yes_name;
	
	public AFInfoDialog(Activity activity, AFInfoDialogEventListener dialogEvent) {
		super(activity);
		this.activity = activity;
		this.dialogEvent = dialogEvent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable( new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setCanceledOnTouchOutside(false);
		setContentView(R.layout.dialog_custom_info);

		yes = (Button) findViewById(R.id.btn_yes);
		if (getBt_yes_name() != null) {
			yes.setText(getBt_yes_name());
		} else {
			yes.setText(activity.getResources().getString(
					R.string.af_dialog_msg_confirm));
		}

		yes.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
				//if(!MFObjectUtils.isNull(dialogEvent))dialogEvent.onConfirm();
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
}
