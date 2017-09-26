package th.co.gosoft.android.framework.lib.implement;

import com.google.gson.Gson;

public abstract class AFJsonResponseObject {
	protected AFJsonResponseObject()
	{
		
	}
  protected static Object parseJsonString(String jsonString,Class c)
  {
	  final Gson gson = new Gson();
	  return gson.fromJson(jsonString, c);
  }
}
