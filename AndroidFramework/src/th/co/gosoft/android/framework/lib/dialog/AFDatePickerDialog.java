package th.co.gosoft.android.framework.lib.dialog;

import hqmobileframework.common.utils.MFDateUtils;
import hqmobileframework.common.utils.MFObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import th.co.gosoft.android.framework.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class AFDatePickerDialog extends Dialog {

	Activity activity;
	private String title;
	private String bt_cancel;
	private String bt_ok;
	private Date minimumDate;
	private Date initialDate;

	private AFDatePickerDialogEventListerner afDatePickerDialogEventListerner;
	final SimpleDateFormat dateViewFormatter = new SimpleDateFormat(
			"dd/MM/yyyy");

	public AFDatePickerDialog(Activity activity, Date initialDate) {
		super(activity);
		this.activity = activity;
		this.initialDate = initialDate;

	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCanceledOnTouchOutside(false);
		View theInflatedView = getLayoutInflater().inflate(
				R.layout.dialog_custom_datepicker, null);
		setContentView(theInflatedView);
		final DatePicker datePicker = (DatePicker) theInflatedView
				.findViewById(R.id.dialog_datepicker);

		initialUIText(theInflatedView);

		if (!MFObjectUtils.isNull(getMinimumDate())) {
			datePicker.setMinDate(getMinimumDate().getTime());
		}

		Calendar choosenDate = Calendar.getInstance(MFDateUtils.defaultLocale);
		choosenDate.setTime(initialDate);
		int year = choosenDate.get(Calendar.YEAR);
		int month = choosenDate.get(Calendar.MONTH);
		int day = choosenDate.get(Calendar.DAY_OF_MONTH);

		Calendar dateToDisplay = Calendar.getInstance();
		dateToDisplay.set(year, month, day);

		datePicker.init(year, month, day,
				new DatePicker.OnDateChangedListener() {
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						Calendar choosenDate = Calendar.getInstance();
						choosenDate.set(year, monthOfYear, dayOfMonth);
					}
				});

	}

	private void initialUIText(View v) {
		final DatePicker datePicker = (DatePicker) v
				.findViewById(R.id.dialog_datepicker);
		final TextView dateTitle = (TextView) v.findViewById(R.id.dialog_title);
		final Button bt_cancel = (Button) v.findViewById(R.id.dialog_cancel);
		final Button bt_select = (Button) v.findViewById(R.id.dialog_select);
		if (getTitle() != null) {
			dateTitle.setText(getTitle());
		}

		if (getBt_cancel() != null) {
			bt_cancel.setText(getBt_cancel());
		}

		if (getBt_ok() != null) {
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
				Calendar choosen = Calendar.getInstance();
				choosen.set(datePicker.getYear(), datePicker.getMonth(),
						datePicker.getDayOfMonth());
				if (!MFObjectUtils
						.isNull(getAfDatePickerDialogEventListerner())) {
					getAfDatePickerDialogEventListerner().onDateSelected(
							choosen.getTime());
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
	 * @return the afDatePickerDialogEventListerner
	 */
	public AFDatePickerDialogEventListerner getAfDatePickerDialogEventListerner() {
		return afDatePickerDialogEventListerner;
	}

	/**
	 * @param afDatePickerDialogEventListerner
	 *            the afDatePickerDialogEventListerner to set
	 */
	public void setAfDatePickerDialogEventListerner(
			AFDatePickerDialogEventListerner afDatePickerDialogEventListerner) {
		this.afDatePickerDialogEventListerner = afDatePickerDialogEventListerner;
	}

	/**
	 * @return the minimumDate
	 */
	public Date getMinimumDate() {
		return minimumDate;
	}

	/**
	 * @param minimumDate
	 *            the minimumDate to set
	 */
	public void setMinimumDate(Date minimumDate) {
		this.minimumDate = minimumDate;
	}

}
