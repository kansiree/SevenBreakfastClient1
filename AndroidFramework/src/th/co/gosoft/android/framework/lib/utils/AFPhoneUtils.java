package th.co.gosoft.android.framework.lib.utils;

import hqmobileframework.common.utils.MFObjectUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import th.co.gosoft.android.framework.lib.exception.AFNotSupportException;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

public class AFPhoneUtils {

	public static void acceptNetworkOnMainThread() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	public static void copyText2Clipboard(Context context, String text) {
		final ClipboardManager clipboard = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		if (android.os.Build.VERSION.SDK_INT > 10) {
			clipboard.setText(text);
		} else {
			ClipData clipData = ClipData.newPlainText("copy", text);
			clipboard.setPrimaryClip(clipData);
		}
	}
	public static String getAppVersionName(Context context) throws NameNotFoundException {
		
			PackageInfo pInfo = context.getPackageManager().getPackageInfo(
					 context.getPackageName(), 0);
			return  pInfo.versionName;
		
	}
	public static int getAppVersionCode(Context context) throws NameNotFoundException {
		
		PackageInfo pInfo = context.getPackageManager().getPackageInfo(
				 context.getPackageName(), 0);
		return  pInfo.versionCode;
	
}
	public static void restartInputMethod(Context context, EditText ed) {
		InputMethodManager inputManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.restartInput(ed);
	}

	public static final String formatImei(String imei) {
		MessageFormat phoneMsgFmt = new MessageFormat(
				"{0} - {1} - {2} - {3} - {4}");
		String[] imeiArray = { imei.substring(0, 3), imei.substring(3, 6),
				imei.substring(6, 9), imei.substring(9, 12), imei.substring(12) };

		return phoneMsgFmt.format(imeiArray);
	}

	public static String getMediaPath(Uri uri, Context context) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(uri, projection,
				null, null, null);
		if (cursor != null) {
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	@Deprecated
	public static final SimpleDateFormat dateFormatToSqlite = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	@Deprecated
	public static final SimpleDateFormat dateTimeFormatShow = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss", Locale.US);
	@Deprecated
	public static final SimpleDateFormat timeChatFormatShow = new SimpleDateFormat(
			"HH:mm", Locale.US);
	@Deprecated
	public static final SimpleDateFormat dateFormatShow = new SimpleDateFormat(
			"dd/MM/yyyy", Locale.US);
	@Deprecated
	public static final SimpleDateFormat dateFormatUniq = new SimpleDateFormat(
			"ddMMyyyyHHmmsssss", Locale.US);
	@Deprecated
	public static final SimpleDateFormat dateTimeFormatShowList = new SimpleDateFormat(
			"dd MMM yy, HH:mm", Locale.US);

	public static String getPhoneNumber1(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}

	public static String getImei(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getDeviceId();
	}

	public static String getAndroidDataVersion(Context context) {
		return "1:" + android.os.Build.ID + " 2:" + android.os.Build.BRAND
				+ " 3:" + android.os.Build.HARDWARE + " 4:"
				+ android.os.Build.BOOTLOADER + " 5:"
				+ android.os.Build.FINGERPRINT + " 6:" + android.os.Build.MODEL
				+ " 7:" + android.os.Build.MANUFACTURER;
	}

	public static boolean isAPKFile(String fileName) {
		if (fileName.toLowerCase().endsWith("apk")) {
			return true;
		}
		return false;
	}

	@Deprecated
	public static void createDirectory(String path) throws Exception {
		File f = new File(path);
		if (!f.exists()) {
			if (!f.mkdir())
				f.mkdirs();
		}
	}

	public static Intent bringActivitytoFront(Context context, Class clazz) {
		Intent i = new Intent(context, clazz);
		i.setAction(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

		return i;
	}

	public static void restartActivity(Class clazz, Context context,
			Activity activity) {
		Intent i = new Intent(context, clazz);
		i.setAction(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		context.startActivity(i);
		activity.finish();
	}
@Deprecated
	public static String getWifiMacAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wInfo = wifiManager.getConnectionInfo();

		return wInfo.getMacAddress();
	}

	public static void alertInfo(Context context, String title, String message,
			String buttonMsg) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);

		alert.setTitle(title);
		alert.setMessage(message);
		alert.setNegativeButton(buttonMsg,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						dialog.cancel();

					}
				});
		alert.show();
	}

	static MediaScannerConnection mediaScan = null;

	public static void updateMediaStore(Activity activity, final String path,final AFEventListener event) {
//		if (android.os.Build.VERSION.SDK_INT < 19) {
//			activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
//					.parse("file://" + path)));
//		} else {
			String[] fs;
			File p = new File(path);
			if (p.isDirectory()) {
				File[] lFile = p.listFiles();
				fs = new String[lFile.length];
				int i = 0;
				for (File f : lFile) {
					fs[i++] = f.getAbsolutePath();
				}
			}else
			{
				fs =new String[]{p.getAbsolutePath()};
			}

			MediaScannerConnection.scanFile(activity, fs, null,
					new MediaScannerConnection.OnScanCompletedListener() {
						public void onScanCompleted(String path, Uri uri) {
							if(event!=null)
							{
								event.onEvent(uri);
							}

						}

					});
		//}

	}
	public static void deleteImageFromMediaStore( Context context, String path )
	{
	    Uri rootUri = MediaStore.Files.getContentUri("external");
	    String[] fs;
		File p = new File(path);
		if (p.isDirectory()) {
			File[] lFile = p.listFiles();
			fs = new String[lFile.length];
			int i = 0;
			for (File f : lFile) {
				fs[i++] = f.getAbsolutePath();
			}
		}else
		{
			fs =new String[]{p.getAbsolutePath()};
		}
		for(String f:fs){
	    context.getContentResolver().delete( rootUri, 
	        MediaStore.MediaColumns.DATA + "=?",new String[]{ f} );
		}
	}

	public static void alertConfirm(Context context, String title,
			String message, String buttonConfirm, String buttonCancel,
			DialogInterface.OnClickListener onConfirm) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);

		alert.setTitle(title);
		alert.setMessage(message);

		alert.setPositiveButton(buttonConfirm, onConfirm);
		alert.setNegativeButton(buttonCancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		alert.show();
	}

	public static void startByContentAuto(Context context, String filePath)
			throws AFNotSupportException {
		try {
			Uri uri = Uri.fromFile(new File(filePath));
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			String mime = "*/*";
			MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
			if (mimeTypeMap.hasExtension(MimeTypeMap
					.getFileExtensionFromUrl(uri.toString())))
				mime = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap
						.getFileExtensionFromUrl(uri.toString()));
			intent.setDataAndType(uri, mime);
			context.startActivity(intent);
		} catch (Throwable t) {
			throw new AFNotSupportException(
					"Content not application supported.");
		}

	}

	public static boolean isPortrait(Context context) {
		WindowManager window = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = window.getDefaultDisplay();
		switch (display.getRotation()) {
		case Surface.ROTATION_0:
			return true;
		case Surface.ROTATION_90:
			return false;
		case Surface.ROTATION_180:
			return true;
		case Surface.ROTATION_270:
			return false;
		}
		return true;
	}

	public static void makeToastShort(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void makeToastLong(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
@Deprecated
	public static boolean haveNetwork(Context context) {
		final ConnectivityManager conMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		} else
			return false;
	}
@Deprecated
	public static NetworkInfo haveNetworkAndInfo(Context context) {
		final ConnectivityManager conMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return activeNetwork;
		} else
			return null;
	}

	public static void browseFromGallery(Activity activity, int requestCode) {
		browseFromGalleryDo(activity, requestCode, "Select Picture");
	}

	public static void browseFromGallery(Activity activity, int requestCode,
			String title) {
		browseFromGalleryDo(activity, requestCode, title);
	}

	private static void browseFromGalleryDo(Activity activity, int requestCode,
			String titlel) {

		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_PICK);
		intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
		activity.startActivityForResult(intent, requestCode);
	}

	public static Bitmap browseFromGallery2Bitmap(Activity activity,
			Intent intent) {
		Uri selectedImageUri = intent.getData();
		return BitmapFactory
				.decodeFile(getMediaPath(selectedImageUri, activity));
	}

	public static String browseFromGallery2Path(Activity activity, Intent intent) {
		Uri selectedImageUri = intent.getData();
		return getMediaPath(selectedImageUri, activity);
	}

	public static void hideSoftKeyboard(Activity activity) {
		activity.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	public static void hiddenKeyboard(Context c, View v) {
		InputMethodManager keyboard = (InputMethodManager) c
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	public static boolean isPackageExisted(Context context, String targetPackage) {
		PackageManager pm = context.getPackageManager();
		try {
			pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			return false;
		}
		return true;
	}

	public static boolean packageApplicationExist(Context context,
			String packageName) {
		try {
			PackageManager m = context.getPackageManager();
			return MFObjectUtils.isNull(m.getPackageInfo(packageName, 0)) ? false
					: true;
		} catch (NameNotFoundException n) {
			return false;
		}
	}

	@Deprecated
	public static void openGooglePlay(Context context, String packageName) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_NO_HISTORY);
		intent.setData(Uri.parse("market://details?id=" + packageName));
		context.startActivity(intent);
	}

	public static void startActivity(Context context, int flag,
			String packageName, String activityName, Uri data) {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setComponent(new ComponentName(packageName, activityName));
		intent.setFlags(flag);
		intent.setData(data);
		context.startActivity(intent);
	}

	public static boolean ping(String hostName, int timeout) {
		try {
			InetAddress address = InetAddress.getByName(hostName);
			return address.isReachable(timeout);
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

	}

	public static boolean ping(byte[] ipaddress, int timeout) {
		try {
			InetAddress address = InetAddress.getByAddress(ipaddress);
			return address.isReachable(timeout);
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

	}
	public static boolean isServiceRunning(Context context,
			String serviceClassName) {
		final ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		final List<RunningServiceInfo> services = activityManager
				.getRunningServices(Integer.MAX_VALUE);

		for (RunningServiceInfo runningServiceInfo : services) {
			if (runningServiceInfo.service.getClassName().equals(
					serviceClassName)) {

				return true;
			}
		}
		return false;
	}
}
