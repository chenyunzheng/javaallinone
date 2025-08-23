package com.chris.allinone.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("kafka/listener")
public class KafkaConsumerController {

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    /**
     * 开启监听
     */
    @GetMapping("/start")
    public String start(@RequestParam("id") String id) {
        String message = null;
        // 判断监听容器是否启动，未启动则将其启动
        if (!registry.getListenerContainer(id).isRunning()) {
            message = String.format("Listener: %s, start", id);
            registry.getListenerContainer(id).start();
        } else {
            // 将其恢复
            message = String.format("Listener: %s, resume", id);
            registry.getListenerContainer(id).resume();
        }
        return message;
    }

    /**
     * 关闭监听
     */
    @GetMapping("/stop")
    public String stop(@RequestParam("id") String id) {
        // 暂停监听
        registry.getListenerContainer(id).pause();
        return String.format("Listener: %s, stop", id);
    }
}