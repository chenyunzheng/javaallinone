package com.chris.allinone.designpattern.command;

public class Main {

    public static void main(String[] args) {
        //遥控器（Invoker）
        RemoteController remoteController = new RemoteController();
        //芯片（Receive）
        Processor processor = new Processor();
        //按键（Command）
        Command oneCommand = new OneKeyCommand(processor);
        Command twoCommand = new TwoKeyCommand(processor);

        remoteController.setCommand(oneCommand);
        remoteController.runCommand();

        remoteController.setCommand(twoCommand);
        remoteController.runCommand();
    }
}
