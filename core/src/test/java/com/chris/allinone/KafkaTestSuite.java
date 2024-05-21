package com.chris.allinone;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@SpringBootTest
@Slf4j
public class KafkaTestSuite {

    private static final String TOPIC = "topic_demo";

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @TestX("kafka producer基本使用")
    void testKafkaProducer() {
        int messageNum = 50;
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.38.170.180:9092,172.38.170.181:9092,172.38.170.182:9092");
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432); //32m
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384); //16k
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        String key = "demo";
        for (int i = 0; i < messageNum; i++) {
            String message = "message: i = " + i;
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, key + i, message);
            kafkaProducer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        //消息投递失败，进行补偿（放到数据库，status=-1，后台线程进行补偿）
                        //可以使用redis替代数据库，或者用redis的pubsub
                        log.error(message + "消息投递失败", exception);
                    } else {
                        //消息投递成功
                        log.info(message + "投递成功");
                    }
                }
            });
        }
        kafkaProducer.close();
    }

    @TestX("kafka producer + springboot基本使用")
    void testKafkaProducerSpringBoot() {
        int messageNum = 10;
        //CountDownLatch countDownLatch = new CountDownLatch(messageNum);
        String key = "demo";
        for (int i = 0; i < messageNum; i++) {
            String message = "message: i = " + i;
            kafkaTemplate.send(TOPIC, key + i, message).addCallback(
                    new SuccessCallback<SendResult<String, String>>() {
                        @Override
                        public void onSuccess(SendResult<String, String> result) {
                            //消息投递成功
                            log.info(message + "投递成功");
                        }
                    }, new FailureCallback() {
                        @Override
                        public void onFailure(Throwable ex) {
                            //消息投递失败，进行补偿（放到数据库，status=-1，后台线程进行补偿）
                            //可以使用redis替代数据库，或者用redis的pubsub
                            log.error(message + "消息投递失败", ex);
                        }
                    });

        }
    }

    @TestX("kafka consumer基本使用")
    void testKafkaConsumer() {
        int messageNum = 10;
        String groupId = "defaultGroup";
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.38.170.180:9092,172.38.170.181:9092,172.38.170.182:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 1000);
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10 * 1000);
        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 30 * 1000);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 200);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Collections.singleton(TOPIC));
        while (true) {
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
            if (!consumerRecords.isEmpty()) {
                //业务逻辑
                //1. 实现消费消息的幂等，避免重复消费（重复消息记录日志）
                //   消费消息的业务中，如果涉及存储则先查存储中是否已经存在messageID，比如利用es存储消息，db设置messageID为唯一键
                //                  如果不涉及存储则考虑使用redis，对messageID缓存（设置过期时间）
                //2. 消费消息失败，存入db或者redis或者kafka其他topic
                consumerRecords.forEach(cr -> {
                    log.info("消费消息：partition = {}; offset = {}; key = {}; value = {}",
                            cr.partition(), cr.offset(), cr.key(), cr.value());
                });
                //同步提交offset
                kafkaConsumer.commitSync();
                /*kafkaConsumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                        //异步提交offset

                    }
                });*/
            }
        }
    }

    @TestX("kafka consumer + springboot kafka listener基本使用")
    void testKafkaConsumerSpringBoot() {
        //参见KafkaConfig
        try {
            Thread.sleep(150000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
