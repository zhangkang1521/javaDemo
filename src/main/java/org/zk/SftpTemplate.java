package org.zk;

import com.jcraft.jsch.*;

import java.util.Properties;

public class SftpTemplate {

	private String username = "root";
	private String host = "10.201.1.203";
	private int port = 22;
	private String password = "admin201203";

	public void execute(SftpCallback sftpCallback) {
		ChannelSftp sftp = null;
		Session sshSession = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			sshSession = jsch.getSession(username, host, port);

			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();

			Channel channel = sshSession.openChannel("sftp");
			channel.connect();

			sftp = (ChannelSftp) channel;

			sftpCallback.doInSftp(sftp);
		} catch (JSchException e) {
			throw new RuntimeException("sftp异常", e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sftp != null)
				sftp.disconnect();
			if (sshSession != null)
				sshSession.disconnect();
		}
	}
}
