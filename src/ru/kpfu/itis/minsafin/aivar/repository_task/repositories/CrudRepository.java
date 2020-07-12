package ru.kpfu.itis.minsafin.aivar.repository_task.repositories;

import java.util.List;

public interface CrudRepository<T> {
    List<T> findAll();
    T findById(Long id);
    void save(T entity);
    void update(T entity);
}
