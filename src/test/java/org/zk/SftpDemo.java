package org.zk;

import com.jcraft.jsch.ChannelSftp;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Vector;

public class SftpDemo {


	@Test
	public void test2() {
		SftpTemplate sftpTemplate = new SftpTemplate();
		sftpTemplate.execute(sftp -> {
			sftp.cd("/data/cgb");
			Vector<ChannelSftp.LsEntry> vector = sftp.ls("./");
			for (ChannelSftp.LsEntry entry : vector) {
				if (".".equals(entry.getFilename()) || "..".equals(entry.getFilename())) {
					continue;
				}
				if (entry.getAttrs().isDir()) {
					continue;
				}
				File file = new File("E:/tmp/cgb");
				if (!file.exists()) {
					file.mkdirs();
				}
				try (OutputStream dest = new FileOutputStream("E:/tmp/cgb/" + entry.getFilename())) {
					sftp.get("test.txt", dest);
				}
			}
		});
	}

}

