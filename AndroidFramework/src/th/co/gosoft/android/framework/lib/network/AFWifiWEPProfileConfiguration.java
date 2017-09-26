package th.co.gosoft.android.framework.lib.network;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class AFWifiWEPProfileConfiguration {
	public final static String IPASS_STATIC = "STATIC";
	public final static String IPASS_DHCP = "DHCP";
	String password;
	String ssid;
	String ip_assignment;
	String ipaddress;
	String ipgateway;
	String ipdns1;
	String ipdns2;

	public void save(Context context) {
		try {
			WifiConfiguration wifiConfig = new WifiConfiguration();
			wifiConfig.SSID = getSsid();
			wifiConfig.preSharedKey = "\"".concat(getPassword()).concat("\"");
			wifiConfig.hiddenSSID = false;
			wifiConfig.status = WifiConfiguration.Status.ENABLED;
			wifiConfig.allowedGroupCiphers
					.set(WifiConfiguration.GroupCipher.TKIP);
			wifiConfig.allowedGroupCiphers
					.set(WifiConfiguration.GroupCipher.CCMP);
			wifiConfig.allowedKeyManagement
					.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			wifiConfig.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.TKIP);
			wifiConfig.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.CCMP);
			wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

			/* IPAssignMent */
			setIpAssignment(getIp_assignment(), wifiConfig);
			setIpAddress(InetAddress.getByName(getIpaddress()), 24, wifiConfig);
			setGateway(InetAddress.getByName(getIpgateway()), wifiConfig);
			setDNS(InetAddress.getByName(getIpdns1()),
					InetAddress.getByName(getIpdns2()), wifiConfig);

			// Add Advance Option//

			WifiManager wifiManag = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			boolean res1 = wifiManag.setWifiEnabled(true);
			int res = wifiManag.addNetwork(wifiConfig);
			boolean b = wifiManag.enableNetwork(wifiConfig.networkId, false);
			boolean c = wifiManag.saveConfiguration();
			boolean d = wifiManag.enableNetwork(res, true);
			AFLogUtils.d(String.format("AFWifiWEPProfileConfiguration:enablewifi:%s,addnetwork:%d,b:%s,c:%s,d:%s", res1,res,b,c,d));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// FIXME As above, what should I do here?
			AFLogUtils.e(e.toString(), e);
		}

	}

	private void setIpAssignment(String assign, WifiConfiguration wifiConf)
			throws SecurityException, IllegalArgumentException,
			NoSuchFieldException, IllegalAccessException {
		setEnumField(wifiConf, assign, "ipAssignment");
	}

	private void setIpAddress(InetAddress addr, int prefixLength,
			WifiConfiguration wifiConf) throws SecurityException,
			IllegalArgumentException, NoSuchFieldException,
			IllegalAccessException, NoSuchMethodException,
			ClassNotFoundException, InstantiationException,
			InvocationTargetException {
		Object linkProperties = getField(wifiConf, "linkProperties");
		if (linkProperties == null)
			return;
		Class laClass = Class.forName("android.net.LinkAddress");
		Constructor laConstructor = laClass.getConstructor(new Class[] {
				InetAddress.class, int.class });
		Object linkAddress = laConstructor.newInstance(addr, prefixLength);

		ArrayList mLinkAddresses = (ArrayList) getDeclaredField(linkProperties,
				"mLinkAddresses");
		mLinkAddresses.clear();
		mLinkAddresses.add(linkAddress);
	}

	private void setGateway(InetAddress gateway, WifiConfiguration wifiConf)
			throws SecurityException, IllegalArgumentException,
			NoSuchFieldException, IllegalAccessException,
			ClassNotFoundException, NoSuchMethodException,
			InstantiationException, InvocationTargetException {
		Object linkProperties = getField(wifiConf, "linkProperties");
		if (linkProperties == null)
			return;
		Class routeInfoClass = Class.forName("android.net.RouteInfo");
		Constructor routeInfoConstructor = routeInfoClass
				.getConstructor(new Class[] { InetAddress.class });
		Object routeInfo = routeInfoConstructor.newInstance(gateway);

		ArrayList mRoutes = (ArrayList) getDeclaredField(linkProperties,
				"mRoutes");
		mRoutes.clear();
		mRoutes.add(routeInfo);
	}

	private void setDNS(InetAddress dns1, InetAddress dns2,
			WifiConfiguration wifiConf) throws SecurityException,
			IllegalArgumentException, NoSuchFieldException,
			IllegalAccessException {
		Object linkProperties = getField(wifiConf, "linkProperties");
		if (linkProperties == null)
			return;

		ArrayList<InetAddress> mDnses = (ArrayList<InetAddress>) getDeclaredField(
				linkProperties, "mDnses");
		mDnses.clear(); // or add a new dns address , here I just want to
						// replace DNS1
		mDnses.add(dns1);
		// mDnses.add(dns2);
	}

	private Object getField(Object obj, String name) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		Field f = obj.getClass().getField(name);
		Object out = f.get(obj);
		return out;
	}

	private Object getDeclaredField(Object obj, String name)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field f = obj.getClass().getDeclaredField(name);
		f.setAccessible(true);
		Object out = f.get(obj);
		return out;
	}

	private void setEnumField(Object obj, String value, String name)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field f = obj.getClass().getField(name);
		f.set(obj, Enum.valueOf((Class<Enum>) f.getType(), value));
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = "\""+ssid+"\"";
	}

	public String getIp_assignment() {
		return ip_assignment;
	}

	public void setIp_assignment(String ip_assignment) {
		this.ip_assignment = ip_assignment;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getIpgateway() {
		return ipgateway;
	}

	public void setIpgateway(String ipgateway) {
		this.ipgateway = ipgateway;
	}

	public String getIpdns1() {
		return ipdns1;
	}

	public void setIpdns1(String ipdns1) {
		this.ipdns1 = ipdns1;
	}

	public String getIpdns2() {
		return ipdns2;
	}

	public void setIpdns2(String ipdns2) {
		this.ipdns2 = ipdns2;
	}

}
