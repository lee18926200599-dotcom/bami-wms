package com.usercenter.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsercenterServerApplicationTests {
    @Test
    public void testa() {
        System.out.println("测试Redis连接...");
        String host="127.0.0.1";
        int port=6379;
        String password="zzz123456!@";
        System.out.println("主机: " + host);
        System.out.println("端口: " + port);

        try (Jedis jedis = new Jedis(host, port, 2000)) {

            if (password != null && !password.isEmpty()) {
                jedis.auth(password);
            }

            String pingResult = jedis.ping();
            System.out.println("✅ 连接成功: " + pingResult);

            // 获取Redis信息
            String info = jedis.info("server");
            System.out.println("Redis版本: " + info.split("\r\n")[0]);

        } catch (Exception e) {
            System.err.println("❌ 连接失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
