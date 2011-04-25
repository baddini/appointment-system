package edu.rit.rdi.as.datalayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static edu.rit.rdi.as.datalayer.Tables.*;

/**
 * @date Apr 24, 2011
 * @author Eric Kisner
 */
public class Patient extends AbstractDatabasePOJO {

    private int patientId;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phone;
    private String username;
    private String password;

    public Patient() {
        super();
    }

    public Patient( int patientId, String firstName, String lastName, String gender, String email, String phone,
                    String username, String password ) {
        this();
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public Patient( int patientId ) {
        this();
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGender() {
        return gender;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Patient{" + "patientId=" + patientId
               + "\tfirstName=" + firstName
               + "\tlastName=" + lastName
               + "\tgender=" + gender
               + "\temail=" + email
               + "\tphone=" + phone
               + "\tusername=" + username
               + "\tpassword=" + password + '}';
    }

    public boolean put() throws SQLException {
        if( fetch() == null ) {
            String sql = "INSERT INTO " + Patient + "(" + asColumns() + ") VALUES ("
                         + patientId + ","
                         + firstName + ","
                         + lastName + ","
                         + gender + ","
                         + email + ","
                         + phone + ","
                         + username + ","
                         + password + ")";
            conn.executeUpdateQuery( sql );
            return true;
        } else {
            return post();
        }
    }

    public Object fetch( int primaryKeyId ) throws SQLException {
        String sql = "SELECT * FROM " + Patient + " WHERE patient_id = " + primaryKeyId;
        ResultSet rs = conn.executeQuery( sql );
        if( buildThisPatient( conn.getSingleRow( rs ) ) ) {
            return this;
        }
        return null;
    }

    public Object fetch() throws SQLException {
        return fetch( patientId );
    }

    public boolean post() throws SQLException {
        int executeUpdateQuery = -1;
        //We aren't updating the primary key of this table, so we don't want to include it in the map we receive.
        HashMap<String, String> columnsToData = asMap( false );
        for( String key : columnsToData.keySet() ) {
            String sql = "UPDATE " + Patient + " SET " + key + " = '" + columnsToData.get( key ) + "'"
                         + " WHERE patient_id = " + patientId;
            executeUpdateQuery = conn.executeUpdateQuery( sql );
        }
        if( executeUpdateQuery < 0 ) {
            return false;
        }
        return true;
    }

    public boolean delete() throws SQLException {
        //There are no appointments for this patient - we can delete the patient information
        if( new Appointment( patientId ).fetch() == null ) {
            String sql = "DELETE FROM " + Patient + " WHERE patient_id = " + patientId;
            int executeUpdateQuery = conn.executeUpdateQuery( sql );
            if( executeUpdateQuery > 0 ) {
                return true;
            }
        }
        return false;
    }

    public boolean fullDelete() throws SQLException {
        //First check if we can just delete this patient outright.
        if( delete() ) {
            return true;
        } else {
            //If we could not delete the patient, delete any appointments that patient may have.
            String sql = "DELETE FROM " + Appointment + " WHERE patient_id = " + patientId;
            int executeUpdateQuery = conn.executeUpdateQuery( sql );
            if( executeUpdateQuery > 0 ) {
                //Now that we have deleted all associated children of this patient, we should be able to delete
                //the patient's information.
                return delete();
            }
        }
        return false;
    }

    public HashMap asMap( boolean includePrimaryKey ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    private static String asColumns() {
        return "patientId, firstName, lastName, gender, email, phone, username, password";
    }

    /**
     * Populates this Patient object with the specified data.
     * @param data
     * @return True if the given data is: not null and correctly populates this Patient object, else false.
     */
    private static boolean buildThisPatient( ArrayList<String> data ) {
        return false;
    }
}
