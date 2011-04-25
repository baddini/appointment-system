package edu.rit.rdi.as.datalayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static edu.rit.rdi.as.datalayer.Tables.*;

/**
 * POJO for Patient table.
 * This class exposes methods to deal with CRUD operations on a Patient object.
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

    public Patient() {
        super();
    }

    public Patient( int patientId, String firstName, String lastName, String gender, String email, String phone,
                    String username ) {
        this();
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.username = username;
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
               + "\tusername=" + username + '}';
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
                         + username + "," + ")";
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
        HashMap<String, String> map = new HashMap<String, String>();
        if( includePrimaryKey ) {
            map.put( "patient_id", String.valueOf( patientId ) );
        }
        map.put( "firstName", firstName );
        map.put( "lastName", lastName );
        map.put( "gender", gender );
        map.put( "email", email );
        map.put( "phone", phone );
        map.put( "username", username );
        return map;
    }

    /**
     * Returns this object's fields as a String of comma-separated values.
     */
    private static String asColumns() {
        return "patient_id, firstName, lastName, gender, email, phone, username";
    }

    /**
     * Populates this Patient object with the specified data. The data must contain all fields of the table
     * we are pulling from.
     * @param data The data from a "SELECT *" statement.
     * @return True if the given data is: not null and correctly populates this Patient object, else false.
     */
    private boolean buildThisPatient( ArrayList<String> data ) {
        if( data == null || data.isEmpty() || data.size() < 7 ) {
            return false;
        }
        try {
            this.patientId = Integer.valueOf( data.get( 0 ) );
        } catch( NumberFormatException ignore ) {
            return false;
        }
        this.firstName = data.get( 1 );
        this.lastName = data.get( 2 );
        this.gender = data.get( 3 );
        this.email = data.get( 4 );
        this.phone = data.get( 5 );
        this.username = data.get( 6 );

        return true;
    }
}
