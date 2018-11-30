package czy.activemq.test1;

/**
 * @auther 陈郑游
 * @create 2017/4/4 0004
 * @功能   消息监听
 * @问题
 * @说明
 * @URL地址
 * @进度描述
 */
public class Listener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			System.out.println("收到的消息："+((TextMessage)message).getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
