package th.co.gosoft.android.framework.lib.transfer;

public interface AFPingListener {
  public void onPingResultPerRound(AFPingResultPerRoundObject pingObject);
  public void onPingResult(AFPingResultObject pingObject);
  public void onPingFail(String fail);
}
