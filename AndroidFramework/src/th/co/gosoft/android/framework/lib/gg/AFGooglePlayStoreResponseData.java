package th.co.gosoft.android.framework.lib.gg;

import java.util.Date;
import java.util.EventListener;



public interface AFGooglePlayStoreResponseData extends EventListener{
 public void onResponse(String version,Date lastUpdate,String newFeature);
 public void onError(Throwable t);
}
