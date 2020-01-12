package org.zk;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleHttpServer {

	private static AtomicLong count = new AtomicLong(0);

	public static void main(String[] args) throws Exception{
		ThreadPool threadPool = new SimpleThreadPool(1);
		ServerSocket ss = new ServerSocket(80);
		Socket socket;
		while((socket = ss.accept()) != null) {
			System.out.println("收到请求" + count.incrementAndGet());
			threadPool.execute(new RequestHandle(socket));
		}
	}

	static class RequestHandle implements Runnable {

		private Socket socket;

		public RequestHandle(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			     PrintWriter out = new PrintWriter(socket.getOutputStream())){
				// GET /test.html HTTP/1.1
				String firstLine = br.readLine();
				String uri = firstLine.split(" ")[1];
				out.println("HTTP/1.1 200 OK");
				out.println("Server: simpleHttpServer");
				if (uri.endsWith("jpg") || uri.endsWith("ico")) {
					out.println("Content-Type: img/jpeg");
					InputStream jpgInput = RequestHandle.class.getClassLoader().getResourceAsStream(uri.substring(1));
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int length;
					while ((length = jpgInput.read(buffer)) != -1) {
						baos.write(buffer, 0, length);
					}
					byte[] bytes = baos.toByteArray();
					out.print("Content-length: " + bytes.length);
					socket.getOutputStream().write(bytes);
					jpgInput.close();
				} else {
					out.println("Content-Type: text/html; charset=UTF-8");
					out.println();
					InputStream htmlInput = RequestHandle.class.getClassLoader().getResourceAsStream(uri.substring(1));
					BufferedReader htmlBr = new BufferedReader(new InputStreamReader(htmlInput));
					String line;
					while ((line = htmlBr.readLine()) != null) {
						out.println(line);
					}
					htmlBr.close();
				}
				out.println();
				out.flush();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
