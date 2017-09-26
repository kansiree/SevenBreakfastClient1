package th.co.gosoft.android.framework.lib.model;

public class Catalog {
	public final static String TABLE = "BB_CATALOG";
	public final static String _ID = "_id";
	public final static String NAME = "NAME";
	public final static String IMAGE_ID = "IMAGE_ID";
	public final static String SYNC_FLAG_ID = "SYNC_FLAG_ID";
	public final static String DESCRIPTION = "DESCRIPTION";

	int id;
	String name;
	int imageId;
	int syncFlagId;
	String desc;

	public int getId() {
		return id;
	}

	public void setId(int catalogId) {
		this.id = catalogId;
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

	public String toString() {
		return this.getName();
	}

}
