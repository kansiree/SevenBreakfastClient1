package th.co.gosoft.android.framework.lib.authen;

import hqmobileframework.common.security.MFStringEncrypterDecrypter;

public class AFAuthenObject {
	private String user;
	private String password;
	private String name;
	private String storeId;
	private String latestVersion;
	private String latestVersionUrl;
	private String latestContentVersion;
	private String latestContentVersionUrl;
	private boolean success = false;
	public AFAuthenObject(String row) {
		row = MFStringEncrypterDecrypter.decrypt(row,
				AFAuthenConfig.KEY);
		if(row==null)return;
		String[] authenData = row.split("\\|");
		if (authenData.length < 8) {
			return;
		}
		setUser(checkString(authenData[0]));
		setPassword(checkString(authenData[1]));
		setName(checkString(authenData[2]));
		setStoreId(checkString(authenData[3]));
		setLatestVersion(checkString(authenData[4]));
		setLatestVersionUrl(checkString(authenData[5]));
		setLatestContentVersion(checkString(authenData[6]));
		setLatestContentVersionUrl(checkString(authenData[7]));
		setSuccess(true);
	}

	private String checkString(String chk) {
		if ("null".equalsIgnoreCase(chk) || chk == null)
			return "";
		return chk;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the storeId
	 */
	public String getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId
	 *            the storeId to set
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the latestVersion
	 */
	public String getLatestVersion() {
		return latestVersion;
	}

	/**
	 * @param latestVersion
	 *            the latestVersion to set
	 */
	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}

	/**
	 * @return the latestVersionUrl
	 */
	public String getLatestVersionUrl() {
		return latestVersionUrl;
	}

	/**
	 * @param latestVersionUrl
	 *            the latestVersionUrl to set
	 */
	public void setLatestVersionUrl(String latestVersionUrl) {
		this.latestVersionUrl = latestVersionUrl;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getLatestContentVersion() {
		return latestContentVersion;
	}

	public void setLatestContentVersion(String latestContentVersion) {
		this.latestContentVersion = latestContentVersion;
	}

	public String getLatestContentVersionUrl() {
		return latestContentVersionUrl;
	}

	public void setLatestContentVersionUrl(String latestContentVersionUrl) {
		this.latestContentVersionUrl = latestContentVersionUrl;
	}
}