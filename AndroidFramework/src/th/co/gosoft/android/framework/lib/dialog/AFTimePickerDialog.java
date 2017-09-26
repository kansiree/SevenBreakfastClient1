package th.co.gosoft.android.framework.lib.dialog;

import hqmobileframework.common.utils.MFDateUtils;
import hqmobileframework.common.utils.MFObjectUtils;

import java.util.Calendar;
import java.util.Date;

import th.co.gosoft.android.framework.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class AFTimePickerDialog extends Dialog  {

	Activity activity;
	private String title;
	private String bt_cancel;
	private String bt_ok;    
	Date initialTime;
	private AFTimePickerDialogEventListerner afTimePickerDialogEventListerner;

   // final SimpleDateFormat dateViewFormatter = new SimpleDateFormat("HH:mm");
	public AFTimePickerDialog(Activity activity,Date initialTime) {
		super(activity);
	    this.activity = activity;
	   this.initialTime =initialTime;
	    
	}

	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCanceledOnTouchOutside(false);
	    View theInflatedView = getLayoutInflater().inflate(R.layout.dialog_custom_timepicker, null);
	    setContentView(theInflatedView);
	    initialUIText(theInflatedView);
	    final TimePicker timePicker = (TimePicker) theInflatedView.findViewById(R.id.dialog_timepicker);
	    

        final Calendar c = Calendar.getInstance();
        c.setTime(initialTime);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

	    Calendar choosenDate = Calendar.getInstance();
	    hour = choosenDate.get(Calendar.HOUR_OF_DAY);
	    minute = choosenDate.get(Calendar.MINUTE);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        
	   
    
	}
	private void initialUIText(View v)
	{
		  final TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_timepicker);
		    final TextView dateTitle = (TextView) v.findViewById(R.id.dialog_title);
		    final Button bt_cancel = (Button) v.findViewById(R.id.dialog_cancel);
			final Button bt_select = (Button) v.findViewById(R.id.dialog_select);
		    
			timePicker.setIs24HourView(true);
			
		    if (getTitle()!=null){
		    	dateTitle.setText(getTitle());
		    }

		    if (getBt_cancel()!=null){
		    	bt_cancel.setText(getBt_cancel());
		    }

		    if (getBt_ok()!=null){
		    	bt_select.setText(getBt_ok());
		    }
		    
		    bt_cancel.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				    dismiss();
				}
			});
		
		    bt_select.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					Calendar c = Calendar.getInstance(MFDateUtils.defaultLocale);
					c.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
					c.set(Calendar.MINUTE, timePicker.getCurrentMinute());
					if(!MFObjectUtils.isNull(getAfTimePickerDialogEventListerner()))
					{
						getAfTimePickerDialogEventListerner().onTimeSelected(c.getTime());
					}
					dismiss();
				}
			});
		    
	}
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getBt_cancel() {
		return bt_cancel;
	}


	public void setBt_cancel(String bt_cancel) {
		this.bt_cancel = bt_cancel;
	}


	public String getBt_ok() {
		return bt_ok;
	}


	public void setBt_ok(String bt_ok) {
		this.bt_ok = bt_ok;
	}

	/**
	 * @return the afTimePickerDialogEventListerner
	 */
	public AFTimePickerDialogEventListerner getAfTimePickerDialogEventListerner() {
		return afTimePickerDialogEventListerner;
	}

	/**
	 * @param afTimePickerDialogEventListerner the afTimePickerDialogEventListerner to set
	 */
	public void setAfTimePickerDialogEventListerner(
			AFTimePickerDialogEventListerner afTimePickerDialogEventListerner) {
		this.afTimePickerDialogEventListerner = afTimePickerDialogEventListerner;
	}

	
	  
}
