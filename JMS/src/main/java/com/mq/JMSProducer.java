package com.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSProducer {
	
	private static final String USERNAME=ActiveMQConnection.DEFAULT_USER;//默认的链接用户名
	private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD;//默认的链接密码
	private static final String BROKERURL="tcp://192.168.199.130:61616";//默认链接地址
	private static final Integer SENDNUM=10;//发送的消息数量
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;//链接工厂
		Connection connection = null;//连接
		Session session;//会话，接受或发送消息的线程
		Destination destination;//消息的目的地
		MessageProducer messageProducer;//消息生产者
		//实例化链接工厂
		connectionFactory=new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKERURL);
		
		try {
			connection=connectionFactory.createConnection();//通过链接工厂获取链接
			connection.start();
			
			session=connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);//创建session
			destination=session.createQueue("firstQueue1");//创建消息队列
			messageProducer=session.createProducer(destination);//创建消息生产者
			
			sendMessage(session, messageProducer);//发送消息
			
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void sendMessage(Session session,MessageProducer messageProducer) throws JMSException{
		for (int i = 0; i < JMSProducer.SENDNUM; i++) {
			TextMessage textMessage=session.createTextMessage("ActiveMQ 发送的消息:"+i);
			System.out.println("发送消息:"+textMessage);
			messageProducer.send(textMessage);
		}
	}
}
