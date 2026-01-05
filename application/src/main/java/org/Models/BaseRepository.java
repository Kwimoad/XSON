package org.Models;

import java.sql.SQLException;

/**
 * This is a generic interface for basic database operations (CRUD).
 *
 * @param <T> the type of object to manage
 */
public interface BaseRepository<T> {
    //public boolean add(T o) throws SQLException;
    /**
     * Remove an object from the database.
     *
     * @param o the object to remove
     * @return true if removed successfully, false otherwise
     * @throws SQLException
     */
    public boolean remove(T o) throws SQLException;
    /**
     * Update an object in the database.
     *
     * @param o the object to update
     * @return true if updated successfully, false otherwise
     * @throws SQLException
     */
    public boolean update(T o) throws SQLException;
    /**
     * Find an object in the database by its ID.
     *
     * @param id the object ID
     * @return the object if found, null otherwise
     * @throws SQLException
     */
    public T findById(int id) throws SQLException;
}
