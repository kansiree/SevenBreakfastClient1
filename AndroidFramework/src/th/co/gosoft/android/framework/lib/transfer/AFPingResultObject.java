package th.co.gosoft.android.framework.lib.transfer;

import java.io.Serializable;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;

import hqmobileframework.common.utils.MFStringUtils;

public class AFPingResultObject implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 12123L;
	//10 packets transmitted, 10 received, 0% packet loss, time 9013ms
	private String rawData;
	int packetsTransmitted;
	int packetsReceived;
	int packetLossPercent;
	float timeMS;
	public AFPingResultObject(String source)
	{
		parse(source);
	}
	public static boolean isPingResultPackets(String text)
	{
		if(MFStringUtils.isEmptyOrNull(text))return false;
		if(text.contains("transmitted"))return true;else return false;
	}
	public void parse(String data)
	{
		data = getContentData(data);
		setRawData(data);
		String[]parse =data.split(" ");
//		text=>[0]10
//				text=>[1]packets
//				text=>[2]transmitted,
//				text=>[3]10
//				text=>[4]received,
//				text=>[5]0%
//				text=>[6]packet
//				text=>[7]loss,
//				text=>[8]time
//				text=>[9]9013ms
		setPacketsTransmitted(Integer.parseInt(parse[0]));
		setPacketsReceived(Integer.parseInt(parse[3]));
		setPacketLossPercent(Integer.parseInt(parse[5].replaceAll("%", "")));
		setTimeMS(Float.parseFloat(parse[9].replaceAll("ms", "")));
	}
	public String getContentData(String source)
	{
		String[]sp =source.split("\n");
		for(String s:sp)
		{
			if(isPingResultPackets(s))return s;
		}
		return null;
	}
	public int getPacketsTransmitted() {
		return packetsTransmitted;
	}
	public void setPacketsTransmitted(int packetsTransmitted) {
		this.packetsTransmitted = packetsTransmitted;
	}
	public int getPacketsReceived() {
		return packetsReceived;
	}
	public void setPacketsReceived(int packetsReceived) {
		this.packetsReceived = packetsReceived;
	}
	public int getPacketLossPercent() {
		return packetLossPercent;
	}
	public void setPacketLossPercent(int packetLoss) {
		this.packetLossPercent = packetLoss;
	}
	public float getTimeMS() {
		return timeMS;
	}
	public void setTimeMS(float timeMS) {
		this.timeMS = timeMS;
	}
	public String getRawData() {
		return rawData;
	}
	public void setRawData(String rawData) {
		this.rawData = rawData;
	}
}
