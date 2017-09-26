package th.co.gosoft.android.framework.lib.network;

import java.util.List;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class AFNetworkUtil {
	public static String getWifiMacAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wInfo = wifiManager.getConnectionInfo();

		return wInfo.getMacAddress();
	}

	public static boolean haveNetwork(Context context) {
		final ConnectivityManager conMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		} else
			return false;
	}

	public static NetworkInfo getNetworkInfo(Context context) {
		final ConnectivityManager conMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return activeNetwork;
		} else
			return null;
	}
	public static boolean isWifiEnabled(Context context)
	{
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.isWifiEnabled();
	}
	public static void setWifiEnabled(Context context,boolean enabled)
	{
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		 wifiManager.setWifiEnabled(enabled);
	}
	public static void removeAllNetworkRememberd(Context context)
	{
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		List<WifiConfiguration> wifiConf = wifi.getConfiguredNetworks();
		for(WifiConfiguration cinf:wifiConf)
		{
			wifi.removeNetwork(cinf.networkId);
			
		}
	}

}
