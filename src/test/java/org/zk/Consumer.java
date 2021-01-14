package org.zk;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.sql.DriverManager;
import java.sql.Statement;

public class Consumer {

	@Test
	public void consume() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("my-queue");
		MessageConsumer consumer = session.createConsumer(queue);
		// 方式1
//		consumer.setMessageListener(new MessageListener() {
//			@Override
//			public void onMessage(Message message) {
//				try {
//					TextMessage textMessage = (TextMessage) message;
//					String text = textMessage.getText();
//					// mq中调用mysql,观察cat消息
//					// invokeMySql();
//					System.out.println("消息消费完毕：" + text);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
		// 方式2，spring-jms使用这种方式
		while(true) {
			TextMessage message = (TextMessage)consumer.receive(1000);
			if (message != null)
				System.out.println(message.getText());
		}
//		consumer.close();
//		session.close();
//		connection.close();
//		System.in.read();
	}

	public void invokeMySql() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		java.sql.Connection  connection = DriverManager.getConnection("jdbc:mysql://localhost/zk", "root", "123456");

		Statement statement = connection.createStatement();
		statement.execute("update tb_user set username='11' where id = 1");

		statement.close();
		connection.close();
	}
}
