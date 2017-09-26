/*
 * Copyright 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package th.co.gosoft.android.framework.lib.zxing;


import java.util.Map;

import th.co.gosoft.android.framework.lib.zxing.common.BitMatrix;
import th.co.gosoft.android.framework.lib.zxing.oned.CodaBarWriter;
import th.co.gosoft.android.framework.lib.zxing.oned.Code128Writer;
import th.co.gosoft.android.framework.lib.zxing.oned.Code39Writer;
import th.co.gosoft.android.framework.lib.zxing.oned.EAN13Writer;
import th.co.gosoft.android.framework.lib.zxing.oned.EAN8Writer;
import th.co.gosoft.android.framework.lib.zxing.oned.ITFWriter;
import th.co.gosoft.android.framework.lib.zxing.oned.UPCAWriter;
import th.co.gosoft.android.framework.lib.zxing.pdf417.encoder.PDF417Writer;
import th.co.gosoft.android.framework.lib.zxing.qrcode.QRCodeWriter;


/**
 * This is a factory class which finds the appropriate Writer subclass for the BarcodeFormat
 * requested and encodes the barcode with the supplied contents.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class MultiFormatWriter implements Writer {

  public BitMatrix encode(String contents,
                          BarcodeFormat format,
                          int width,
                          int height) throws WriterException {
    return encode(contents, format, width, height, null);
  }

  public BitMatrix encode(String contents,
                          BarcodeFormat format,
                          int width, int height,
                          Map<EncodeHintType,?> hints) throws WriterException {

    Writer writer;
    switch (format) {
      case EAN_8:
        writer = new EAN8Writer();
        break;
      case EAN_13:
        writer = new EAN13Writer();
        break;
      case UPC_A:
        writer = new UPCAWriter();
        break;
      case QR_CODE:
        writer = new QRCodeWriter();
        break;
      case CODE_39:
        writer = new Code39Writer();
        break;
      case CODE_128:
        writer = new Code128Writer();
        break;
      case ITF:
        writer = new ITFWriter();
        break;
      case PDF_417:
        writer = new PDF417Writer();
        break;
      case CODABAR:
	      writer = new CodaBarWriter();
        break;
      default:
        throw new IllegalArgumentException("No encoder available for format " + format);
    }
    return writer.encode(contents, format, width, height, hints);
  }

}