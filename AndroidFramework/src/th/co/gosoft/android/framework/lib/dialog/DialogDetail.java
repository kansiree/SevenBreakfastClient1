package th.co.gosoft.android.framework.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import th.co.gosoft.android.framework.R;

/**
 * Created by print on 9/12/2017.
 */

public class DialogDetail extends Dialog {
    String distance,time;
    public AFConfirmDialogEventListener dialogEvent;
    public DialogDetail(@NonNull Context context,String distance,String time,AFConfirmDialogEventListener dialogEvent) {
        super(context);
        this.dialogEvent=dialogEvent;
        this.distance = distance;
        this.time = time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detail_map);
        TextView textView = (TextView)findViewById(R.id.distance);
        TextView textView1 = (TextView)findViewById(R.id.time);
        Button button = (Button)findViewById(R.id.bt_dismiss);
        textView.setText(distance);
        textView1.setText(time);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

    }
}
