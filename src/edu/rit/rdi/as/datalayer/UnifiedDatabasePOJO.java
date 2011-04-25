package edu.rit.rdi.as.datalayer;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Interface for all plain old java objects from the database to implement.
 * @date Apr 24, 2011
 * @author Eric Kisner
 */
public interface UnifiedDatabasePOJO {

    /**
     * Inserts the DatabasePOJO into the database. If this object's data already exists in the database,
     * this method will run the post method instead.
     * @return True if the put or post was successful, else false.
     * @see UnifiedDatabasePOJO#post() 
     */
    public boolean put() throws SQLException;

    /**
     * Fetches an instance of this DatabasePOJO with the specified key. The instance object must be empty before
     * using this method, and the object will be populate with the retrieve data.
     * @param primaryKeyId The primary key of the object we want to fetch from the database.
     * @return A populated instance of the database object, or null if the data was invalid.
     * @throws SQLException
     */
    public Object fetch( int primaryKeyId ) throws SQLException;

    /**
     * Fetches an instance of this DatbasePOJO. The instance object must have, at least, the primary key id
     * filled in before populating the rest of the object's data.
     * @return A populated instance of the database object, or null if the data was invalid.
     * @throws SQLException
     */
    public Object fetch() throws SQLException;

    /**
     * Updates data in the database based on the instance object's data.
     * @return True if data was updated, else false.
     * @throws SQLException
     */
    public boolean post() throws SQLException;

    /**
     * Deletes this instance object from the database. If the object has child data, or foreign key constraints,
     * this method will do nothing.
     * @return True if the delete occurred (there was no child data), else false.
     * @throws SQLException
     */
    public boolean delete() throws SQLException;

    /**
     * Deletes this instance object from the database. If the object has child data, or foreign key constraints,
     * this method will also delete those data instances as well.
     * @return True if the full delete was successful, else false.
     * @throws SQLException
     */
    public boolean fullDelete() throws SQLException;

    /**
     * Returns this instance object as a map of table headers to this object's data.
     * @param includePrimaryKey We may not want to have the primary key value in the returned map.
     * @return Untyped mapping of column header to this object's value for that column. The returned map will
     *         most likely be of string to string, however, there is a chance that int and datetime will be included.
     */
    public HashMap asMap( boolean includePrimaryKey );
}
