package th.co.gosoft.android.framework.lib.transfer;

import java.io.ByteArrayOutputStream;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import th.co.gosoft.android.framework.lib.exception.AFHTTPException;

public interface AFHttpReaderEventListener extends EventListener {
	public void onConnected();

	public void onConnectFail();
	
	public void onHttpAccepted(Map<String, List<String>>headerField);
	
	public void onHttpDenied(AFHTTPException exception);

	public void onUploadCount(int bytes);
	
	public void onUploadSuccess();

	public void onDownloadProgress(int percent);

	public void onDownloadSuccess();
}
