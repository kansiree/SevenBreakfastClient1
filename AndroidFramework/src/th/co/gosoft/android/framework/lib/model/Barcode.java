package th.co.gosoft.android.framework.lib.model;

import android.graphics.Bitmap;

public class Barcode {
	public final static String TABLE = "BB_BARCODE";
	public final static String _ID = "_id";
	public final static String CONTENT = "CONTENT";
	public final static String TYPE_ID = "TYPE_ID";
	public final static String NAME = "NAME";
	public final static String FORMAT = "FORMAT";
	public final static String IMAGE_ID = "IMAGE_ID";
	public final static String SYNC_FLAG_ID = "SYNC_FLAG_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	private long id;
	private String content;
	private int typeId;
	private String name;
	private String format;
	private int imageId;
	private int syncFlagId;
	private String desc;
	private Bitmap typeImage;

	public long getId() {
		return id;
	}

	public void setId(long barcodeId) {
		this.id = barcodeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public int getSyncFlagId() {
		return syncFlagId;
	}

	public void setSyncFlagId(int syncFlagId) {
		this.syncFlagId = syncFlagId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the typeImage
	 */
	public Bitmap getTypeImage() {
		return typeImage;
	}

	/**
	 * @param typeImage the typeImage to set
	 */
	public void setTypeImage(Bitmap typeImage) {
		this.typeImage = typeImage;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
