package org.jsut;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;


@SpringBootTest//如果在测试类上添加了这个注解，那么将来单元测试方法执行之前会初始化Spring容器，并创建Spring容器中的所有对象
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void testSet() {

        //让redis中存储一个键值对  StringRedisTemplate
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("username", "张三");
        ops.set("id","1",100, TimeUnit.SECONDS);
    }

    @Test
    //获取redis中存储的键值对
    public void testGet() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String username = ops.get("username");
        String id = ops.get("id");
        System.out.println(id);
        System.out.println(username);
    }
}
