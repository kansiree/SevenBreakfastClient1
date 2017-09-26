package th.co.gosoft.android.framework.lib.transfer;

import hqmobileframework.common.utils.MFObjectUtils;
import hqmobileframework.common.utils.MFStringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;

public class AFPing extends Thread {
	String ipAddress = "127.0.0.1";
	int round = 1;
	private AFPingListener afPingListener;
	public AFPing(String ipaddress, int round) {
		this.ipAddress = ipaddress;
		this.round = round;
	}

	@Override
	public void run() {
		if (MFStringUtils.isEmpty(this.ipAddress))
			return;

		Process mProcess;
		OutputStream mOut = System.out;
		try {
			mProcess = new ProcessBuilder("sh").redirectErrorStream(true)
					.start();

			try {
				InputStream in = mProcess.getInputStream();
				OutputStream out = mProcess.getOutputStream();
				byte[] buffer = new byte[1024];
				int count;
				byte[] command = ("ping -c " + this.round + " "
						+ this.ipAddress + "\n").getBytes();
				out.write(command, 0, command.length);
				out.flush();
				// in -> buffer -> mPOut -> mReader -> 1 line of ping
				// information to parse
				while ((count = in.read(buffer)) != -1) {
					// mOut.write(buffer, 0, count);
					final String line = new String(buffer, 0, count);
					AFLogUtils.i("ping:"+line);
					if (AFPingResultPerRoundObject.isPingResultPerRoundPackets(line)) {
						if(!MFObjectUtils.isNull(getAfPingListener()))
						{
							if(!MFObjectUtils.isNull(getAfPingListener()))getAfPingListener().onPingResultPerRound( new AFPingResultPerRoundObject(line));
						}
					}
					
					if (AFPingResultObject.isPingResultPackets(line)) {
						
						if(!MFObjectUtils.isNull(getAfPingListener()))
						{
							if(!MFObjectUtils.isNull(getAfPingListener()))getAfPingListener().onPingResult(new AFPingResultObject(line));
						
						}
						break;

					}
					if(!AFPingResultPerRoundObject.isPingResultPerRoundPackets(line) &&!AFPingResultObject.isPingResultPackets(line))
					{
						if(!MFObjectUtils.isNull(getAfPingListener()))getAfPingListener().onPingFail(line);
					}
				}
				out.close();
				in.close();
				mOut.close();
			} finally {
				mProcess.destroy();
				mProcess = null;
			}
		} catch (IOException e) {
			AFLogUtils.e(e.toString(), e);
		}
	}

	
	public AFPingListener getAfPingListener() {
		return afPingListener;
	}

	public void setAfPingListener(AFPingListener afPingListener) {
		this.afPingListener = afPingListener;
	}
}
