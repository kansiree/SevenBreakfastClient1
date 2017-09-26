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

public class AFWifiEAPProfileConfiguration {
	public final static String IPASS_STATIC="STATIC";
	public final static String IPASS_DHCP="DHCP";
	String userName;
	String password;
	String ssid;
	String ip_assignment;
	String ipaddress;
	String ipgateway;
	String ipdns1;
	String ipdns2;
	

	
	public void save(Context context) {

		/******************************** Configuration Strings ****************************************************/
		final String INT_PRIVATE_KEY = "private_key";
		final String INT_PHASE2 = "phase2";
		final String INT_PASSWORD = "password";
		final String INT_IDENTITY = "identity";
		final String INT_EAP = "eap";
		final String INT_CLIENT_CERT = "client_cert";
		final String INT_CA_CERT = "ca_cert";
		final String INT_ANONYMOUS_IDENTITY = "anonymous_identity";
		final String INT_ENTERPRISEFIELD_NAME = "android.net.wifi.WifiConfiguration$EnterpriseField";
		final String INT_IPASSINGMENT_NAME = "android.net.wifi.WifiConfiguration$IpAssignment";
		final String INT_IP_ASSIGNMENT_METHOD = "ipAssignment";
		final String INT_LINK_PROPERTIES = "linkProperties";
		final String ENTERPRISE_EAP = "PEAP";
		final String ENTERPRISE_CLIENT_CERT = "keystore://USRCERT_CertificateName";
		final String ENTERPRISE_PRIV_KEY = "keystore://USRPKEY_CertificateName";
		// CertificateName = Name given to the certificate while installing it

		/* Optional Params- My wireless Doesn't use these */
		final String ENTERPRISE_PHASE2 = "MSCHAPV2";
		final String ENTERPRISE_ANON_IDENT = userName;
		final String ENTERPRISE_CA_CERT = "";
		/******************************** Configuration Strings ****************************************************/

		/* Create a WifiConfig */
		WifiConfiguration wifiConfig = new WifiConfiguration();
		/* AP Name */
		wifiConfig.SSID = ssid;

		/* Priority */
		wifiConfig.priority = 40;

		/* Enable Hidden SSID */
		wifiConfig.hiddenSSID = false;

		/* Key Mgmnt */
		wifiConfig.allowedKeyManagement.clear();
		wifiConfig.allowedKeyManagement
				.set(WifiConfiguration.KeyMgmt.IEEE8021X);
		wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);

		/* Group Ciphers */
		wifiConfig.allowedGroupCiphers.clear();
		wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		wifiConfig.allowedGroupCiphers
				.set(WifiConfiguration.GroupCipher.WEP104);
		wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

		/* Pairwise ciphers */
		wifiConfig.allowedPairwiseCiphers.clear();
		wifiConfig.allowedPairwiseCiphers
				.set(WifiConfiguration.PairwiseCipher.CCMP);
		wifiConfig.allowedPairwiseCiphers
				.set(WifiConfiguration.PairwiseCipher.TKIP);

		/* Protocols */
		wifiConfig.allowedProtocols.clear();
		wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
		wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);

		// Enterprise Settings
		// Reflection magic here too, need access to non-public APIs
		try {
			// Let the magic start
			Class[] wcClasses = WifiConfiguration.class.getClasses();
			// null for overzealous java compiler
			Class wcEnterpriseField = null;
			Class wcIPAssignmentField = null;

			for (Class wcClass : wcClasses) {
				if (wcClass.getName().equals(INT_ENTERPRISEFIELD_NAME)) {
					wcEnterpriseField = wcClass;

				} else if (wcClass.getName().equals(INT_IPASSINGMENT_NAME)) {
					wcIPAssignmentField = wcClass;
				}
			}
			boolean noEnterpriseFieldType = false;
			if (wcEnterpriseField == null)
				noEnterpriseFieldType = true; // Cupcake/Donut access enterprise
												// settings directly

			Field wcefAnonymousId = null, wcefCaCert = null, wcefClientCert = null, wcefEap = null, wcefIdentity = null, wcefPassword = null, wcefPhase2 = null, wcefPrivateKey = null, wcefIPAssignment = null, wcefLinkProperties = null;
			Field[] wcefFields = WifiConfiguration.class.getFields();
			// Dispatching Field vars
			for (Field wcefField : wcefFields) {
				// Log.d("WifiPreference", "Fields:" + wcefField.getName());
				if (wcefField.getName().equals(INT_ANONYMOUS_IDENTITY))
					wcefAnonymousId = wcefField;
				else if (wcefField.getName().equals(INT_CA_CERT))
					wcefCaCert = wcefField;
				else if (wcefField.getName().equals(INT_CLIENT_CERT))
					wcefClientCert = wcefField;
				else if (wcefField.getName().equals(INT_EAP))
					wcefEap = wcefField;
				else if (wcefField.getName().equals(INT_IDENTITY))
					wcefIdentity = wcefField;
				else if (wcefField.getName().equals(INT_PASSWORD))
					wcefPassword = wcefField;
				else if (wcefField.getName().equals(INT_PHASE2))
					wcefPhase2 = wcefField;
				else if (wcefField.getName().equals(INT_PRIVATE_KEY))
					wcefPrivateKey = wcefField;
				else if (wcefField.getName().equals(INT_IP_ASSIGNMENT_METHOD))
					wcefIPAssignment = wcefField;
				else if (wcefField.getName().equals(INT_LINK_PROPERTIES)) {
					wcefLinkProperties = wcefField;
				}

			}

			Method wcefSetValue = null;
			if (!noEnterpriseFieldType) {
				for (Method m : wcEnterpriseField.getMethods())
					// System.out.println(m.getName());
					if (m.getName().trim().equals("setValue"))
						wcefSetValue = m;
			}
			/* EAP Method */
			wcefSetValue.invoke(wcefEap.get(wifiConfig), ENTERPRISE_EAP);
			/* EAP Phase 2 Authentication */
			wcefSetValue.invoke(wcefPhase2.get(wifiConfig), ENTERPRISE_PHASE2);
			/* EAP Anonymous Identity */
			wcefSetValue.invoke(wcefAnonymousId.get(wifiConfig),
					ENTERPRISE_ANON_IDENT);
			/* EAP CA Certificate */
		//	wcefSetValue.invoke(wcefCaCert.get(wifiConfig), ENTERPRISE_CA_CERT);
			/* EAP Private key */
		//	wcefSetValue.invoke(wcefPrivateKey.get(wifiConfig),
			//		ENTERPRISE_PRIV_KEY);

			/* EAP Identity */
			wcefSetValue.invoke(wcefIdentity.get(wifiConfig), userName);
			/* EAP Password */
			wcefSetValue.invoke(wcefPassword.get(wifiConfig), password);

			/* EAp Client certificate */
		//	wcefSetValue.invoke(wcefClientCert.get(wifiConfig),
		//			ENTERPRISE_CLIENT_CERT);

			/* EAp IPAssignMent */
			setIpAssignment(getIp_assignment(), wifiConfig);
			setIpAddress( InetAddress.getByName(getIpaddress()), 24, wifiConfig);
			setGateway(InetAddress.getByName(getIpgateway()), wifiConfig);
			setDNS(InetAddress.getByName(getIpdns1()),InetAddress.getByName(getIpdns2()), wifiConfig);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// FIXME As above, what should I do here?
			e.printStackTrace();
		}
		// Add Advance Option//

		WifiManager wifiManag = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		boolean res1 = wifiManag.setWifiEnabled(true);
		int res = wifiManag.addNetwork(wifiConfig);
		boolean b = wifiManag.enableNetwork(wifiConfig.networkId, false);
		boolean c = wifiManag.saveConfiguration();
		boolean d = wifiManag.enableNetwork(res, true);
		AFLogUtils.d(String.format("AFWifiEAPProfileConfiguration:%s,addnetwork:%d,b:%s,c:%s,d:%s", res1,res,b,c,d));
		
	}
	
	
	
	
	private void setIpAssignment(String assign , WifiConfiguration wifiConf)
			    throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException{
			        setEnumField(wifiConf, assign, "ipAssignment");     
			    }
	private void setIpAddress(InetAddress addr, int prefixLength, WifiConfiguration wifiConf)
			    throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException,
			    NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException{
			        Object linkProperties = getField(wifiConf, "linkProperties");
			        if(linkProperties == null)return;
			        Class laClass = Class.forName("android.net.LinkAddress");
			        Constructor laConstructor = laClass.getConstructor(new Class[]{InetAddress.class, int.class});
			        Object linkAddress = laConstructor.newInstance(addr, prefixLength);

			        ArrayList mLinkAddresses = (ArrayList)getDeclaredField(linkProperties, "mLinkAddresses");
			        mLinkAddresses.clear();
			        mLinkAddresses.add(linkAddress);        
			    }

			   private void setGateway(InetAddress gateway, WifiConfiguration wifiConf)
			    throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, 
			    ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException{
			        Object linkProperties = getField(wifiConf, "linkProperties");
			        if(linkProperties == null)return;
			        Class routeInfoClass = Class.forName("android.net.RouteInfo");
			        Constructor routeInfoConstructor = routeInfoClass.getConstructor(new Class[]{InetAddress.class});
			        Object routeInfo = routeInfoConstructor.newInstance(gateway);

			        ArrayList mRoutes = (ArrayList)getDeclaredField(linkProperties, "mRoutes");
			        mRoutes.clear();
			        mRoutes.add(routeInfo);
			    }

			   private void setDNS(InetAddress dns1,InetAddress dns2, WifiConfiguration wifiConf)
			    throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException{
			        Object linkProperties = getField(wifiConf, "linkProperties");
			        if(linkProperties == null)return;

			        ArrayList<InetAddress> mDnses = (ArrayList<InetAddress>)getDeclaredField(linkProperties, "mDnses");
			        mDnses.clear(); //or add a new dns address , here I just want to replace DNS1
			        mDnses.add(dns1); 
			        mDnses.add(dns2); 
			    }

			   private Object getField(Object obj, String name)
			    throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
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
			    throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
			        Field f = obj.getClass().getField(name);
			        f.set(obj, Enum.valueOf((Class<Enum>) f.getType(), value));
			    }
			   public String getUserName() {
					return userName;
				}




				public void setUserName(String userName) {
					this.userName = userName;
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
