package org.zk;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		// 不开启事务，自动提交
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 创建队列
		Destination destination = session.createQueue("my-queue");

		MessageProducer producer = session.createProducer(destination);

		TextMessage textMessage1 = session.createTextMessage("hello");
		producer.send(textMessage1);

		producer.close();
		session.close();
		connection.close();

		System.in.read();
	}
}
