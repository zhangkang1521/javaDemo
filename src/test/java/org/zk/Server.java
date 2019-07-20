package org.zk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhangkang on 2019/6/2.
 */
public class Server {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();
        // 接受客户端消息
//        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        System.out.println(input.readLine());
        // 向客户端发送消息
        Writer writer = new OutputStreamWriter(socket.getOutputStream());
        writer.write("pong");
        writer.flush();
        writer.close();
    }
}
