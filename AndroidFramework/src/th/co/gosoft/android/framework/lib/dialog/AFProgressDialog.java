package th.co.gosoft.android.framework.lib.dialog;

import th.co.gosoft.android.framework.R;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;

public class AFProgressDialog extends AlertDialog {
 
	private String text;
	private TextView textView;
	public AFProgressDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public AFProgressDialog(Context context, String _text){
		super(context);
		this.setText(_text);
		this.setCancelable(false);
	}
	@Override
	public void show() {
        super.show();
        setContentView(R.layout.dialog_pregressbar_custom);
		textView= (TextView) findViewById(R.id.prog_text);
		textView.setText(this.getText());
	}
 
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
		if(textView!=null)textView.setText(text);
	}

}
