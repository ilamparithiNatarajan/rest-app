package com.london.reboot;

import com.london.reboot.config.LoggingInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spi.DocumentationType;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spring.web.plugins.Docket;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.Caching;
import javax.cache.Cache;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.concurrent.TimeUnit;


@EnableSwagger2
@SpringBootApplication
public class RestfulApplication {

	@Value("${redisPort}")
	private int redisPort;

	@Value("${request-response-logging}")
	private boolean logSwitch;

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(RestfulApplication.class);
		springApplication.setApplicationStartup(new BufferingApplicationStartup(100));
		springApplication.run(args);
	}

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.OAS_30);
	}

//	@Bean
//	JedisConnectionFactory jedisConnectionFactory() {
//		return new JedisConnectionFactory();
//	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		var template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}

	// use below if you want to configure redis non-defaults
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConFactory
				= new JedisConnectionFactory();
		jedisConFactory.setHostName("localhost");
		jedisConFactory.setPort(redisPort);
		return jedisConFactory;
	}

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

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		if(!logSwitch) {
			return restTemplate;
		}
		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		if (CollectionUtils.isEmpty(interceptors)) {
			interceptors = new ArrayList<>();
		}
		interceptors.add(new LoggingInterceptor());
		restTemplate.setInterceptors(interceptors);
		return restTemplate;
	}

}
