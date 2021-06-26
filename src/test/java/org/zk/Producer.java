package org.zk;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Producer {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		// 采用工厂模式
		ConnectionFactory factory = new ConnectionFactory();
		// 设置主机，因是本地，故不用配置其他属性。
		factory.setHost("localhost");
		// try-with-resources语句：括号里进行嵌套字连接，使用工厂得到连接类，再建立一个通道。
		// try-with-resources特性是在括号里的资源不用手动finally进行关闭，前提是资源实现了java.lang.AutoCloseable或java.io.Closeable的类
		try (Connection connection = factory.newConnection();
		     Channel channel = connection.createChannel()) {
			// 声明一个队列：指定名字、是否持久、是否仅此连接、是否自动删除、队列的其他属性
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			for (int i = 0; i < 10; i++) {
				String message = "hello" + i;
				// 进行发布消息，指定交易所、队列名、消息的其他属性、消息体
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
				// 打印看看是否发送了
				System.out.println(" [x] 发送 '" + message + "'");
			}
		}
	}

}
