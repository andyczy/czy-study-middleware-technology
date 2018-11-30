package czy.zookeeper.base;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;


/**
 * @auther 陈郑游
 * @create
 * @功能   Zookeeper
 * @问题
 * @说明
 * @URL地址
 * @进度描述
 */
public class ZookeeperBase {

	/** zookeeper地址 */
	static final String CONNECT_ADDR = "192.168.92.11:2181,192.168.92.12:2181,192.168.92.13:2181";
	/** session超时时间秒 */
	static final int SESSION_OUTTIME = 5000;
	/** 信号量，阻塞程序执行，用于等待zookeeper连接成功，发送成功信号 */
	static final CountDownLatch connectedSemaphore = new CountDownLatch(1);



	public static void main(String[] args) throws Exception{

		ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher() {
			@Override
			public void process(WatchedEvent watchedEvent) {
				//获取事件的状态
				KeeperState keeperState = watchedEvent.getState();
				EventType eventType = watchedEvent.getType();
				//如果是建立连接
				if(KeeperState.SyncConnected == keeperState){
					if(EventType.None == eventType){
						//如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
						connectedSemaphore.countDown();
						System.out.println("ZK已经建立连接！");
					}
				}
			}
		});
		//进行阻塞
		connectedSemaphore.await();
		
		System.out.println("...................");

		//创建父节点
//		zk.create("/Root", "Rootdata".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		//创建子节点
//		zk.create("/Root/children", "children base.dataStructure.data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //获取节点洗信息
		byte[] data = zk.getData("/", false, null);
		System.out.println(new String(data));
		System.out.println(zk.getChildren("/", false));
		
		//修改节点的值
//		zk.setData("/testRoot", "modify base.dataStructure.data root".getBytes(), -1);
//		byte[] base.dataStructure.data = zk.getData("/Root", false, null);
//		System.out.println(new String(base.dataStructure.data));

        //判断节点是否存在
//		System.out.println(zk.exists("/Root/children", false));

		//删除节点
//		zk.delete("/Root/children", -1);
//		System.out.println(zk.exists("/Root/children", false));
		
		zk.close();

	}
}
