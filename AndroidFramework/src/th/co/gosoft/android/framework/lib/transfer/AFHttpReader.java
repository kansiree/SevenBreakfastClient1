package th.co.gosoft.android.framework.lib.transfer;

import hqmobileframework.common.utils.MFFileUtils;
import hqmobileframework.common.utils.MFObjectUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.os.Environment;

import th.co.gosoft.android.framework.lib.exception.AFHTTPException;
import th.co.gosoft.android.framework.lib.utils.AFLogUtils;

public class AFHttpReader {

	private int connectionTimeOut = 120*1000;
	private int readTimeOut = 300*1000;//sec
	final private int retry = 0;
	final private long sleepForRetry = 1000;
	private int memoryBuf = 256;
	private OutputStream outputStream = null;
	public static final String METHOD_GET="GET";
	public static final String METHOD_POST="POST";
	private String method = METHOD_GET;
	private AFHttpReaderEventListener afHttpReaderEventListener;
	private HashMap<String, String> customHeader;

	public void readHTML(URL url) throws IOException, InterruptedException {
		if (isHttps(url))
			readHttps(url);
		else
			readHttp(url);

	}

	public void readHTML(URL url, HashMap<String, String> postData)
			throws IOException, Exception {
		if (isHttps(url))
			readHttps(url, postData);
		else
			readHttp(url, postData);

	}

	public void readHTML(URL url, InputStream inputStream) throws IOException,
			Exception {
		if (isHttps(url))
			readHttps(url, inputStream);
		else
			readHttp(url, inputStream);

	}

	public boolean isHttps(URL url) {
		return "https".equalsIgnoreCase(url.getProtocol()) ? true : false;
	}

	public boolean isHttp(URL url) {
		return "http".equalsIgnoreCase(url.getProtocol()) ? true : false;
	}

	public boolean isFile(URL url) {
		return "file".equalsIgnoreCase(url.getProtocol()) ? true : false;
	}

	private void readHttp(URL url) throws IOException, InterruptedException {
		HttpURLConnection http = null;
		int retryCount = -1;
		boolean success = false;
		while (retryCount < retry) {
			try {
				retryCount++;
				http = (HttpURLConnection) url.openConnection();
				preparedRequestHeader(http);
				http.setConnectTimeout(getConnectionTimeOut());
				http.setReadTimeout(getReadTimeOut());
				http.connect();
				fireOnConnected();
				if (http.getResponseCode() >= 200
						&& http.getResponseCode() < 300) {
					fireOnHttpAccepted(http.getHeaderFields());
					AFLogUtils.d("HTTPReader Accepted code:"
							+ http.getResponseCode());
					InputStream ins = http.getInputStream();
					long length = getContentLength(http);
					byte[] buf = new byte[memoryBuf];
					int read;
					int received = 0;
					while ((read = ins.read(buf)) > 0) {
						getOutputStream().write(buf, 0, read);
						received += read;
						fireOnDownloadProgress(calPercent(received, length));
					}
					fireOnDownloadSuccess();
					success = true;

				} else {
					fireOnHttpDenied(new AFHTTPException("HTTP read error : ",
							http.getResponseCode(), http.getResponseMessage()));

				}
				break;
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(sleepForRetry);
			} finally {
				if (http != null) {
					http.disconnect();
				}
			}
		}
		if (!success) {
			fireOnConnectFail();
		}

	}

	private void readHttp(URL url, HashMap<String, String> postData)
			throws AFHTTPException, InterruptedException {
		HttpURLConnection http = null;
		int retryCount = -1;
		boolean success = false;
		while (retryCount < retry) {
			try {
				retryCount++;
				http = (HttpURLConnection) url.openConnection();
				preparedRequestHeader(http);
				http.setConnectTimeout(getConnectionTimeOut());
				http.setReadTimeout(getReadTimeOut());
				http.connect();
				fireOnConnected();
				OutputStream os = http.getOutputStream();
				String out = "";
				for (String key : postData.keySet()) {
					if ("".equals(out)) {
						out += key + "="
								+ URLEncoder.encode(postData.get(key), "UTF-8");
					} else {
						out += "&" + key + "=";
						out += URLEncoder.encode(postData.get(key), "UTF-8");
					}
				}
				os.write(out.getBytes(), 0, out.getBytes().length);
				os.flush();
				fireOnUploadSuccess();
				if (http.getResponseCode() >= 200
						&& http.getResponseCode() < 300) {
					fireOnHttpAccepted(http.getHeaderFields());
					AFLogUtils.d("HTTPReader Accepted code:"
							+ http.getResponseCode());
					InputStream ins = http.getInputStream();
					long length = getContentLength(http);
					byte[] buf = new byte[memoryBuf];
					int received = 0;
					int read;
					while ((read = ins.read(buf)) > 0) {
						getOutputStream().write(buf, 0, read);
						received += read;
						fireOnDownloadProgress(calPercent(received, length));
					}
					fireOnDownloadSuccess();
					success = true;

				} else {
					AFLogUtils.e("HTTPReader Read error:"
							+ http.getResponseCode()+":"+http.getResponseMessage());
					fireOnHttpDenied(new AFHTTPException("HTTP read error : ",
							http.getResponseCode(), http.getResponseMessage()));

				}
				break;
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(sleepForRetry);
			} finally {
				if (http != null) {
					http.disconnect();
				}
			}
		}
		if (!success) {
			fireOnConnectFail();
		}

	}
	
	private void readHttp(URL url, InputStream inputStream)
			throws AFHTTPException, InterruptedException {
		HttpURLConnection http = null;
		boolean success = false;
		int retryCount = -1;
		while (retryCount < retry) {
			try {
				retryCount++;
				http = (HttpURLConnection) url.openConnection();
				preparedRequestHeader(http);
				http.setConnectTimeout(getConnectionTimeOut());
				http.setReadTimeout(getReadTimeOut());
				http.connect();
				OutputStream os = http.getOutputStream();
				fireOnConnected();
				byte[] buf = new byte[memoryBuf];
				int read;
				int sended = 0;
				//String  bound = "boundary=gc0p4Jq0M2Yt08jU534c0p";
				//os.write(bound.getBytes());
				while ((read = inputStream.read(buf)) > 0) {
					os.write(buf, 0, read);
					sended += read;
					fireOnUploadBytes(sended);
				}
				//os.write(("--"+bound).getBytes());
				os.flush();
				fireOnUploadSuccess();
				if (http.getResponseCode() >= 200
						&& http.getResponseCode() < 300) {
					fireOnHttpAccepted(http.getHeaderFields());
					AFLogUtils.d("HTTPReader Accepted code:"
							+ http.getResponseCode()+" for "+url);
					long length = getContentLength(http);
					InputStream ins = http.getInputStream();
					int received = 0;
					while ((read = ins.read(buf)) > 0) {
						getOutputStream().write(buf, 0, read);
						received += read;
						fireOnDownloadProgress(calPercent(received, length));
					}
					fireOnDownloadSuccess();
					success = true;

				} else {
					AFLogUtils.e("HTTPReader Read error:"
							+ http.getResponseCode()+":"+http.getResponseMessage());
					fireOnHttpDenied(new AFHTTPException("HTTP read error : ",
							http.getResponseCode(), http.getResponseMessage()));

				}
				break;
			} catch (Throwable e) {
				AFLogUtils.e(e.toString(),e);
				e.printStackTrace();
				Thread.sleep(sleepForRetry);
			} finally {
				if (http != null) {
					http.disconnect();
				}
			}
		}
		if (!success) {
			fireOnConnectFail();
		}

	}

	private void readHttps(URL url) throws IOException, InterruptedException {
		HttpsURLConnection https = null;
		int retryCount = -1;
		boolean success = false;
		while (retryCount < retry) {
			try {
				retryCount++;
				trustServer();
				https = (HttpsURLConnection) url.openConnection();
				https.setDoOutput(true);
				https.setDoInput(true);
				https.setRequestMethod(getMethod());
				https.setConnectTimeout(getConnectionTimeOut());
				https.setReadTimeout(getReadTimeOut());
				https.setRequestProperty("Connection", "close");
				https.connect();
				fireOnConnected();
				if (https.getResponseCode() >= 200
						&& https.getResponseCode() < 300) {
					fireOnHttpAccepted(https.getHeaderFields());
					AFLogUtils.d("HTTPReader Accepted code:"
							+ https.getResponseCode());
					InputStream ins = https.getInputStream();
					long length = getContentLength(https);
					byte[] buf = new byte[memoryBuf];
					int read;
					int received = 0;
					while ((read = ins.read(buf)) > 0) {
						getOutputStream().write(buf, 0, read);
						received += read;
						fireOnDownloadProgress(calPercent(received, length));
					}
					fireOnDownloadSuccess();
					success = true;
				} else {
					AFLogUtils.e("HTTPReader Read error:"
							+ https.getResponseCode()+":"+https.getResponseMessage());
					fireOnHttpDenied(new AFHTTPException("HTTP read error : ",
							https.getResponseCode(), https.getResponseMessage()));

				}
				break;
			} catch (Exception ex) {
				Thread.sleep(sleepForRetry);
			} finally {
				if (https != null) {
					https.disconnect();
				}
			}
		}
		if (!success) {
			fireOnConnectFail();
		}

	}

	private void readHttps(URL url, HashMap<String, String> postData)
			throws Exception {
		HttpsURLConnection https = null;
		boolean success = false;
		int retryCount = -1;
		while (retryCount < retry) {
			try {
				retryCount++;
				trustServer();
				https = (HttpsURLConnection) url.openConnection();
				https.setDoOutput(true);
				https.setDoInput(true);
				https.setRequestMethod(getMethod());
				https.setConnectTimeout(getConnectionTimeOut());
				https.setReadTimeout(getReadTimeOut());
				https.setRequestProperty("Connection", "close");
				https.connect();
				fireOnConnected();
				OutputStream os = https.getOutputStream();
				String out = "";
				for (String key : postData.keySet()) {
					if ("".equals(out)) {
						out += key + "="
								+ URLEncoder.encode(postData.get(key), "UTF-8");
					} else {
						out += "&" + key + "=";
						out += URLEncoder.encode(postData.get(key), "UTF-8");
					}
				}
				os.write(out.getBytes(), 0, out.getBytes().length);
				os.flush();
				fireOnUploadSuccess();
				if (https.getResponseCode() >= 200
						&& https.getResponseCode() < 300) {
					AFLogUtils.d("HTTPReader Accepted code:"
							+ https.getResponseCode());
					InputStream ins = https.getInputStream();
					long length = getContentLength(https);
					byte[] buf = new byte[memoryBuf];
					int read;
					int received = 0;
					while ((read = ins.read(buf)) > 0) {
						getOutputStream().write(buf, 0, read);
						received += read;
						fireOnDownloadProgress(calPercent(received, length));
					}
					fireOnDownloadSuccess();
					success = true;

				} else {
					AFLogUtils.e("HTTPReader Read error:"
							+ https.getResponseCode()+":"+https.getResponseMessage());
					fireOnHttpDenied(new AFHTTPException("HTTP read error : ",
							https.getResponseCode(), https.getResponseMessage()));

				}
				break;
			} catch (Exception ex) {
				Thread.sleep(sleepForRetry);
				if (retryCount == retry) {
					throw new Exception(ex);
				}
			} finally {
				if (https != null) {
					https.disconnect();
				}
			}
		}
		if (!success) {
			fireOnConnectFail();
		}

	}

	private void readHttps(URL url, InputStream inputStream)
			throws AFHTTPException, InterruptedException {
		HttpsURLConnection http = null;
		boolean success = false;
		int retryCount = -1;
		while (retryCount < retry) {
			try {
				retryCount++;
				trustServer();
				http = (HttpsURLConnection) url.openConnection();
				preparedRequestHeader(http);
				http.setConnectTimeout(getConnectionTimeOut());
				http.setReadTimeout(getReadTimeOut());
				http.connect();
				OutputStream os = http.getOutputStream();
				fireOnConnected();
				byte[] buf = new byte[memoryBuf];
				int read;
				int sended = 0;
				//String  bound = "boundary=gc0p4Jq0M2Yt08jU534c0p";
				//os.write(bound.getBytes());
				while ((read = inputStream.read(buf)) > 0) {
					os.write(buf, 0, read);
					sended += read;
					fireOnUploadBytes(sended);
				}
				//os.write(("--"+bound).getBytes());
				os.flush();
				fireOnUploadSuccess();
				if (http.getResponseCode() >= 200
						&& http.getResponseCode() < 300) {
					fireOnHttpAccepted(http.getHeaderFields());
					AFLogUtils.d("HTTPReader Accepted code:"
							+ http.getResponseCode());
					long length = getContentLength(http);
					InputStream ins = http.getInputStream();
					int received = 0;
					while ((read = ins.read(buf)) > 0) {
						getOutputStream().write(buf, 0, read);
						received += read;
						fireOnDownloadProgress(calPercent(received, length));
					}
					fireOnDownloadSuccess();
					success = true;

				} else {
					AFLogUtils.e("HTTPReader Read error:"
							+ http.getResponseCode()+":"+http.getResponseMessage());
					fireOnHttpDenied(new AFHTTPException("HTTP read error : ",
							http.getResponseCode(), http.getResponseMessage()));

				}
				break;
			} catch (Exception e) {
				AFLogUtils.e(e.toString(),e);
				e.printStackTrace();
				Thread.sleep(sleepForRetry);
			} finally {
				if (http != null) {
					http.disconnect();
				}
			}
		}
		if (!success) {
			fireOnConnectFail();
		}

	}

	private void preparedRequestHeader(HttpURLConnection http)
			throws ProtocolException {
		http.setDoOutput(true);
		http.setDoInput(true);
		http.setRequestMethod(getMethod());
		// http.setRequestProperty("Accept-Language","th-th,th;q=0.8,en-us;q=0.6,en-gb;q=0.4,en;q=0.2");
		http.setRequestProperty("Accept-Encoding", "*");
		// http.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		// http.setRequestProperty("Host", "chimchill.tirapong.com");
		http.setRequestProperty("Connection", "close");
		// http.addRequestProperty("User-Agent",
		// "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0; .NET CLR 3.5; ffco7) Gecko/20100101 Firefox/26.0");
	     http.setRequestProperty("Content-Type", "application/octet-stream");
		if (customHeader != null)
			for (String key : customHeader.keySet()) {
				http.setRequestProperty(key, customHeader.get(key));
			}

	}
	private void preparedRequestHeader(HttpsURLConnection http)
			throws ProtocolException {
		http.setDoOutput(true);
		http.setDoInput(true);
		http.setRequestMethod(getMethod());
		// http.setRequestProperty("Accept-Language","th-th,th;q=0.8,en-us;q=0.6,en-gb;q=0.4,en;q=0.2");
		http.setRequestProperty("Accept-Encoding", "*");
		// http.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		// http.setRequestProperty("Host", "chimchill.tirapong.com");
		//http.setRequestProperty("Connection", "close");
		// http.addRequestProperty("User-Agent",
		// "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0; .NET CLR 3.5; ffco7) Gecko/20100101 Firefox/26.0");
	     http.setRequestProperty("Content-Type", "application/octet-stream");
		if (customHeader != null)
			for (String key : customHeader.keySet()) {
				http.setRequestProperty(key, customHeader.get(key));
			}

	}

	private long getContentLength(HttpURLConnection http) {
		// Map<String,List<String>>m =http.getHeaderFields();
		// for(String key:m.keySet())
		// {
		// List<String>value = m.get(key);
		// for(String s:value)
		// {
		// AFLogUtils.i("key:"+key+" value:"+s);
		// }
		// }
		long length = 0;
		try {
			length = Long.parseLong(http.getHeaderField("Content-Length"));
		} catch (NumberFormatException e) {

		}
		return length;
	}

	private long getContentLength(HttpsURLConnection https) {
		return Long.parseLong(https.getHeaderField("Content-Length"));
	}

	private void trustServer() {
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
			e.printStackTrace();
		}
	}

	/**
	 * @return the connectionTimeOut
	 */
	public int getConnectionTimeOut() {
		return this.connectionTimeOut;
	}

	/**
	 * @param connectionTimeOut
	 *            the connectionTimeOut to set
	 */
	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	/**
	 * @return the readTimeOut
	 */
	public int getReadTimeOut() {
		return this.readTimeOut;
	}

	/**
	 * @param readTimeOut
	 *            the readTimeOut to set
	 */
	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return this.method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	public int getMemoryBuf() {
		return memoryBuf;
	}

	public void setMemoryBuf(int memoryBuf) {
		this.memoryBuf = memoryBuf;
	}

	private int calPercent(long current, long total) {
		int per = (int) (current / (double) total * 100);
		return per;
	}

	private void fireOnConnected() {
		if (!MFObjectUtils.isNull(getAfHttpReaderEventListener())) {
			getAfHttpReaderEventListener().onConnected();
		}
	}

	private void fireOnConnectFail() {
		if (!MFObjectUtils.isNull(getAfHttpReaderEventListener())) {
			getAfHttpReaderEventListener().onConnectFail();
		}
	}

	private void fireOnUploadBytes(int bytes) {
		if (!MFObjectUtils.isNull(getAfHttpReaderEventListener())) {
			getAfHttpReaderEventListener().onUploadCount(bytes);
		}
	}

	private void fireOnDownloadProgress(int percent) {
		if (!MFObjectUtils.isNull(getAfHttpReaderEventListener())) {
			getAfHttpReaderEventListener().onDownloadProgress(percent);
		}
	}

	private void fireOnUploadSuccess() {
		if (!MFObjectUtils.isNull(getAfHttpReaderEventListener())) {
			getAfHttpReaderEventListener().onUploadSuccess();
		}
	}

	private void fireOnDownloadSuccess() {
		if (!MFObjectUtils.isNull(getAfHttpReaderEventListener())) {
			getAfHttpReaderEventListener().onDownloadSuccess();
		}
	}

	private void fireOnHttpAccepted(Map<String, List<String>> headerField) {
		if (!MFObjectUtils.isNull(getAfHttpReaderEventListener())) {
			getAfHttpReaderEventListener().onHttpAccepted(headerField);
		}
	}

	private void fireOnHttpDenied(AFHTTPException exception) {
		if (!MFObjectUtils.isNull(getAfHttpReaderEventListener())) {
			getAfHttpReaderEventListener().onHttpDenied(exception);
		}
	}

	/**
	 * @return the outputStream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * @param outputStream
	 *            the outputStream to set
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	/**
	 * @return the afHttpReaderEventListener
	 */
	public AFHttpReaderEventListener getAfHttpReaderEventListener() {
		return afHttpReaderEventListener;
	}

	/**
	 * @param afHttpReaderEventListener
	 *            the afHttpReaderEventListener to set
	 */
	public void setAfHttpReaderEventListener(
			AFHttpReaderEventListener afHttpReaderEventListener) {
		this.afHttpReaderEventListener = afHttpReaderEventListener;
	}

	/**
	 * @return the customHeader
	 */
	public HashMap<String, String> getCustomHeader() {
		return customHeader;
	}

	/**
	 * @param customHeader
	 *            the customHeader to set
	 */
	public void setCustomHeader(HashMap<String, String> customHeader) {
		this.customHeader = customHeader;
	}
}
