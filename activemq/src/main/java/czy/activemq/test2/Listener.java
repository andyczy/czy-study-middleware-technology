package czy.activemq.test2;

/**
 * 消息监听-订阅者一
 * @author Administrator
 *
 */
public class Listener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			System.out.println("订阅者一收到的消息："+((TextMessage)message).getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
