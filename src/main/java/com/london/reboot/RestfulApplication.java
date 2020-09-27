package com.london.reboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spi.DocumentationType;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spring.web.plugins.Docket;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import javax.cache.Caching;
import javax.cache.CacheManager;
import javax.cache.Cache;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.concurrent.TimeUnit;


@EnableSwagger2
@SpringBootApplication
public class RestfulApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulApplication.class, args);
	}

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.OAS_30);
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		var template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}

	// use below if you want to configure redis non-defaults
//	@Bean
//	JedisConnectionFactory jedisConnectionFactory() {
//		JedisConnectionFactory jedisConFactory
//				= new JedisConnectionFactory();
//		jedisConFactory.setHostName("localhost");
//		jedisConFactory.setPort(6379);
//		return jedisConFactory;
//	}

	@Bean
	@ConditionalOnProperty(name="cache.enabled", havingValue="true")
	public Cache<Long, Object> cache() {
		var cachingProvider = Caching.getCachingProvider();
		var cacheManager = cachingProvider.getCacheManager();
		var config
				= new MutableConfiguration<Long, Object>().setTypes(Long.class, Object.class)
				.setStoreByValue(false)
				.setStatisticsEnabled(true)
				.setExpiryPolicyFactory(FactoryBuilder.factoryOf(new CreatedExpiryPolicy(new Duration(TimeUnit.MINUTES, 5))));
		return cacheManager
				.createCache("simpleCache", config);
	}

}
