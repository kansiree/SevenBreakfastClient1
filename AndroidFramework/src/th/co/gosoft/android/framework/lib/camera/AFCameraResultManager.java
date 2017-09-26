package th.co.gosoft.android.framework.lib.camera;

import th.co.gosoft.android.framework.lib.image.AFImageManager;
import th.co.gosoft.android.framework.lib.utils.AFPhoneUtils;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.util.Base64;

public class AFCameraResultManager {
	public String camera2Base64Image_resize(Intent data, int imageWidh,
			int imageHeight) {

		// TODO Auto-generated method stub
		return Base64.encodeToString(AFImageManager.bitmapToBytes(
				AFImageManager.resizedBitmap(
						(Bitmap) data.getExtras().get("data"), imageWidh,
						imageHeight), CompressFormat.PNG), Base64.DEFAULT);

	}

	public String gallery2Base64Image_resize(Context context, Intent data,
			int imageWidth, int imageHeight) {

		Uri selectedImageUri = data.getData();
		Bitmap bitmap = BitmapFactory.decodeFile(AFPhoneUtils.getMediaPath(
				selectedImageUri, context));
		// TODO Auto-generated method stub
		return Base64.encodeToString(AFImageManager.bitmapToBytes(
				AFImageManager.rotate(AFImageManager.resizedBitmap(bitmap,
						imageWidth, imageHeight), 90), CompressFormat.PNG),
				Base64.DEFAULT);

	}

	public Bitmap camera2Bitmap_resize(Intent data, int imageWidh,
			int imageHeight) {

		// TODO Auto-generated method stub
		return AFImageManager.resizedBitmap(
				(Bitmap) data.getExtras().get("data"), imageWidh, imageHeight);

	}

	public Bitmap gallery2Bitmap_resize(Context context, Intent data,
			int imageWidth, int imageHeight) {

		Uri selectedImageUri = data.getData();
		Bitmap bitmap = BitmapFactory.decodeFile(AFPhoneUtils.getMediaPath(
				selectedImageUri, context));
		// TODO Auto-generated method stub
		return AFImageManager.rotate(
				AFImageManager.resizedBitmap(bitmap, imageWidth, imageHeight),
				90);

	}

	public Bitmap camera2Bitmap_raw(Intent data, int imageWidh, int imageHeight) {
		// TODO Auto-generated method stub
		return (Bitmap) data.getExtras().get("data");

	}

	public Bitmap gallery2Bitmap_raw(Context context, Intent data,
			int imageWidth, int imageHeight) {

		Uri selectedImageUri = data.getData();
		return BitmapFactory.decodeFile(AFPhoneUtils.getMediaPath(
				selectedImageUri, context));

	}
}
