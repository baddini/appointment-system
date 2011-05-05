package edu.rit.rdi.as.datalayer;

import edu.rit.rdi.as.database.DatabaseConnection;
import edu.rit.rdi.as.exceptions.DataLayerException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static edu.rit.rdi.as.datalayer.Tables.*;

/**
 * Security provides methods dealing with logging in a user.
 * @date May 1, 2011
 * @author Eric Kisner
 */
public class Security {

    private DatabaseConnection conn;

    public Security() {
        conn = DatabaseConnection.instance();
    }

    /**
     * Logs a patient in given their username and password.
     * @param username The username of the patient.
     * @param password The password of the patient. The password should have previously been hashed in the
     *                 presentation layer (for security reasons).
     * @return The identification number of the patient, or -1 if the username/password combination was incorrect.
     */
    public int loginPatient( String username, String password ) throws DataLayerException {
        String sql = "SELECT patient_id FROM " + Patient + " WHERE username = '" + username + "' AND password = '"
                     + password + "'";
        try {
            ResultSet rs = conn.executeQuery( sql );
            //Get the only data row from the result set
            ArrayList<String> row = conn.getSingleRow( rs );
            if( !row.isEmpty() ) {
                //There is only one piece of information in the ResultSet: the patient's ID
                int patientId = Integer.parseInt( row.get( 0 ) );
                return patientId;
            }
        } catch( SQLException sqle ) {
            throw new DataLayerException( "Problem running query: " + sql, sqle );
        } catch( NumberFormatException nfe ) {
            throw new DataLayerException( "Problem running query: " + sql, nfe );
        }
        return -1;
    }

    /**
     * Logs a doctor in given their username and password.
     * @param username The username of the doctor.
     * @param password The password of the doctor. The password should have previously been hashed in the
     *                 presentation layer (for security reasons).
     * @return The identification number of the doctor, or -1 if the username/password combination was incorrect.
     */
    public int loginDoctor( String username, String password ) throws DataLayerException {
        String sql = "SELECT doctor_id FROM " + Doctor + " WHERE username = '" + username + "' AND password = '"
                     + password + "'";
        try {
            ResultSet rs = conn.executeQuery( sql );
            //Get the only data row from the result set
            ArrayList<String> row = conn.getSingleRow( rs );
            if( !row.isEmpty() ) {
                //There is only one piece of information in the ResultSet: the patient's ID
                int patientId = Integer.parseInt( row.get( 0 ) );
                return patientId;
            }
        } catch( SQLException sqle ) {
            throw new DataLayerException( "Problem running query: " + sql, sqle );
        } catch( NumberFormatException nfe ) {
            throw new DataLayerException( "Problem running query: " + sql, nfe );
        }
        return -1;
    }
}
