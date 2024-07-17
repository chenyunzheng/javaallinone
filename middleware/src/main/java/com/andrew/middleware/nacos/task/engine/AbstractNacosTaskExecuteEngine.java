package com.andrew.middleware.nacos.task.engine;

import com.andrew.middleware.nacos.task.NacosTask;
import com.andrew.middleware.nacos.task.NacosTaskProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract nacos task execute engine.
 *
 * @author xiweng.yy
 */
public abstract class AbstractNacosTaskExecuteEngine<T extends NacosTask> implements NacosTaskExecuteEngine<T> {
    
    private final Logger log;
    
    private final ConcurrentHashMap<Object, NacosTaskProcessor> taskProcessors = new ConcurrentHashMap<>();
    
    private NacosTaskProcessor defaultTaskProcessor;
    
    public AbstractNacosTaskExecuteEngine(Logger logger) {
        this.log = null != logger ? logger : LoggerFactory.getLogger(AbstractNacosTaskExecuteEngine.class.getName());
    }
    
    @Override
    public void addProcessor(Object key, NacosTaskProcessor taskProcessor) {
        taskProcessors.putIfAbsent(key, taskProcessor);
    }
    
    @Override
    public void removeProcessor(Object key) {
        taskProcessors.remove(key);
    }
    
    @Override
    public NacosTaskProcessor getProcessor(Object key) {
        return taskProcessors.containsKey(key) ? taskProcessors.get(key) : defaultTaskProcessor;
    }
    
    @Override
    public Collection<Object> getAllProcessorKey() {
        return taskProcessors.keySet();
    }
    
    @Override
    public void setDefaultTaskProcessor(NacosTaskProcessor defaultTaskProcessor) {
        this.defaultTaskProcessor = defaultTaskProcessor;
    }
    
    protected Logger getEngineLog() {
        return log;
    }
}
