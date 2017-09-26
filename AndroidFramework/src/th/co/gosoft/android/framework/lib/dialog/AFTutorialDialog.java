package th.co.gosoft.android.framework.lib.dialog;

import java.util.List;

import th.co.gosoft.android.framework.R;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AFTutorialDialog extends Dialog  {

	Activity activity;
	List<AFTutorialDialogModel> tutorialList;
	int countSeq;
    int width, height;
    AFTutorialDialogModel tutorial;
	int boxWidth = 0;
	int boxHeight = 0;
	RelativeLayout rltBox;
	ImageView ivArrow;
	RelativeLayout rltImg;
	TextView tvMessageTop;
	TextView tvMessageBottom;

	public AFTutorialDialog(Activity activity, List<AFTutorialDialogModel> tutorialList) {
		super(activity);
		this.activity = activity;
		this.tutorialList = tutorialList;
		this.countSeq = 0;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		if (tutorialList.size()>0){
			tutorial=tutorialList.get(countSeq);
			setContentView(R.layout.dialog_tutorial);
			initial();
		}else{
			this.dismiss();
		}
	}

	private void initial(){

		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	    if (Build.VERSION.SDK_INT > VERSION_CODES.FROYO) {
			width = displaymetrics.widthPixels;
			height = displaymetrics.heightPixels;
			
	    } else {
	        Point point = new Point();
	        activity.getWindowManager().getDefaultDisplay().getSize(point);
	        width = point.x;
	        height = point.y;
	    }
	    
	    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(this.getWindow().getAttributes());
	    lp.width = width;
	    lp.height = height;
	    this.getWindow().setAttributes(lp);

	    setDialogContent();
	    
	}
	
	private void setDialogContent(){
		
		boxWidth = 0;
		boxHeight = 0;

		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		final int screenWidth = size.x;
		final int screenHeight = size.y;

		ivArrow = (ImageView) findViewById(R.id.dialog_tutorial_iv_arrow);
		tvMessageTop = (TextView) findViewById(R.id.dialog_tutorial_tv_message_top);
		tvMessageBottom = (TextView) findViewById(R.id.dialog_tutorial_tv_message_bottom);
		ivArrow.setImageResource(R.drawable.ic_arrow_left);
		tvMessageTop.setText("");
		tvMessageBottom.setText("");

		if (tutorial.getMessage().length()>0){
			tvMessageTop.setText(Html.fromHtml(tutorial.getMessage()));
			tvMessageBottom.setText(Html.fromHtml(tutorial.getMessage()));
		}

		if (tutorial.getIcon()>0){
			ivArrow.setImageResource(tutorial.getIcon());
		}
		
		rltImg = (RelativeLayout) findViewById(R.id.dialog_tutorial_rlt_panel_arrow);
		rltBox = (RelativeLayout) findViewById(R.id.dialog_tutorial_rlt_panel);
		rltBox.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
		{
		    @Override
		    public void onGlobalLayout()
		    {
		    	if (boxWidth==0){
			    	boxWidth = rltBox.getWidth();
			    	boxHeight = rltBox.getHeight();
			    	

					if (tutorial.getWidth()>0){;
						boxWidth = tutorial.getWidth();
						rltBox.getLayoutParams().width = boxWidth;
					}
					rltImg.getLayoutParams().width = boxWidth;
					
					
					int posX = tutorial.getX();
					int posY = tutorial.getY();
					
			    	if ((posX+boxWidth)<=screenWidth){
			    		if ((posY+boxHeight)<=screenHeight){
							// Top Left
			    			rltBox.setX(posX);
			    			rltBox.setY(posY);
							rltImg.setGravity(Gravity.LEFT);
							if (tutorial.getIcon()<1){
								ivArrow.setImageResource(R.drawable.ic_arrow_left);
							}
				    		tvMessageTop.setVisibility(View.GONE);
				    		tvMessageBottom.setVisibility(View.VISIBLE);
						}else{
							// Bottom Left 
			    			rltBox.setX(posX);
			    			rltBox.setY(posY-boxHeight);
							rltImg.setGravity(Gravity.LEFT);
							if (tutorial.getIcon()<1){
								ivArrow.setImageResource(R.drawable.ic_arrow_left_bt);
							}
				    		tvMessageTop.setVisibility(View.VISIBLE);
				    		tvMessageBottom.setVisibility(View.GONE);
						}
			    		
			    	}else{
			    		if ((posY+boxHeight)<=screenHeight){
							// Top Right
			    			rltBox.setX(posX-boxWidth);
			    			rltBox.setY(posY);
							rltImg.setGravity(Gravity.RIGHT);
							if (tutorial.getIcon()<1){
								ivArrow.setImageResource(R.drawable.ic_arrow_right);
							}
				    		tvMessageTop.setVisibility(View.GONE);
				    		tvMessageBottom.setVisibility(View.VISIBLE);
			    			
						}else{
							// Bottom Right
			    			rltBox.setX(posX-boxWidth);
			    			rltBox.setY(posY-boxHeight);
							rltImg.setGravity(Gravity.RIGHT);
							if (tutorial.getIcon()<1){
								ivArrow.setImageResource(R.drawable.ic_arrow_right_bt);
							}
				    		tvMessageTop.setVisibility(View.VISIBLE);
				    		tvMessageBottom.setVisibility(View.GONE);
						}
			    	}
			    	

					
		    	}
		    	
		    }
		});
	}
	
	public boolean onTouchEvent(MotionEvent event)  
	{  
	    int eventAction = event.getAction();
	    if (eventAction==MotionEvent.ACTION_DOWN){
    		if (countSeq<tutorialList.size()-1){
	    		countSeq += 1;
				tutorial=tutorialList.get(countSeq);
	    	    setDialogContent();
			}else{
				this.dismiss();
			}
	    }
		return false;  
	}
	
	
}
