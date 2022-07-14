package com.accenture.assignmentweek.repositories;

import com.accenture.assignmentweek.entities.Industry;

import java.util.List;

public class IndustryRepository implements Crud<Industry, Integer> {


    @Override
    public Industry findById(Integer id) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<Industry> findAll() {
        return null;
    }

    @Override
    public List<Industry> findByProperty(String name, Integer id) {
        return null;
    }

    @Override
    public void create(Industry entity) {

    }

    @Override
    public void update(Industry entity) {

    }
}
