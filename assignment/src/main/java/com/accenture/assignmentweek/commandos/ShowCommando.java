package com.accenture.assignmentweek.commandos;

import java.io.FileNotFoundException;

public class ShowCommando implements Commando {

//    String commandoName;
//
//    public ShowCommando(String commandoName) {
//        this.commandoName = commandoName;
//    }

    @Override
    public void execute() throws FileNotFoundException {

        System.out.println("test show commando");

    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return commandoName.startsWith("show");
        //return "show".startsWith("show");
    }
}
