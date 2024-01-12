package com.chris.allinone.designpattern.command;

/**
 * @author chrischen
 */
public class OneKeyCommand extends AbstractCommand{

    public OneKeyCommand(Processor processor) {
        super(processor);
    }

    @Override
    public void execute() {
        getProcessor().onePressed();
    }
}
