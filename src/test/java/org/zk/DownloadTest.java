package org.zk;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DownloadTest {

	@Test
	public void test1() throws Exception {
		List<Pair> list = readFile();
		list.forEach(pair -> {
			try {
				URL url = new URL("http://super.lvmama.com/sas-service/v1/file/downLoadFileAndDistinguishingTypes?fileId=" + pair.getFileId());
				URLConnection urlConnection = url.openConnection();
				urlConnection.setConnectTimeout(3 * 1000);
				urlConnection.connect();
				InputStream inputStream =  urlConnection.getInputStream();
				IOUtils.copy(inputStream, new FileOutputStream(new File("E:/invoice/" + pair.getFileName())));
			} catch (IOException e) {
				System.out.println("下载失败：" + pair.getFileId());
				e.printStackTrace();
			}
		});

	}

	@Test
	public void test2() throws Exception {
		List<Pair> list = readFile();
	}

	private List<Pair> readFile() throws Exception {
		List<Pair> list = new ArrayList<>(317);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("E:/invoice2019.txt")));
		String line = null;
		while ( (line = bufferedReader.readLine()) != null) {
			int index = line.indexOf("\t");
			list.add(new Pair(Long.valueOf(line.substring(0, index)), line.substring(index + 1)));
		}
		bufferedReader.close();
		return list;
	}

	static class Pair {
		private Long fileId;
		private String fileName;

		public Pair(Long fileId, String fileName) {
			this.fileId = fileId;
			this.fileName = fileName;
		}

		public Long getFileId() {
			return fileId;
		}

		public void setFileId(Long fileId) {
			this.fileId = fileId;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
	}
}
