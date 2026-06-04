package repository;

import java.util.List;

public interface Repository<T> {

    void save(T entity);

    List<T> findAll();
}