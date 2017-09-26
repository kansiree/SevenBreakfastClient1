/*
 * Copyright 2010 ZXing authors
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

package th.co.gosoft.android.framework.lib.zxing.aztec;


import java.util.List;
import java.util.Map;

import th.co.gosoft.android.framework.lib.zxing.BarcodeFormat;
import th.co.gosoft.android.framework.lib.zxing.BinaryBitmap;
import th.co.gosoft.android.framework.lib.zxing.ChecksumException;
import th.co.gosoft.android.framework.lib.zxing.DecodeHintType;
import th.co.gosoft.android.framework.lib.zxing.FormatException;
import th.co.gosoft.android.framework.lib.zxing.NotFoundException;
import th.co.gosoft.android.framework.lib.zxing.Reader;
import th.co.gosoft.android.framework.lib.zxing.Result;
import th.co.gosoft.android.framework.lib.zxing.ResultMetadataType;
import th.co.gosoft.android.framework.lib.zxing.ResultPoint;
import th.co.gosoft.android.framework.lib.zxing.ResultPointCallback;
import th.co.gosoft.android.framework.lib.zxing.aztec.decoder.Decoder;
import th.co.gosoft.android.framework.lib.zxing.aztec.detector.Detector;
import th.co.gosoft.android.framework.lib.zxing.common.DecoderResult;


/**
 * This implementation can detect and decode Aztec codes in an image.
 *
 * @author David Olivier
 */
public final class AztecReader implements Reader {

  /**
   * Locates and decodes a Data Matrix code in an image.
   *
   * @return a String representing the content encoded by the Data Matrix code
   * @throws NotFoundException if a Data Matrix code cannot be found
   * @throws FormatException if a Data Matrix code cannot be decoded
   * @throws ChecksumException if error correction fails
   */
  public Result decode(BinaryBitmap image) throws NotFoundException, FormatException {
    return decode(image, null);
  }

  public Result decode(BinaryBitmap image, Map<DecodeHintType,?> hints)
      throws NotFoundException, FormatException {

    AztecDetectorResult detectorResult = new Detector(image.getBlackMatrix()).detect();
    ResultPoint[] points = detectorResult.getPoints();

    if (hints != null) {
      ResultPointCallback rpcb = (ResultPointCallback) hints.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
      if (rpcb != null) {
        for (ResultPoint point : points) {
          rpcb.foundPossibleResultPoint(point);
        }
      }
    }

    DecoderResult decoderResult = new Decoder().decode(detectorResult);

    Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), points, BarcodeFormat.AZTEC);
    
    List<byte[]> byteSegments = decoderResult.getByteSegments();
    if (byteSegments != null) {
      result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
    }
    String ecLevel = decoderResult.getECLevel();
    if (ecLevel != null) {
      result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
    }
    
    return result;
  }

  public void reset() {
    // do nothing
  }

}