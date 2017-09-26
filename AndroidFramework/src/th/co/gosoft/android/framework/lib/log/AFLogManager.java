package th.co.gosoft.android.framework.lib.log;

import hqmobileframework.common.utils.MFDateUtils;
import hqmobileframework.common.utils.MFFileUtils;
import hqmobileframework.common.utils.MFZipUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.view.KeyEvent;

public class AFLogManager {
	private static final String LOG_DEBUG = "logcat -d";
	

	
	private static long lastTime=0;
	private static int difTime=800;
	    public static boolean onKeyDown(int keyCode, KeyEvent event,
	    		Activity activity,
	    		String logpath,
	    		String[] mailto,
	    		String mailSubject) {
	        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
	            //Do something
	        	lastTime = MFDateUtils.getCurrentDateMillis();
	        }else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
	            //Do something
	        	if(MFDateUtils.getCurrentDateMillis()-lastTime<=difTime)
	        	{
	        		doLog(activity,logpath,mailto,mailSubject);
	        		return true;
	        	}
	        	
	        }
	        
	        return false;
	    }
	    public static boolean onKeyDown(int keyCode, int firstKey,int lastKey,
	    		Activity activity,
	    		String logpath,
	    		String[] mailto,
	    		String mailSubject) {
	        if ((keyCode == firstKey)){
	            //Do something
	        	lastTime = MFDateUtils.getCurrentDateMillis();
	        }else if ((keyCode == lastKey)){
	            //Do something
	        	if(MFDateUtils.getCurrentDateMillis()-lastTime<=difTime)
	        	{
	        		doLog(activity,logpath,mailto,mailSubject);
	        		return true;
	        	}
	        	
	        }
	        
	        return false;
	    }

	private static void doLog(Activity activity,String logpath,String[]mailTo,String mailSubject)
	{
		try{
    		File log=AFLogManager.getLogfile(logpath);
    		//send email//
    		PackageInfo pInfo = activity.getPackageManager().getPackageInfo(
					activity.getPackageName(), 0);
    		String os=String.valueOf(android.os.Build.VERSION.RELEASE);
    		String device=android.os.Build.BRAND+":"+android.os.Build.MODEL;
    		sendEmail(activity, String.valueOf(pInfo.versionCode),pInfo.versionName,os,device, log,mailTo,mailSubject);
    		}catch(Exception e)
    		{
    			AFLogUtils.e(e.toString(),e);
    		}
	}
	public static File getLogfile(String logpath) throws FileNotFoundException, IOException
	{
		File log = new File(logpath+MFDateUtils.getUniquename()+".txt");
		MFFileUtils.makeDirForFile(log);
		MFFileUtils.writeFile(new FileOutputStream(log), new ByteArrayInputStream(getLogCatDetails().getBytes()));
		
		//zip//
		log=MFZipUtil.zip(log, true);
		
		return log;
	}
	
	private static String getLogCatDetails() {
		StringBuilder log = null;
		try {
			Process process = Runtime.getRuntime().exec(LOG_DEBUG);

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			 log = new StringBuilder();

			String line = "";

			while ((line = bufferedReader.readLine()) != null) {
				if(line.indexOf("TextLayoutCache") != -1){ // Skip tag "TextLayoutCache"
					continue;
				}else if(line.indexOf("GC_CONCURRENT")!=-1)
				{
					continue;
				}
			
				log.append( line + "\n");
			}
			
			

		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		}

		return log.toString();
	}
	public static void sendEmail(Activity activity,
			String versionCode,
			String versionName,
			String os,
			String device,
			File attacheFile,
			String[]mailto,
			String mailSubject)
	{
		
		
		
		String message = "Version code : " + versionCode + "\n" +
				"Version name : " + versionName + "\n" +
				"OS : " + os + "\n" +
				"Device : " + device + "\n" + 
				"Message : ...";
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("message/rfc822");//"text/plain");
	//	emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
		emailIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT,mailSubject);
		emailIntent.putExtra(Intent.EXTRA_TEXT, message);
		
		if (attacheFile == null || !attacheFile.exists() || !attacheFile.canRead()) {
			activity.startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
		    return;
		}
		else{
			Uri uri = Uri.fromFile(attacheFile);
			emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
			activity.startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
		}
	}
}
