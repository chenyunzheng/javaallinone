package com.chris.allinone;

import com.chris.allinone.spring.skills.pojo.Account;
import com.chris.allinone.spring.skills.service.BillService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.util.Locale;

/**
 * @author chrischen
 */
@Slf4j
@SpringBootApplication(exclude = {RabbitAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class})
public class AllinoneApplication {

	@SneakyThrows
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AllinoneApplication.class, args);

		//i18n
		String message = context.getMessage("API_NOT_FOUND", new String[]{"/i8n", "hi"}, "接口未找到", new Locale("cn"));
		System.out.println(message);
		log.info(message);

		//资源加载
		Resource fileResource = context.getResource("file://D:\\cyzhope\\code\\non-project\\allinone\\pom.xml");
		System.out.println(fileResource.contentLength());
		System.out.println(fileResource.getURL());
		Resource httpResource = context.getResource("https://www.baidu.com");
		System.out.println(httpResource.contentLength());
		System.out.println(httpResource.getURL());
		Resource classPathResource = context.getResource("classpath:k8s/demo.yml");
		System.out.println(classPathResource.contentLength());
		System.out.println(classPathResource.getURL());

		//获取运行时环境
		for (PropertySource<?> propertySource : context.getEnvironment().getPropertySources()) {
			System.out.println(propertySource.getClass().getName());
		}
		System.out.println(context.getEnvironment().getProperty("spring.application.name"));
		System.out.println(context.getEnvironment().getProperty("abcd"));
		//事件发布


		//类型转化

		BillService billService = context.getBean("billService", BillService.class);
		System.out.println(billService.accountService());
		System.out.println(billService.accountService());

		//FactoryBean
		System.out.println(context.getBean("andrewFactoryBean"));

	}

	@Bean(autowireCandidate = false)
	@Qualifier("a")
	public Account account() {
		return new Account();
	}

}
