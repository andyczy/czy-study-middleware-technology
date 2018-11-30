package czy.activemq.test2;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Session;
import java.sql.Connection;


/**
 * @auther 陈郑游
 * @create 2017/4/4 0004
 *       发布-订阅消息模式实现
 * @功能 消息消费者-消息订阅者一
 * @问题
 * @说明
 * @URL地址
 * @进度描述
 */
public class JMSConsumer {

	//安全机制
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

		// 实例化连接工厂
		connectionFactory=new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD, JMSConsumer.BROKEURL);

		try {
			connection=connectionFactory.createConnection();  // 通过连接工厂获取连接
			connection.start(); // 启动连接
			session=connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE); // 创建Session
			// destination=session.createQueue("FirstQueue1");  // 创建连接的消息队列
			destination=session.createTopic("First-Topic");
			messageConsumer=session.createConsumer(destination); // 创建消息消费者
			messageConsumer.setMessageListener(new Listener()); // 注册消息监听
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
