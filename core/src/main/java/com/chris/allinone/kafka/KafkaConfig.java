package com.chris.allinone.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

/**
 * @author chrischen
 */
@Configuration
@Slf4j
public class KafkaConfig {

    /**
     * @KafkaListener(groupId = "testGroup", topicPartitions = {
     *             @TopicPartition(topic = "topic1", partitions = {"0", "1"}),
     *             @TopicPartition(topic = "topic2", partitions = "0",
     *                     partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))
     *     },concurrency = "6")
     *  //concurrency就是同组下的消费者个数，就是并发消费数，必须小于等于分区总数
     * @param consumerRecord
     */
    @KafkaListener(id = "demo", topics = "topic_demo", groupId = "defaultGroup", autoStartup = "false")
    public void kafkaDemoListener(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {
        log.info(consumerRecord.toString());
        ack.acknowledge();
    }

    @KafkaListener(id = "demo1", topics = "topic_demo", groupId = "default-group", concurrency = "3", batch = "true")
    public void kafkaDemo1Listener(List<ConsumerRecord<String, String>> consumerRecord, Acknowledgment ack) {
        log.info(consumerRecord.toString());
        ack.acknowledge();
    }

}
