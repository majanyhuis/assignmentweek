package com.accenture.assignmentweek.commandos;

public interface Commando {

    void execute();

    boolean shouldExecute(String commandoName);

}
