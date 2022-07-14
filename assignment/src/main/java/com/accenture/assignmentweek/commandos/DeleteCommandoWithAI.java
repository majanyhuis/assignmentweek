package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.repositories.StockRepository;

import java.sql.SQLException;

public class DeleteCommandoWithAI implements Commando{

    private StockRepository stockRepository;

    public DeleteCommandoWithAI (StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void execute () {

        try {
            stockRepository.deleteAllWithAutoIncrement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("All data has been removed from database. IDs start with 1 again. Import your Data with 'import'.");
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "deleteAI".equalsIgnoreCase(commandoName);
    }
}
