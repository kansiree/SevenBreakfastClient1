package th.co.gosoft.android.framework.lib.network;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

public class AFWifiOpenProfileConfiguration {
String ssid;
	
	public void save(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiConfiguration wc = new WifiConfiguration();
		wc.SSID = getSsid(); // IMP! This should be in Quotes!!
		wc.hiddenSSID = false;
		wc.status = WifiConfiguration.Status.ENABLED;
		wc.priority = 40;
		wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
		wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
	

		boolean res1 = wifi.setWifiEnabled(true);
		int res = wifi.addNetwork(wc);
		boolean b = wifi.saveConfiguration();
		boolean c = wifi.enableNetwork(res, true);
		AFLogUtils.d(String.format("AFWifiOpenProfileConfiguration:%s,addnetwork:%d,b:%s,c:%s,", res1,res,b,c));
		
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = "\""+ssid+"\"";
	}
}
