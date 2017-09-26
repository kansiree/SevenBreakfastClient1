package th.co.gosoft.android.framework.lib.dialog;

import th.co.gosoft.android.framework.R;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AFProgressDialogWithButton extends AlertDialog {
 
	private String text;
	private String btText;
	public AFProgressDialogWithButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public AFProgressDialogWithButton(Context context, String _text,String btText,OnCancelListener onCancel){
		super(context);
		this.setText(_text);
		this.setBtText(btText);
		this.setCancelable(true);
		this.setOnCancelListener(onCancel);
		
	}
	@Override
	public void show() {
        super.show();
        setContentView(R.layout.dialog_pregressbar_custom_with_button);
		TextView tv_msg = (TextView) findViewById(R.id.prog_text);
		Button bt = (Button)findViewById(R.id.bt);
		tv_msg.setText(this.getText());
		bt.setText(getBtText());
		bt.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancel();
			}
		});
	}
 
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getBtText() {
		return btText;
	}

	public void setBtText(String btText) {
		this.btText = btText;
	}

}
