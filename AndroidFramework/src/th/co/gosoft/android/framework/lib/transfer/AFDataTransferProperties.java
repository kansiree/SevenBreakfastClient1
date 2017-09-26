package th.co.gosoft.android.framework.lib.transfer;

import hqmobileframework.common.utils.MFFileUtils;

import java.net.MalformedURLException;
import java.net.URL;

import android.os.Environment;

public class AFDataTransferProperties {

	private String tempFilePath = Environment.getExternalStorageDirectory()+"/af/temp/";
	private String charSet ="UTF-8";
	private String arg0="";
	public void setUrl(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public URL getUrl() {
		return this.url;
	}

	/**
	 * @return the tempFile
	 */
	public String getTempFilePath() {
		MFFileUtils.makeDir(tempFilePath);
		return tempFilePath;
	}

	/**
	 * @param tempFile the tempFile to set
	 */
	public void setTempFilePath(String tempFilePath) {
		this.tempFilePath = tempFilePath;
	}

	/**
	 * @return the charSet
	 */
	public String getCharSet() {
		return charSet;
	}

	/**
	 * @param charSet the charSet to set
	 */
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	/**
	 * @return the arg0
	 */
	public String getArg0() {
		return arg0;
	}

	/**
	 * @param arg0 the arg0 to set
	 */
	public void setArg0(String arg0) {
		this.arg0 = arg0;
	}



	private URL url;
}
