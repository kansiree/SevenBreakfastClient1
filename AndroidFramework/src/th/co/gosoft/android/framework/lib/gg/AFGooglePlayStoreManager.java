package th.co.gosoft.android.framework.lib.gg;

import hqmobileframework.common.utils.MFConverter;
import hqmobileframework.common.utils.MFDateUtils;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import th.co.gosoft.android.framework.lib.transfer.AFSimpleHTTPReader;

public class AFGooglePlayStoreManager {
	private String packageName;
	private AFGooglePlayStoreResponseData afVersionGooglePlayCheckerResponse;
	
	public static void openGooglePlay(Context context, String packageName) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_NO_HISTORY);
		intent.setData(Uri.parse("market://details?id=" + packageName));
		context.startActivity(intent);
	}
  public AFGooglePlayStoreManager(String packageNmae,AFGooglePlayStoreResponseData afVersionGooglePlayCheckerResponse)
  {
	  this.packageName = packageNmae;
	  this.afVersionGooglePlayCheckerResponse = afVersionGooglePlayCheckerResponse;
  }
  
  public void request()
  {
	  Thread t = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				
				  URL url = new URL("https://play.google.com/store/apps/details?id="+packageName);//+packageName);
				  
				        HashMap<String, String>header = new HashMap<String, String>();
				        header.put("Accept-Encoding", "gzip, deflate");
//				       
				        
					  AFSimpleHTTPReader reader = new AFSimpleHTTPReader();
					  reader.setHeader(header);
					ByteArrayOutputStream out = reader.readHTML(url,null);
					out = MFConverter.gZipBytesExtract(out.toByteArray());
					String a = new String(out.toByteArray(),"utf-8");
				     //System.out.println("Out:"+a);
				     
				     Document doc = Jsoup.parse(a);
				    String version= doc.getElementsByAttributeValue("itemprop", "softwareVersion").get(0).text();
				    String update= doc.getElementsByAttributeValue("itemprop", "datePublished").get(0).text();
				    String recent= doc.getElementsByAttributeValue("class", "recent-change").get(0).text();
					SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
					
					Date updateDate = null;
					try{
						updateDate=format.parse(update);
					}catch(Exception e)
					{
						 format = new SimpleDateFormat("dd MMMM yyyy",MFDateUtils.ThaiLocale);
						 updateDate=format.parse(update);
							
					}
				   // System.out.println("Ver:"+version+"\nupdate:"+update+"["+updateDate+"]"+"\nrecent:"+recent);
				 afVersionGooglePlayCheckerResponse.onResponse(version, updateDate, recent);
			
			}catch(Throwable t)
			{
				afVersionGooglePlayCheckerResponse.onError(t);
			}
		}
	});
	  t.start();
  }
  
}
