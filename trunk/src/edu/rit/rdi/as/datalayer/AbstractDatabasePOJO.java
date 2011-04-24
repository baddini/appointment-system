package edu.rit.rdi.as.datalayer;

import edu.rit.rdi.as.database.DatabaseConnection;

/**
 * Abstract UnifiedDatabasePOJO. This class exposes the DatabaseConnection instance for child classes.
 * @date Apr 24, 2011
 * @author Eric Kisner
 */
public abstract class AbstractDatabasePOJO implements UnifiedDatabasePOJO {

    protected DatabaseConnection conn;

    /**
     * DatabaseConnection should already be loaded prior to instantiating an UnifiedDatabasePOJO.
     */
    public AbstractDatabasePOJO() {
        conn = DatabaseConnection.instance();
    }
}
