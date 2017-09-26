package th.co.gosoft.android.framework.lib.transfer;

import java.io.File;
import java.util.EventListener;

import th.co.gosoft.android.framework.lib.exception.AFHTTPException;

import android.graphics.Bitmap;

public interface AFDataTransferEventListener extends EventListener{
  public void onConnected(String requestCode);
  public void onUploadByteCount(String requestCode,int bytes);
  public void onDownloadProgress(String requestCode,int percent);
  public void onUploadSuccess(String requestCode);
  public void onGetFileSuccess(String requestCode,File file);
  public void onGetTextSuccess(String requestCode,String text);
  public void onGetImageSuccess(String requestCode,Bitmap bitmap);
  public void onConnectFail(String requestCode);
  public void onHttpDenied(String requestCode,AFHTTPException exception);
  public void onUploadResponse(String requestCode,String text);
}
