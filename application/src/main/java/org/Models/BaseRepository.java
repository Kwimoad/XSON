package org.Models;

import java.sql.SQLException;

public interface BaseRepository<T> {
    //public boolean add(T o) throws SQLException;
    public boolean remove(T o) throws SQLException;
    public boolean update(T o) throws SQLException;
    public T findById(int id) throws SQLException;
}
