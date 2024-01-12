package com.chris.allinone.designpattern.command;

/**
 * @author chrischen
 */
public class TwoKeyCommand extends AbstractCommand{

    public TwoKeyCommand(Processor processor) {
        super(processor);
    }

    @Override
    public void execute() {
        getProcessor().twoPressed();
    }
}
