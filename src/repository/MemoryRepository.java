package repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Grace Solano
 * @author Luis Gomez
 * @author Dilan Badilla
 * */

public class MemoryRepository<T>
        implements Repository<T> {

    private final List<T> data =
            new ArrayList<>();

    @Override
    public void save(T entity) {
        data.add(entity);
    }

    @Override
    public List<T> findAll() {
        return data;
    }
}