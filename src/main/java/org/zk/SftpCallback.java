package org.zk;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;

public interface SftpCallback {

	public void doInSftp(ChannelSftp channelSftp) throws Exception;
}
