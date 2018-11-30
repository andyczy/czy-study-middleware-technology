package redis;


import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;

/**
 * @auther 陈郑游
 * @create 2016/12/17 0017
 * @description: redis测试：
 * @problem:
 * @company address:
 * @Modify the time again:
 * @Modify the author again:
 */
public class JedisTest {

    private static final String RedisAddress = "192.168.29.132";
    private static final int RedisPort = 6379;
    /**
     * 集群端口
     */
    private static final int RedisPort1 = 7001;
    private static final int RedisPort2 = 7002;
    private static final int RedisPort3 = 7003;
    private static final int RedisPort4 = 7004;
    private static final int RedisPort5 = 7005;
    private static final int RedisPort6 = 7006;


    /**
     * jdies
     */
    @Test
    public void jedis() {
        //创建一个jedis的对象。
        Jedis jedis = new Jedis(RedisAddress, RedisPort);
        //调用jedis对象的方法，方法名称和redis的命令一致。
        jedis.set("key1", "jedis test");
        String string = jedis.get("key1");
        System.out.println(string);
        //关闭jedis。
        jedis.close();
    }

    /**
     * jdies连接池(JedisPool)
     */
    @Test
    public void jedis01() {
        JedisPool jedisPool = new JedisPool(RedisAddress, RedisPort);
        Jedis jedis = jedisPool.getResource();
        jedis.set("czy", "chenzhengyou");
        System.out.println(jedis.get("czy"));
        jedis.close();
        jedisPool.close();
    }

    /**
     * 集群版:自带连接池
     */
    @Test
    public void testJedisCluster() {
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort(RedisAddress, RedisPort1));
        nodes.add(new HostAndPort(RedisAddress, RedisPort2));
        nodes.add(new HostAndPort(RedisAddress, RedisPort3));
        nodes.add(new HostAndPort(RedisAddress, RedisPort4));
        nodes.add(new HostAndPort(RedisAddress, RedisPort5));
        nodes.add(new HostAndPort(RedisAddress, RedisPort6));

        JedisCluster cluster = new JedisCluster(nodes);

        cluster.set("czy", "1000");
        String string = cluster.get("czy");
        System.out.println(string);
        cluster.close();
    }


    /**
     * 整合测试:单机版测试
     */
    @Test
    public void testSpring() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/application-jedis.xml");
        JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
        Jedis jedis = pool.getResource();
        String string = jedis.get("key1");
        System.out.println(string);
        jedis.close();
        pool.close();
    }

    /**
     * 整合测试:集群版测试
     */
    @Test
    public void testSpringJedisCluster() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/application-jedis.xml");
        JedisCluster jedisCluster =  (JedisCluster) applicationContext.getBean("redisClient");
        String string = jedisCluster.get("key1");
        System.out.println(string);
        jedisCluster.close();
    }

}
