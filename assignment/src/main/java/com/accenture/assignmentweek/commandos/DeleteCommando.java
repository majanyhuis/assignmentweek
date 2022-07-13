package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.database.StockRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class DeleteCommando implements Commando  {

    private StockRepository stockRepository;

    public DeleteCommando (StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void execute () {

        try {
            stockRepository.deleteALL();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("All data has been removed from database.");
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "delete".equalsIgnoreCase(commandoName);
    }


}
