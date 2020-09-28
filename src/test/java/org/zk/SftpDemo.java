package org.zk;

import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SftpDemo {


	@Test
	public void test2() {
		SftpTemplate sftpTemplate = new SftpTemplate();
		List<File> tmpFiles = new ArrayList<>();
		sftpTemplate.execute(sftp -> {
			sftp.cd("/data/cgb/20200926");
			Vector<ChannelSftp.LsEntry> vector = sftp.ls("./");
			for (ChannelSftp.LsEntry entry : vector) {
				if (entry.getAttrs().isDir()) {
					continue;
				}
				File tmpFile = new File("E:/tmp/cgb/" + entry.getFilename());

				try (OutputStream dest = new FileOutputStream(tmpFile)) {
					sftp.get(entry.getFilename(), dest);
				}
				tmpFiles.add(tmpFile);
			}
		});
		for (File tmpFile : tmpFiles) {
			boolean result = tmpFile.delete();
			System.out.println(result);
		}
	}

	@Test
	public void test3() throws Exception {
		System.out.println(System.getProperty("java.io.tmpdir"));
	}

}

