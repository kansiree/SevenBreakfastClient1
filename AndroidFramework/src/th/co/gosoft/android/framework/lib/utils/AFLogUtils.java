package th.co.gosoft.android.framework.lib.utils;

import android.util.Log;

public class AFLogUtils {
	final static String LOG_TAG = "AFLOG";

	public static void i(String msg) {
		Log.i(LOG_TAG, msg);
	}
	public static void d(String msg)
	{
		Log.d(LOG_TAG, msg);
	}
	public static void e(String msg)
	{
		Log.e(LOG_TAG, msg);
	}
	public static void e(String msg,Throwable t)
	{
		Log.e(LOG_TAG, msg,t);
	}
}
