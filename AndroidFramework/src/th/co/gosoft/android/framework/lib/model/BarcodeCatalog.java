package th.co.gosoft.android.framework.lib.model;

public class BarcodeCatalog {
	public final static String TABLE = "BB_BARCODE_CATALOG";
	public final static String BARCODE_ID = "BARCODE_ID";
	public final static String CATALOG_ID = "CATALOG_ID";
	public final static String ORDERS = "ORDERS";
	public final static String SYNC_FLAG_ID = "SYNC_FLAG_ID";
	int barcodeId;
	int catalogId;
	int order;
	int syncFlagId;
	
	public int getBarcodeId() {
		return barcodeId;
	}
	public void setBarcodeId(int barcodeId) {
		this.barcodeId = barcodeId;
	}
	public int getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getSyncFlagId() {
		return syncFlagId;
	}
	public void setSyncFlagId(int syncFlagId) {
		this.syncFlagId = syncFlagId;
	}
	
}
