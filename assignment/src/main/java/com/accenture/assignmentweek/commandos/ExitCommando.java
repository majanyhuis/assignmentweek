package com.accenture.assignmentweek.commandos;

public class ExitCommando implements Commando {

    public void execute() {
        System.out.println("Tschüssli-Müsli");
        System.exit(0);
    }

    public boolean shouldExecute(String commandoName) {
        return "exit".equalsIgnoreCase(commandoName);
    }

}
