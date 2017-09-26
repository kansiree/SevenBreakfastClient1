package th.co.gosoft.android.framework.lib.dialog;

import hqmobileframework.common.utils.MFObjectUtils;
import th.co.gosoft.android.framework.R;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AFListDialog extends Dialog {

	  public Activity activity;
	  public AFListDialogEventListener dialogEvent;
	  private String[] items;
	  private String title="";
	  private int iconDrawable=-1;
	  
	  public AFListDialog(Activity activity) {
	    super(activity);
	    this.activity = activity;
	   // this.dialogEvent = dialogEvent;
	    
	  }
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setCanceledOnTouchOutside(false);
	    setContentView(R.layout.dialog_custom_list);
	    
	    TextView lb_name = (TextView) findViewById(R.id.lb_name);
	    
	    if(getIconDrawable()!=-1)
	    {
	    	ImageView icon = (ImageView)findViewById(R.id.img_icon);
	    	icon.setImageDrawable(activity.getResources().getDrawable(getIconDrawable()));
	    }
	    lb_name.setText(getTitle());
	    ListView listView = (ListView) findViewById(R.id.list_dialog);
	    Button bt_cancel = (Button) findViewById(R.id.bt_cancel);
	    final AFListDialogAdapter adapter = new AFListDialogAdapter(activity, getItems());
	    listView.setAdapter(adapter);
	    listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view, int index, long id) {
				// TODO Auto-generated method stub
				   if (!MFObjectUtils.isNull(getDialogEvent())){
					   getDialogEvent().onItemClickListener(index, view.findViewById(R.id.tv_data));
				   }
			}
		});
	    bt_cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				   if (!MFObjectUtils.isNull(getDialogEvent())){
					 getDialogEvent().onCancel();
				   }
			}
		});
	    
	  }
	   
	  public AFListDialogEventListener getDialogEvent() {
			return dialogEvent;
		}


		public void setDialogEvent(AFListDialogEventListener dialogEvent) {
			this.dialogEvent = dialogEvent;
		}


		public String[] getItems() {
			return items;
		}


		public void setItems(String[] items) {
			this.items = items;
		}


		public String getTitle() {
			return title;
		}


		public void setTitle(String title) {
			this.title = title;
		}

		/**
		 * @return the iconDrawable
		 */
		public int getIconDrawable() {
			return iconDrawable;
		}

		/**
		 * @param iconDrawable the iconDrawable to set
		 */
		public void setIconDrawable(int iconDrawable) {
			this.iconDrawable = iconDrawable;
		}


	  
}
