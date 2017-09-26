package th.co.gosoft.android.framework.lib.dialog;

import th.co.gosoft.android.framework.R;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AFToastDialog  {

	private String text;
	private long showTime=2000;
	private Context mContext;
	private int mIcon=-1;
	int toastLength =Toast.LENGTH_LONG;
	
	public AFToastDialog(Context context) {
		
		mContext = context;
	}
	public AFToastDialog(Context context, String text){
		this.setText(text);
		mContext = context;
	}
	public AFToastDialog(Context context, String text,int toastLength){
		this.setText(text);
		mContext = context;
		this.toastLength =toastLength;
	}
	public void show() {
       View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_toast_custom,null);
       // setContentView(R.layout.dialog_toast_custom);
		TextView tv_msg = (TextView) v.findViewById(R.id.toast_text);
		tv_msg.setText(this.getText());

		if (getIcon()!=-1){
			ImageView tv_img = (ImageView) v.findViewById(R.id.toast_img);
			tv_img.setImageDrawable(mContext.getResources().getDrawable(getIcon()));
		}
		 Toast toast = new Toast(mContext);
	        // Set layout to toast
	        toast.setView(v);
	        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
	                0, 0);
	        toast.setDuration(this.toastLength);
	        toast.show();
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getShowTime() {
		return showTime;
	}
	public void setShowTime(long showTime) {
		this.showTime = showTime;
	}
	public int getIcon() {
		return mIcon;
	}
	public void setIcon(int mIcon) {
		this.mIcon = mIcon;
	}
	
	
}
