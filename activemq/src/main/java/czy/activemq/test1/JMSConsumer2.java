package czy.activemq.test1;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


/**
 * @auther 陈郑游
 * @create 2017/4/4 0004
 * @功能   消息消费者\使用 Listener 监听方式
 * @问题
 * @说明
 * @URL地址
 * @进度描述
 */
public class JMSConsumer2 {

	private static final String USERNAME=ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
	private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
	private static final String BROKEURL=ActiveMQConnection.DEFAULT_BROKER_URL; // 默认的连接地址

	public static void main(String[] args) {

		// 1、创建连接工厂ConnectionFactory对象、需要填写用户名密码、url
		ConnectionFactory connectionFactory;

		// 2、通过ConnectionFactory对象给我们的连接对象、并且启动连接。connection默认是关闭的。
		Connection connection = null;

		// 3、通过Connection对象创建session会话、接受或者发送消息的线程。
		Session session;

		// 4、通过Session对象创建Destination对象创建连接的消息队列。
		Destination destination;

		// 5、通过Session对象创建MessageConsumer对象创建消息消费者
		MessageConsumer messageConsumer;

		// 6、实例化连接工厂
		connectionFactory=new ActiveMQConnectionFactory(JMSConsumer2.USERNAME, JMSConsumer2.PASSWORD, JMSConsumer2.BROKEURL);

		try {
			// 通过连接工厂获取连接
			connection=connectionFactory.createConnection();
			// 启动连接
			connection.start();
			// 创建Session
			session=connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// 创建连接的消息队列
			destination=session.createQueue("First-Queue");
			// 创建消息消费者
			messageConsumer=session.createConsumer(destination);
			// 注册消息监听
			messageConsumer.setMessageListener(new Listener());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
