package com.london.reboot;

import org.springframework.boot.test.context.TestConfiguration;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@TestConfiguration
public class TestRedisConfiguration {

    private int redisPort = 6379;
    RedisServer redisServer = new RedisServer( redisPort);
    Jedis jedis = new Jedis("localhost", redisPort);

    public TestRedisConfiguration() throws IOException {
    }

    @PostConstruct
    public void postConstruct() {
    System.out.println("starting redis !!!");
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        jedis.flushDB();
        redisServer.stop();
    }
}
