package th.co.gosoft.android.framework.lib.writer;

import th.co.gosoft.android.framework.lib.exception.BarcodeException;
import th.co.gosoft.android.framework.lib.image.AFImageManager;
import th.co.gosoft.android.framework.lib.zxing.BarcodeFormat;
import th.co.gosoft.android.framework.lib.zxing.MultiFormatWriter;
import th.co.gosoft.android.framework.lib.zxing.WriterException;
import th.co.gosoft.android.framework.lib.zxing.common.BitMatrix;

import android.graphics.Bitmap;



public class BarCodeWriterManager {
	private boolean showText = false;

	public Bitmap generateBarcodeNew(String text, int width, int height,
			BarcodeFormat format) throws BarcodeException, WriterException {

		MultiFormatWriter mw = new MultiFormatWriter();
		BitMatrix bMatrix = mw.encode(text, format, width, height);

		if (showText) {
			return AFImageManager.appendTextBarcode(
					AFImageManager.bitMatrix2Bitmap(bMatrix), text);
		} else {
			return AFImageManager.bitMatrix2Bitmap(bMatrix);
		}
	}

	/**
	 * @return the showText
	 */
	public boolean isShowText() {
		return showText;
	}

	/**
	 * @param showText
	 *            the showText to set
	 */
	public void setShowText(boolean showText) {
		this.showText = showText;
	}
}
