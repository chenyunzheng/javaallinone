package com.chris.allinone.designpattern.command;

/**
 * @author chrischen
 */
public abstract class AbstractCommand implements Command{

    private Processor processor;

    public AbstractCommand(Processor processor) {
        this.processor = processor;
    }

    public Processor getProcessor() {
        return processor;
    }

}
