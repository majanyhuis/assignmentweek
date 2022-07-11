package com.accenture.assignmentweek.commandos;

 public interface Commando {

     void execute(); //throws SQLException;

      boolean shouldExecute(String commandoName);

}
