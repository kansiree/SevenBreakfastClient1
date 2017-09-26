package th.co.gosoft.android.framework.lib.dialog;

import th.co.gosoft.android.framework.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AFListDialogAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
    
	 public AFListDialogAdapter(Context context, String[] values) {
	    super(context, R.layout.dialog_list_item, values);
	    this.context = context;
	    this.values = values;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.dialog_list_item, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.tv_data);
	    textView.setText(values[position]);
	    return rowView;
	  }
}

