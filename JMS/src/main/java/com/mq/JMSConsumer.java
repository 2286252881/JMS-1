package com.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSConsumer {
	private static final String USERNAME=ActiveMQConnection.DEFAULT_USER;//默认的链接用户名
	private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD;//默认的链接密码
	private static final String BROKERURL="tcp://192.168.199.130:61616";//默认链接地址
	private static final Integer SENDNUM=10;//发送的消息数量
	
	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory;//链接工厂
		Connection connection = null;//连接
		Session session;//会话，接受或发送消息的线程
		Destination destination;//消息的目的地
		MessageConsumer messageConsumer;//消息消费者
		
		
		//实例化链接工厂
		connectionFactory=new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD, JMSConsumer.BROKERURL);
		
		connection=connectionFactory.createConnection();
		connection.start();
		session=connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		destination=session.createQueue("firstQueue1");//要消费的队列
		
		messageConsumer=session.createConsumer(destination);//创建消费者
		
		while(true){
			TextMessage textMessage=(TextMessage)messageConsumer.receive(100000);
			if(textMessage!=null){
				System.out.println("收到的message:"+textMessage.getText());
			}else{
				break;
			}
		}
	}
}
