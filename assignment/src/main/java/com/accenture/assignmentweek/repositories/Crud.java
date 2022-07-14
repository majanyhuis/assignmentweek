package com.accenture.assignmentweek.repositories;

import java.util.List;

public interface Crud<E, I> {

    E findById(I id);

    void deleteById(I id);

    List<E> findAll();

    List<E> findByProperty(String name, I id);

    void create(E entity);

    void update(E entity);

}
