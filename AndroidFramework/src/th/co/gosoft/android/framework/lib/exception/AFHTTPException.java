package th.co.gosoft.android.framework.lib.exception;

public class AFHTTPException extends Exception {
	private String message;
	private int responseCode;
	private String responseMessage;

	public AFHTTPException(String message, int responseCode,
			String responseMessage) {
		this.message = message;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return message + " http:" + responseCode + ":" + responseMessage;
	}

}
