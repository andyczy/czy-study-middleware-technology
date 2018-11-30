package czy.activemq.test1;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


/**
 * @auther 陈郑游
 * @create 2017/4/4 0004
 * @功能  消息生产者
 * @问题
 * @说明
 * @URL地址
 * @进度描述
 */
public class JMSProducer {

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
		MessageProducer messageProducer;

		// 实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);

		try {
			connection=connectionFactory.createConnection(); // 通过连接工厂获取连接
			connection.start(); // 启动连接
			session=connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE); // 创建Session
			destination=session.createQueue("First-Queue"); // 创建消息队列
			messageProducer=session.createProducer(destination); // 创建消息生产者
			sendMessage(session, messageProducer); // 发送消息
			session.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 发送消息
	 * @param session
	 * @param messageProducer
	 * @throws Exception
	 */
	public static void sendMessage(Session session,MessageProducer messageProducer)throws Exception{
		int SENDNUM=10; // 发送的消息数量

		for(int i=0;i<SENDNUM;i++){
			TextMessage message=session.createTextMessage("ActiveMQ 发送的消息"+i);
			System.out.println("发送消息："+"ActiveMQ 发送的消息"+i);
			messageProducer.send(message);
		}
	}
}
