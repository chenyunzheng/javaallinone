package com.chris.allinone.designpattern.command;

/**
 * @author chrischen
 */
public class RemoteController {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void runCommand() {
        this.command.execute();
    }
}
