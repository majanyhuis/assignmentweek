package com.accenture.assignmentweek;

import com.accenture.assignmentweek.commandos.Commando;
import com.accenture.assignmentweek.commandos.ExitCommando;
import com.accenture.assignmentweek.commandos.ImportCommando;
import com.accenture.assignmentweek.connector.Connector;
//import com.sun.jdi.connect.Connector;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class StockApp {

    public void run() throws SQLException, FileNotFoundException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Stock Application!");

        Connector connector = new Connector();
        Connection connection = new Connector().getConnection();

        // neue Kommandos!
        Commando exitCommando = new ExitCommando();
        Commando importCommando = new ImportCommando();

        // Liste mit den Kommandos
        ArrayList<Commando> commandos = new ArrayList<>();
        commandos.add(exitCommando);
        commandos.add(importCommando);

        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("Choose from the following commands: exit, import");
            String userInput = scanner.nextLine();

            for (Commando commando : commandos) {
                if (commando.shouldExecute(userInput)) {
                    commando.execute();
                }
            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException, SQLException {
        StockApp stockApp = new StockApp();
        stockApp.run();

        Scanner input = new Scanner(new File("STOCKDATA:txt"));

        while (input.hasNext()) {

            String test = input.nextLine();
            System.out.println(test);

        }
    }



}
