package com.chris.allinone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {RabbitAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class})
public class AllinoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllinoneApplication.class, args);
	}

}
