package com.accenture.assignmentweek.commandos;

public class ExitCommando implements Commando {

    @Override
    public void execute() {
        System.out.println("Tschüssli");
        System.exit(0);
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "exit".equalsIgnoreCase(commandoName);
    }
}
