package th.co.gosoft.android.framework.lib.exception;

public class AFNotSupportException extends Exception {

	String message;

	public AFNotSupportException(String msg) {
		message = msg;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return message;
	}

	private static final long serialVersionUID = 711L;

}
