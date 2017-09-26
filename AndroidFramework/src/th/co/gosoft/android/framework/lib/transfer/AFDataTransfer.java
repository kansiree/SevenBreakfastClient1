package th.co.gosoft.android.framework.lib.transfer;

import hqmobileframework.common.security.MFSecurityController;
import hqmobileframework.common.security.MFStringEncrypterDecrypter;
import hqmobileframework.common.utils.MFFileUtils;
import hqmobileframework.common.utils.MFObjectUtils;
import hqmobileframework.hq.http.MFDataManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import th.co.gosoft.android.framework.lib.exception.AFHTTPException;
import th.co.gosoft.android.framework.lib.image.AFImageManager;
import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public class AFDataTransfer extends Thread implements AFHttpReaderEventListener {
	private AFDataTransferProperties mDataProperties;
	private AFDataTransferEventListener afDataTransferEventListener;
	InputStream mInputStream;
	private int ACTION = -1;
	private String requestCode;
	HashMap<String, String> header;
	final private int ACTION_SEND_TEXT = 1;
	final private int ACTION_SEND_IMAGE = 2;
	final private int ACTION_SEND_FILE = 3;
	final private int ACTION_GET_TEXT = 4;
	final private int ACTION_GET_IMAGE = 5;
	final private int ACTION_GET_FILE = 6;
	
	private int connectionTimeOut = 120*1000; //2 min;
	private int readTimeOut = 300*1000;//sec 5 min

	public AFDataTransfer(AFDataTransferProperties afDataTransferProperties) {
		setmDataProperties(afDataTransferProperties);
	}

	public void sendTextData(String text) throws IOException, Exception {
		header = new HashMap<String, String>();
		header.put(MFDataManager.ARG0, this.mDataProperties.getArg0());
		this.mInputStream = new ByteArrayInputStream(
				text.getBytes(getmDataProperties().getCharSet()));
		ACTION = ACTION_SEND_TEXT;
//		inputStream2File(mDataProperties.getUrl(), new ByteArrayInputStream(
//				text.getBytes(getmDataProperties().getCharSet())));
	}

	public void sendTextData(byte[] bytes) throws IOException, Exception {
		header = new HashMap<String, String>();
		header.put(MFDataManager.ARG0, this.mDataProperties.getArg0());
		this.mInputStream = new ByteArrayInputStream(bytes);
		ACTION = ACTION_SEND_TEXT;
//		inputStream2File(mDataProperties.getUrl(), new ByteArrayInputStream(
//				bytes));
	}

	public void sendImage(Bitmap bitmap, CompressFormat format)
			throws IOException, Exception {
		header = new HashMap<String, String>();
		header.put(MFDataManager.ARG0, this.mDataProperties.getArg0());
		this.mInputStream = new ByteArrayInputStream(
				AFImageManager.bitmapToBytes(bitmap, format));
		ACTION = ACTION_SEND_IMAGE;
//		inputStream2File(mDataProperties.getUrl(), new ByteArrayInputStream(
//				AFImageManager.bitmapToBytes(bitmap, format)));
	}

	public void sendFile(File file) throws FileNotFoundException, IOException,
			Exception {
		this.mInputStream = new FileInputStream(file);
		header = new HashMap<String, String>();
		header.put(MFDataManager.FILENAME_HEADER, file.getName());
		header.put(MFDataManager.FILESIZE_HEADER, String.valueOf(file.length()));
		header.put(MFDataManager.ARG0, this.mDataProperties.getArg0());
		ACTION = ACTION_SEND_FILE;
//		inputStream2File(mDataProperties.getUrl(), new FileInputStream(file));
	}

	public void receiveTextData(String query) throws IOException, Exception {
		query = checkEncrypt(query);
		header = new HashMap<String, String>();
		header.put(MFDataManager.ARG0, this.mDataProperties.getArg0());
		this.mInputStream = new ByteArrayInputStream(
				query.getBytes(getmDataProperties().getCharSet()));
		ACTION = ACTION_GET_TEXT;
//		inputStream2File(mDataProperties.getUrl(), new ByteArrayInputStream(
//				query.getBytes(getmDataProperties().getCharSet())));
	}

	public void receiveImage(String query) throws IOException, Exception {
		query = checkEncrypt(query);
		header = new HashMap<String, String>();
		header.put(MFDataManager.ARG0, this.mDataProperties.getArg0());
		this.mInputStream = new ByteArrayInputStream(
				query.getBytes(getmDataProperties().getCharSet()));
		ACTION = ACTION_GET_IMAGE;
//		inputStream2File(mDataProperties.getUrl(), new ByteArrayInputStream(
//				query.getBytes(getmDataProperties().getCharSet())));
	}

	public void receiveFile(String query) throws FileNotFoundException,
			IOException, Exception {
		query = checkEncrypt(query);
		header = new HashMap<String, String>();
		header.put(MFDataManager.ARG0, this.mDataProperties.getArg0());
		this.mInputStream = new ByteArrayInputStream(
				query.getBytes(getmDataProperties().getCharSet()));
		ACTION = ACTION_GET_FILE;
//		inputStream2File(mDataProperties.getUrl(), new ByteArrayInputStream(
//				query.getBytes(getmDataProperties().getCharSet())));
	}

	private void inputStream2File(URL url, InputStream inputStream) {
		try {
			AFLogUtils.i("path:"+url.getPath());
			String[] temp = url.getPath().split("\\/");
			String fileName = temp[temp.length - 1];
			File f = new File(Environment.getExternalStorageDirectory()
					+ "/Bill/Data/" + fileName);
			AFLogUtils.i("Write 2 file:"+fileName);
			MFFileUtils.writeFile(new FileOutputStream(f), inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String checkEncrypt(String query) {
		if (MFSecurityController.getInstatnce().isEnbleEncrypt()) {
			query = MFStringEncrypterDecrypter.encrypt(query);
		}
		return query;
	}

	AFHttpReader reader;

	@Override
	public void run() {
		try {
			reader = new AFHttpReader();
			reader.setCustomHeader(header);
			reader.setMethod(AFHttpReader.METHOD_POST);
			reader.setAfHttpReaderEventListener(this);
			reader.setConnectionTimeOut(getConnectionTimeOut());
			reader.setReadTimeOut(getReadTimeOut());
			reader.readHTML(getmDataProperties().getUrl(), this.mInputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the mDataProperties
	 */
	public AFDataTransferProperties getmDataProperties() {
		return mDataProperties;
	}

	/**
	 * @param mDataProperties
	 *            the mDataProperties to set
	 */
	public void setmDataProperties(AFDataTransferProperties mDataProperties) {
		this.mDataProperties = mDataProperties;
	}

	public void onConnected() {
		// TODO Auto-generated method stub
		if (!MFObjectUtils.isNull(getAfDataTransferEventListener())) {
			getAfDataTransferEventListener().onConnected(getRequestCode());
		}

	}

	public void onConnectFail() {
		// TODO Auto-generated method stub
		if (!MFObjectUtils.isNull(getAfDataTransferEventListener())) {
			getAfDataTransferEventListener().onConnectFail(getRequestCode());
		}
	}

	File fileOut;
	ByteArrayOutputStream out;

	public void onHttpAccepted(Map<String, List<String>> headerField) {
		// TODO Auto-generated method stub
		try {

			AFLogUtils.i("onHttpAccepted headerField 1 : "+headerField);
			AFLogUtils.i("MFDataManager.ARG0 : "+MFDataManager.ARG0);
			AFLogUtils.i("MFDataManager.ARG0 : "+headerField.get(MFDataManager.ARG0));
			
			this.mDataProperties.setArg0(headerField.get(MFDataManager.ARG0)
					.get(0));
			if (ACTION == ACTION_GET_FILE) {
				String fileNameDownload = headerField.get(
						MFDataManager.FILENAME_HEADER).get(0);
				fileOut = new File(getmDataProperties().getTempFilePath()
						+ fileNameDownload);
				reader.setOutputStream(new FileOutputStream(fileOut));
			} else {
				out = new ByteArrayOutputStream();
				reader.setOutputStream(out);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onHttpDenied(AFHTTPException exception) {
		// TODO Auto-generated method stub
		if (!MFObjectUtils.isNull(getAfDataTransferEventListener())) {
			getAfDataTransferEventListener().onHttpDenied(getRequestCode(),exception);
		}

	}

	public void onUploadCount(int bytes) {
		// TODO Auto-generated method stub
		if (!MFObjectUtils.isNull(getAfDataTransferEventListener())) {
			getAfDataTransferEventListener().onUploadByteCount(getRequestCode(),bytes);
		}

	}

	public void onUploadSuccess() {
		// TODO Auto-generated method stub
		if (!MFObjectUtils.isNull(getAfDataTransferEventListener())) {
			getAfDataTransferEventListener().onUploadSuccess(getRequestCode());
		}
	}

	public void onDownloadProgress(int percent) {
		// TODO Auto-generated method stub
		if (!MFObjectUtils.isNull(getAfDataTransferEventListener())) {
			getAfDataTransferEventListener().onDownloadProgress(getRequestCode(),percent);
		}

	}

	public void onDownloadSuccess() {
		// TODO Auto-generated method stub
		try {
			if (MFObjectUtils.isNull(getAfDataTransferEventListener()))
				return;
			boolean enableEncrypt = MFSecurityController.getInstatnce()
					.isEnbleEncrypt();
			switch (ACTION) {
			case ACTION_GET_TEXT:
				getAfDataTransferEventListener().onGetTextSuccess(getRequestCode(),
						enableEncrypt ? MFStringEncrypterDecrypter
								.decrypt(new String(out.toByteArray(),
										getmDataProperties().getCharSet()))
								: new String(out.toByteArray(),
										getmDataProperties().getCharSet()));
				break;
			case ACTION_GET_IMAGE:
				getAfDataTransferEventListener().onGetImageSuccess(getRequestCode(),
						AFImageManager.bytesToBitmap(out.toByteArray()));
				break;
			case ACTION_GET_FILE:
				getAfDataTransferEventListener().onGetFileSuccess(getRequestCode(),fileOut);
				break;
			default:
				getAfDataTransferEventListener().onUploadResponse(getRequestCode(),
						enableEncrypt ? MFStringEncrypterDecrypter
								.decrypt(new String(out.toByteArray(),
										getmDataProperties().getCharSet()))
								: new String(out.toByteArray(),
										getmDataProperties().getCharSet()));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return the afDataTransferEventListener
	 */
	public AFDataTransferEventListener getAfDataTransferEventListener() {
		return afDataTransferEventListener;
	}

	/**
	 * @param afDataTransferEventListener
	 *            the afDataTransferEventListener to set
	 */
	public void setAfDataTransferEventListener(
			AFDataTransferEventListener afDataTransferEventListener) {
		this.afDataTransferEventListener = afDataTransferEventListener;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public int getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

}
