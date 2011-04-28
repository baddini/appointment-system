package edu.rit.rdi.as.exceptions;

/**
 * Custom exception for any caught SQL Exceptions.
 * @date Apr 27, 2011
 * @author Eric Kisner
 */
public class DataLayerException extends Exception {

    /**
     * Allows for the cause to be logged as well as a message.
     */
    public DataLayerException( String message, Throwable cause ) {
        super( message, cause );
    }

    /**
     * Verbose message used for logging.
     * @param message A message describing what went wrong. In most cases, the message will contain an error
     * statement, and the SQL that was trying to be run when the error occurred.
     */
    public DataLayerException( String message ) {
        super( message );
    }

    /**
     * Default exception. Should be used to display a simple error message to the user.
     */
    public DataLayerException() {
        this( "The operation could not be completed." );
    }
}
