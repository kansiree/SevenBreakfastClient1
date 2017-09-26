package th.co.gosoft.android.framework.lib.views;

import hqmobileframework.common.utils.MFObjectUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import th.co.gosoft.android.framework.R;
import th.co.gosoft.android.framework.lib.image.AFImageManager;
import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class AFDrawableView extends View {

	ScrollView scView;
	private static final float STROKE_WIDTH = 5f;

	/** Need to track this so the dirty region can accommodate the stroke. **/
	private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;

	final private Paint paint = new Paint();
	private Path path = new Path();
	Context mContext;
	int dColor = Color.BLACK;
	float dStrokeWidth = 5;
	Bitmap restore=null;
	private boolean isDraw =false;
	private boolean isBlank =true;

	/**
	 * Optimizes painting by invalidating the smallest possible area.
	 */
	private float lastTouchX;
	private float lastTouchY;
	private final RectF dirtyRect = new RectF();

	public AFDrawableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext =context;
		initalMyAttrs(attrs);
		initial();

	}
	public boolean save(String path) throws FileNotFoundException {
		if(MFObjectUtils.isNull(getDrawingCache()))return false;
		AFImageManager.saveBitmap(getDrawingCache(), path, CompressFormat.PNG);
		return true;
	}
	public void restore(String path)
	{
		
		restore=BitmapFactory.decodeFile(path);
		setBlank(false);
	}
	public void restore(Bitmap bitmap)
	{
		
		restore=bitmap;
		setBlank(false);
	}
	
	private void initial() {
		setDrawingCacheEnabled(true);
		paint.setAntiAlias(true);
		paint.setColor(dColor);
		paint.setStrokeWidth(dStrokeWidth);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
	}

	private void initalMyAttrs(AttributeSet attrs) {
		TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs,
				R.styleable.DrawableView, 0, 0);
		dColor = a.getColor(R.styleable.DrawableView_drawColor, dColor);
		dStrokeWidth = a.getDimension(R.styleable.DrawableView_strokeWidth,
				dStrokeWidth)
				/ getResources().getDisplayMetrics().scaledDensity;

	}

	public void setParentScrollView(ScrollView scrollVIew) {
		this.scView = scrollVIew;

	}

	/**
	 * Erases the signature.
	 */
	public void clear() {
		isDraw=true;
		restore=null;
		setBlank(true);
		path.reset();
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(!MFObjectUtils.isNull(restore))
		{
			canvas.drawBitmap(restore, 0, 0, paint);
		}
		canvas.drawPath(path, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getX();
		float eventY = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (scView != null)
				scView.requestDisallowInterceptTouchEvent(true);
			path.moveTo(eventX, eventY);
			lastTouchX = eventX;
			lastTouchY = eventY;
			// There is no end point yet, so don't waste cycles invalidating.
			return true;

		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
			resetDirtyRect(eventX, eventY);
			int historySize = event.getHistorySize();
			for (int i = 0; i < historySize; i++) {
				float historicalX = event.getHistoricalX(i);
				float historicalY = event.getHistoricalY(i);
				expandDirtyRect(historicalX, historicalY);
				path.lineTo(historicalX, historicalY);
			}
			path.lineTo(eventX, eventY);
			break;
		case MotionEvent.ACTION_CANCEL:
			if (scView != null)
				scView.requestDisallowInterceptTouchEvent(false);
			break;
		default:
			// debug("Ignored touch event: " + event.toString());
			return true;
		}
		invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
				(int) (dirtyRect.top - HALF_STROKE_WIDTH),
				(int) (dirtyRect.right + HALF_STROKE_WIDTH),
				(int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

		lastTouchX = eventX;
		lastTouchY = eventY;
		setDraw(true);
		setBlank(false);
		return true;
	}

	/**
	 * Called when replaying history to ensure the dirty region includes all
	 * points.
	 */
	private void expandDirtyRect(float historicalX, float historicalY) {
		if (historicalX < dirtyRect.left) {
			dirtyRect.left = historicalX;
		} else if (historicalX > dirtyRect.right) {
			dirtyRect.right = historicalX;
		}
		if (historicalY < dirtyRect.top) {
			dirtyRect.top = historicalY;
		} else if (historicalY > dirtyRect.bottom) {
			dirtyRect.bottom = historicalY;
		}
	}

	/**
	 * Resets the dirty region when the motion event occurs.
	 */
	private void resetDirtyRect(float eventX, float eventY) {

		dirtyRect.left = Math.min(lastTouchX, eventX);
		dirtyRect.right = Math.max(lastTouchX, eventX);
		dirtyRect.top = Math.min(lastTouchY, eventY);
		dirtyRect.bottom = Math.max(lastTouchY, eventY);
	}
	public boolean isDraw() {
		return isDraw;
	}
	public void setDraw(boolean isDraw) {
		this.isDraw = isDraw;
	}
	public boolean isBlank() {
		return isBlank;
	}
	public void setBlank(boolean isBlank) {
		this.isBlank = isBlank;
	}

}
