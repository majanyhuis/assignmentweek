package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.database.StockRepository;

import java.sql.SQLException;

public class TruncateCommando implements Commando {

    private StockRepository stockRepository;

    public TruncateCommando (StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    public void execute () {

        try {
            stockRepository.truncateALL();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("All data has been removed from database with truncate-Commando.");

    }

    public boolean shouldExecute(String commandoName) {
        return "truncate".equalsIgnoreCase(commandoName);
    }

}
