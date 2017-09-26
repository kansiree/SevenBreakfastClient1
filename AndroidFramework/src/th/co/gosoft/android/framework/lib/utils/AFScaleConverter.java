package th.co.gosoft.android.framework.lib.utils;

import android.content.Context;
import android.util.TypedValue;

public class AFScaleConverter {
	public static int toDPScale(Context context, int pixel) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				pixel, context.getResources().getDisplayMetrics());
	}

	public static int toPXScale(Context context, int sp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				sp, context.getResources().getDisplayMetrics());
	}
}
