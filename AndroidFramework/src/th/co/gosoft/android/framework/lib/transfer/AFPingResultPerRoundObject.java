package th.co.gosoft.android.framework.lib.transfer;

import java.io.Serializable;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;

import hqmobileframework.common.utils.MFStringUtils;

public class AFPingResultPerRoundObject implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 223212L;
//64 bytes from 10.151.26.54: icmp_seq=2 ttl=61 time=14.5 ms
	private String rawData;
	int bytes;
	String from;
	int seq;
	int ttl;
	float timeMS;
	public static synchronized boolean isPingResultPerRoundPackets(String text)
	{
		
		if(MFStringUtils.isEmptyOrNull(text))return false;
		if(text.contains("bytes from"))return true;else return false;
	}
	public AFPingResultPerRoundObject(String source)
	{
		parse(source);
	}
	public void parse(String source)
	{
		source =getContentData(source);
		setRawData(source);
		String[] parse = source.split(" ");
//		text=>[0]64
//				text=>[1]bytes
//				text=>[2]from
//				text=>[3]10.151.26.54:
//				text=>[4]icmp_seq=2
//				text=>[5]ttl=61
//				text=>[6]time=14.5
//				text=>[7]ms
				
				setBytes(Integer.parseInt(parse[0]));
				setFrom(parse[3].replaceAll(":", ""));
				setSeq(Integer.parseInt(parse[4].split("=")[1]));
				setTtl(Integer.parseInt(parse[5].split("=")[1]));
				setTimeMS(Float.parseFloat(parse[6].split("=")[1]));
				
	}
	public String getContentData(String source)
	{
		String[]sp =source.split("\n");
		for(String s:sp)
		{
			if(isPingResultPerRoundPackets(s))return s;
		}
		return null;
	}
	public int getBytes() {
		return bytes;
	}
	public void setBytes(int bytes) {
		this.bytes = bytes;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
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

