package com.chris.allinone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Locale;

@Slf4j
@SpringBootApplication(exclude = {RabbitAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class})
public class AllinoneApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AllinoneApplication.class, args);

		//i8n
		String message = context.getMessage("API_NOT_FOUND", new String[]{"/i8n", "hi"}, "接口未找到", new Locale("cn"));
		System.out.println(message);
		log.info(message);


	}

}
