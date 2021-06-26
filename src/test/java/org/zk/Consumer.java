package org.zk;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumer {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] 等待消息.");

		// 声明一个回调，进行处理得到的队列消息。
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] 接收 '" + message + "'");
		};

		// 队列名、 如果服务器应考虑消息传递后已确认，则为true、传递回调、取消回调
		// 消费开启：一直保持着，得到了队列内容，就执行回调函数
		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

	}

}
