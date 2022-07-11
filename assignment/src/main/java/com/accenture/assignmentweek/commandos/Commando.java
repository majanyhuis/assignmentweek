package com.accenture.assignmentweek.commandos;

import java.io.FileNotFoundException;

public interface Commando {

     void execute() throws FileNotFoundException; //throws SQLException;

      boolean shouldExecute(String commandoName);

}
