package th.co.gosoft.android.framework.lib.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import th.co.gosoft.android.framework.lib.zxing.common.BitMatrix;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.provider.MediaStore;

public class AFImageManager {
	private static Config IMAGE_TYPE = Config.ARGB_8888;
	private static final String[] EXT_IMG = { "png", "jpg", "jpeg" };



	public static Bitmap appendBarcodeToCard(Bitmap card, Bitmap barcode) {
		Bitmap buf = Bitmap.createBitmap(card.getWidth(),card.getHeight(),Bitmap.Config.ARGB_8888);
		Canvas comboImage = new Canvas(buf);
		//PKUtil.log("Card W:"+card.getWidth()+" H:"+card.getHeight()+" Barcode W:"+barcode.getWidth()+" H:"+barcode.getHeight());
		comboImage.drawBitmap(card, 0f,0f,null);
		comboImage.drawBitmap(barcode, (card.getWidth()-barcode.getWidth())/2, 
				(card.getHeight()-barcode.getHeight())-10, null);
		comboImage.save();
		

		return buf;
	}
	public static BitmapFactory.Options getScale(int width, int hight) {

		BitmapFactory.Options o = new BitmapFactory.Options();

		o.inSampleSize = calculateInSampleSize(o, width, hight);

		return o;
	}
	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
	public static Bitmap bitMatrix2Bitmap(BitMatrix bitMatrix) {
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		Bitmap buf = Bitmap.createBitmap(width, height, IMAGE_TYPE);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				buf.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK
						: Color.WHITE);
				// image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		Paint mPaint = new Paint();
		mPaint.setStrokeWidth(10);
		mPaint.setColor(Color.WHITE);
		Canvas c = new Canvas(buf);
		c.drawLine(0, 0, width, 0, mPaint);
		c.drawLine(0, height, width, height, mPaint);

		return buf;
	}
	
	public static Bitmap appendTextBarcode(Bitmap bitmap, String text) {
		int textSize = 50;
		int width = bitmap.getWidth();
		
		Paint mPaint = new Paint();
		mPaint.setTextSize(textSize);
		mPaint.setStrokeWidth(textSize + 10);
		
		String textBuf = "";
		int textSpace = (int) ((width) / (text.length() * textSize));
		for (int i = 0; i < text.length(); i++) {
			for (int j = 0; j < textSpace; j++) {
				textBuf += " ";
			}
			textBuf += text.charAt(i);
		}
		mPaint.setColor(Color.WHITE);
		Bitmap buf = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight()+30,IMAGE_TYPE);
		int height = buf.getHeight();
		Canvas c = new Canvas(buf);
		c.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
		int lineY = height - (textSize / 2) - 5;
		c.drawLine(0, lineY, width, lineY, mPaint);
		mPaint.setColor(Color.BLACK);
		c.drawText(textBuf, (int) (width * 0.2), height - (textSize / 3),
				mPaint);

		return buf;
	}

	public Bitmap fitBarcode(Bitmap bitmap, int BLACK_COLOR) {
		int imgWidth = bitmap.getWidth();
		int xLeft = 0;
		int xRight = 0;
		for (int x = 0; x < imgWidth; x++) {
			if (Color.red(bitmap.getPixel(x, 0)) < BLACK_COLOR) {
				xLeft = x;
				break;
			}

		}
		for (int x = imgWidth - 1; x > 0; x--) {
			if (Color.red(bitmap.getPixel(x, 0)) < BLACK_COLOR) {
				xRight = x;
				break;
			}

		}
		bitmap = Bitmap.createBitmap(bitmap, xLeft, 0, xRight - xLeft,
				bitmap.getHeight());
		return bitmap;
	}

	

	public static boolean isImageFile(String fileName) {
		for (String ext : EXT_IMG) {
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}

		return false;
	}

	public static byte[] bitmapToBytes(Bitmap bitmap, CompressFormat format) {
		int size = bitmap.getWidth() * bitmap.getHeight();
		ByteArrayOutputStream out = new ByteArrayOutputStream(size);
		bitmap.compress(format, 100, out);
		return out.toByteArray();
	}

	public static Bitmap bytesToBitmap(byte[] bytes) {
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	

	
	public Bitmap cropCenterImage(Bitmap bitmap, int height) {
		int imgHeight = bitmap.getHeight();
		int center = (int) (imgHeight / (float) 2);
		bitmap = Bitmap.createBitmap(bitmap, 0, center, bitmap.getWidth(),
				height);
		return bitmap;
	}

	public Bitmap cropImage(Bitmap bitmap, int xStart, int yStart, int xStop,
			int yStop) {
		int imgHeight = bitmap.getHeight();
		bitmap = Bitmap.createBitmap(bitmap, xStart, yStart, xStop - xStart,
				yStop - yStart);
		return bitmap;
	}

	public static Bitmap resizedBitmap(Bitmap bm, int newWidth, int newHeight) {
	
		return Bitmap.createScaledBitmap(bm,  newWidth, newHeight,  false);
	}
//	public static Bitmap resizedAndRotateBitmap_notRecommend(Bitmap bm, int degree,int newWidth, int newHeight) {
//		int width = bm.getWidth();
//		int height = bm.getHeight();
//		float scaleWidth = ((float) newWidth) / width;
//		float scaleHeight = ((float) newHeight) / height;
//		// CREATE A MATRIX FOR THE MANIPULATION
//		Matrix matrix = new Matrix();
//		// RESIZE THE BIT MAP
//		matrix.postScale(scaleWidth, scaleHeight);
//		// ROTATE
//		 matrix.postRotate(degree);
//
//		return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
//	}

	public static Bitmap whiteBlack(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, IMAGE_TYPE);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	public Bitmap createContrast(Bitmap src, double value) {
		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		// color information
		int A, R, G, B;
		int pixel;
		// get contrast value
		double contrast = Math.pow((100 + value) / 100, 2);

		// scan through all pixels
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				// apply filter contrast for every channel R, G, B
				R = Color.red(pixel);
				R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if (R < 0) {
					R = 0;
				} else if (R > 255) {
					R = 255;
				}

				G = Color.red(pixel);
				G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if (G < 0) {
					G = 0;
				} else if (G > 255) {
					G = 255;
				}

				B = Color.red(pixel);
				B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if (B < 0) {
					B = 0;
				} else if (B > 255) {
					B = 255;
				}

				// set new pixel color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}
	public static Bitmap rotate(Bitmap bitmap,int degree)
	{
		// create a matrix for the manipulation
		 Matrix matrix = new Matrix();
		 // rotate the Bitmap
		 matrix.postRotate(degree);

		 // recreate the new Bitmap
		 return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true); 
	}
	
	public static Bitmap decodeFile(File f,int WIDTH,int HIGHT) throws FileNotFoundException{
       
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //The new size we want to scale to
            final int REQUIRED_WIDTH=WIDTH;
            final int REQUIRED_HIGHT=HIGHT;
            //Find the correct scale value. It should be the power of 2.
            int scale=1;
            while(o.outWidth/scale/2>=REQUIRED_WIDTH && o.outHeight/scale/2>=REQUIRED_HIGHT)
                scale*=2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
    }
	public static Bitmap decodeResource(Resources resource,int resId,int WIDTH,int HIGHT) throws FileNotFoundException{
	       
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resource,resId,o);

        //The new size we want to scale to
        final int REQUIRED_WIDTH=WIDTH;
        final int REQUIRED_HIGHT=HIGHT;
        //Find the correct scale value. It should be the power of 2.
        int scale=1;
        while(o.outWidth/scale/2>=REQUIRED_WIDTH && o.outHeight/scale/2>=REQUIRED_HIGHT)
            scale*=2;

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize=scale;
        return BitmapFactory.decodeResource(resource,resId, o2);
}
	
	public static void saveBitmap(Bitmap bitmap,String path,CompressFormat format) throws FileNotFoundException
	{
		File file = new File(path);
		File dir = new File(file.getParent());
		if(!dir.exists()&&!dir.mkdir())dir.mkdirs();
		
		bitmap.compress(format, 100, new FileOutputStream(new File(path)));
		
	}
	public static void saveBitmapQuality(Bitmap bitmap,int quality,String path,CompressFormat format) throws FileNotFoundException
	{
		File file = new File(path);
		File dir = new File(file.getParent());
		if(!dir.exists()&&!dir.mkdir())dir.mkdirs();
		
		bitmap.compress(format, quality, new FileOutputStream(new File(path)));
		
	}
	
	public static void saveBitmapSourceAndQuality(String sourcePath,int quality,String desPath,CompressFormat format) throws FileNotFoundException
	{
		Bitmap b = BitmapFactory.decodeFile(sourcePath);
		File file = new File(desPath);
		File dir = new File(file.getParent());
		if(!dir.exists()&&!dir.mkdir())dir.mkdirs();
		
		b.compress(format, quality, new FileOutputStream(file));
		
	}
	public static void saveBitmapSourceAndQualityAndCheckRotate(String sourcePath,int quality,String desPath,int width,int height,CompressFormat format) throws IOException
	{
		ExifInterface exif = new ExifInterface(sourcePath);
		int rotate =exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
		Bitmap b = decodeFile(new File(sourcePath), width, height);
		Bitmap temp;
		switch(rotate)
		{
		case ExifInterface.ORIENTATION_ROTATE_90:
			
			temp=rotate(b, 90);
			recycleBitmap(b);
			b =temp;
			break;
		case ExifInterface.ORIENTATION_ROTATE_180:
			temp=rotate(b, 180);
			recycleBitmap(b);
			b =temp;
			break;
		case ExifInterface.ORIENTATION_ROTATE_270:
			temp=rotate(b, 270);
			recycleBitmap(b);
			b =temp;
			break;
			
		}
		
		File file = new File(desPath);
		File dir = new File(file.getParent());
		if(!dir.exists()&&!dir.mkdir())dir.mkdirs();
		
		b.compress(format, quality, new FileOutputStream(file));
		recycleBitmap(b);
		
	
		
	}
	public static void recycleBitmap(Bitmap bitmap)
	{
		if(bitmap!=null)
			{
			   bitmap.recycle();
			   bitmap=null;
			}
	}
	public static void rotateAndSave(File file,int degree,int width,int height) throws FileNotFoundException
	{
		Bitmap bitmap = decodeFile(file, width, height);
		bitmap=rotate(bitmap, degree);
		saveBitmap(bitmap, file.getAbsolutePath(), CompressFormat.PNG);
		
	}
	public static int getExifOrientation(String imgPath) throws IOException
	{
		ExifInterface exif = new ExifInterface(imgPath);
		return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
	}
	public static int getOrientation(Context context,int id) {
		String[] mProjection = { MediaStore.Images.Media.ORIENTATION };
		Cursor c = MediaStore.Images.Media.query(context.getContentResolver(),
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mProjection,
				MediaStore.Images.Media._ID + "=" + id,// where,
				MediaStore.Images.Media._ID);// order);
		if (c.moveToFirst()) {
			int result = c.getInt(0);
			c.close();
			return result;
		}
		c.close();
		return 0;
	}
}
