package com.chris.allinone.kafka.nativeuse.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 * @date 2025/8/29
 * @desc KafkaProducer
 */
public class KafkaPassCarProducer {

    public static final String TOPIC_PASSCAR = "topic_passcar";
    public static final int MESSAGES_PER_SECOND = 10; // 每秒生成的消息数量

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "host180:9092,host181:9092,host182:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.LINGER_MS_CONFIG, 5);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 500);
//        props.put(ProducerConfig.RETRIES_CONFIG, 3);
//        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        // 创建JSON序列化器
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        // 创建数据生成器
        PassCarDataGenerator generator = new PassCarDataGenerator();

        System.out.println("开始生成车辆过车数据并发送到 Kafka...");
        System.out.println("主题: " + TOPIC_PASSCAR);
        System.out.println("按 Ctrl+C 停止程序");

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            final int[] messageCount = {0};
            while (true) {
                for (int i = 0; i < MESSAGES_PER_SECOND; i++) {
                    PassCarData passCarData = generator.generateVehicleData();
                    try {
                        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_PASSCAR, objectMapper.writeValueAsString(passCarData));
                        producer.send(producerRecord, new Callback() {
                            @Override
                            public void onCompletion(RecordMetadata metadata, Exception exception) {
                                if (exception != null) {
                                    System.err.println("发送消息失败: " + exception.getMessage());
                                } else {
                                    messageCount[0]++;
                                }
                            }
                        });
                    } catch (JsonProcessingException e) {
                        System.err.println("JSON 序列化失败: " + e.getMessage());
                    }
                    if (messageCount[0] % 100 == 0) {
                        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                        System.out.printf("[%s] 已发送 %d 条消息，最新: %s%n",
                                time, messageCount[0], passCarData.getPlateNumber());
                    }
                }
                // 等待
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("程序被中断");
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Kafka 生产者异常: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
