package th.co.gosoft.android.framework.lib.barcode;

import th.co.gosoft.android.framework.lib.zxing.client.android.Intents;
import android.content.Intent;

public class AFBarcodeResultManager {
	private String lastFormat;

	public String barcode2String(Intent data) {
		String contents = data.getStringExtra(Intents.Scan.RESULT);
		this.lastFormat = data.getStringExtra(Intents.Scan.RESULT_FORMAT);

		// Handle successful scan
		String bufContent = "";
		for (int i = 0; i < contents.length(); i++) {
			char at = contents.charAt(i);
			if (at < ' ') {
				at = ' ';
			}
			bufContent += at;

		}
		return bufContent;
	}

	public String getLastBarcodeFormat() {
		return lastFormat;
	}
}
