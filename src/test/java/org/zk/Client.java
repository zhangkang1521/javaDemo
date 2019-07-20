package org.zk;

import java.io.*;
import java.net.Socket;

/**
 * Created by zhangkang on 2019/6/2.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8080);
        // 向服务器发送消息
        Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("hello");
        writer.flush();
        writer.close();
        // 接收服务器信息
//        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        System.out.println(reader.readLine());
    }
}
