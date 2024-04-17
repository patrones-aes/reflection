package co.edu.javeriana.repository;


import java.util.List;

public interface Repository<T, ID> {
    T create(T entity);
    T readById(ID id);
    List<T> readAll();
    T update(T entity);
    void delete(ID id);
}
