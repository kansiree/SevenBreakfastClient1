package th.co.gosoft.android.framework.lib.authen;

import hqmobileframework.common.security.MFStringEncrypterDecrypter;
import hqmobileframework.common.utils.MFDateUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;


public class AFAuthenRequestUser {

	/**
	 * 
	 * @param baseUrl https://localhost:443
	 * @param imeiOrId
	 * @param phoneNumber
	 * @param version
	 * @param macAddress
	 * @return
	 * @throws Exception
	 */
	public static AFAuthenObject request(String baseUrl,String imeiOrId,String phoneNumber,String version,String macAddress,String type) throws Throwable
	{
		//https://setopap6:38181/StoreMessageMobileAuthen
		AFAuthenObject authenObject=null;
		if(!baseUrl.toLowerCase().contains("https"))throw new Exception("Support https only.");
		String url = baseUrl+ "/StoreMessageMobileAuthen/MobileAuthen.do?p="
				+ encodeParam("imei=" + imeiOrId + "&telephone=" + phoneNumber
						+ "&version=" +version + "&mac="+macAddress+"&type="+type);
		
	
		AFLogUtils.i("request:"+url);
		HttpsURLConnection https=null;
		try {
			trustServer();
			https = (HttpsURLConnection) new URL(url)
					.openConnection();

			https.setDoOutput(true);
			https.setDoInput(true);
			https.setRequestMethod("GET");
			https.setConnectTimeout(10000);
			https.setReadTimeout(10000);
			https.setRequestProperty("Connection", "close");
			https.connect();
			if (https.getResponseCode() >= 200 && https.getResponseCode() < 300) {
				AFLogUtils.i("Accepted code:" + https.getResponseCode());
				InputStream ins = https.getInputStream();
				byte[] buf = new byte[256];
				int read;
				String append = "";
				while ((read = ins.read(buf)) > 0) {
					append += new String(buf, 0, read);
				}

				AFLogUtils.i("Receive:" + append);
				authenObject = new AFAuthenObject(append);
			} else {
				AFLogUtils.i("Error : " + https.getResponseCode() + ":"
						+ https.getResponseMessage());
			}
		
		} finally {
			if (https != null) {
				https.disconnect();
			}
		}
		
		return authenObject;
		
	}
	private static void trustServer() {
		try {
			// TrustHost//

			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				
				
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(
						java.security.cert.X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(
						java.security.cert.X509Certificate[] certs,
						String authType) {
				}
			} };

			SSLContext sc = SSLContext.getInstance("SSLv3");
			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			};
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		} catch (Exception e) {
			AFLogUtils.e(e.toString(),e);
		}
	}
	private static String encodeParam(String param)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(MFStringEncrypterDecrypter.encrypt(param,
				AFAuthenConfig.KEY), AFAuthenConfig.CHARSET);
	}
}

