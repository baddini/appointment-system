package edu.rit.rdi.as.datalayer;

import edu.rit.rdi.as.exceptions.DataLayerException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static edu.rit.rdi.as.datalayer.Tables.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * POJO for Doctor table.
 * This class exposes methods to deal with CRUD operations on a Doctor object.
 * @date Apr 24, 2011
 * @author Eric Kisner
 */
public class Doctor extends AbstractDatabasePOJO {

    private int doctorId;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phone;
    private String username;

    public Doctor() {
        super();
    }

    public Doctor( int doctorId, String firstName, String lastName, String gender, String email, String phone,
                   String username ) {
        this();
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.username = username;
    }

    public Doctor( int doctorId ) {
        this();
        this.doctorId = doctorId;
    }

    public int getDoctorId() {
        return doctorId;
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
        return "Doctor{" + "doctorId=" + doctorId
               + "\tfirstName=" + firstName
               + "\tlastName=" + lastName
               + "\tgender=" + gender
               + "\temail=" + email
               + "\tphone=" + phone
               + "\tusername=" + username + '}';
    }

    @Override
    public boolean put() throws DataLayerException {
        if( fetch() == null ) {
            String sql = "INSERT INTO " + Doctor + "(" + asInsertColumns() + ") VALUES ("
                         + "'" + firstName + "',"
                         + "'" + lastName + "',"
                         + "'" + gender + "',"
                         + "'" + email + "',"
                         + "'" + phone + "',"
                         + "'" + username + "'," + ")";
            try {
                conn.executeUpdateQuery( sql );
            } catch( SQLException sqle ) {
                throw new DataLayerException( "Error running INSERT statement: " + sql, sqle );
            }
            return true;
        }
        return false;
    }

    @Override
    public Object fetch( int primaryKeyId ) throws DataLayerException {
        String sql = "SELECT * FROM " + Doctor + " WHERE doctor_id = " + primaryKeyId;
        try {
            ResultSet rs = conn.executeQuery( sql );
            if( buildThisDoctor( conn.getSingleRow( rs ) ) ) {
                return this;
            }
        } catch( SQLException sqle ) {
            throw new DataLayerException( "Error running SELECT statement: " + sql, sqle );
        }
        return null;
    }

    @Override
    public Object fetch() throws DataLayerException {
        return fetch( doctorId );
    }

    @Override
    public boolean post() throws DataLayerException {
        int executeUpdateQuery = -1;
        //We aren't updating the primary key of this table, so we don't want to include it in the map we receive.
        HashMap<String, String> columnsToData = asMap( false );
        for( String key : columnsToData.keySet() ) {
            String sql = "UPDATE " + Doctor + " SET " + key + " = '" + columnsToData.get( key ) + "'"
                         + " WHERE doctor_id = " + doctorId;
            try {
                executeUpdateQuery = conn.executeUpdateQuery( sql );
            } catch( SQLException sqle ) {
                throw new DataLayerException( "Error running UPDATE statement: " + sql, sqle );
            }
        }
        if( executeUpdateQuery < 0 ) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete() throws DataLayerException {
        //There are no appointments for this doctor - we can delete the doctor information
        if( new Appointment( doctorId, "garbage" ).fetch() == null ) {
            String sql = "DELETE FROM " + Doctor + " WHERE doctor_id = " + doctorId;
            try {
                int executeUpdateQuery = conn.executeUpdateQuery( sql );
                if( executeUpdateQuery > 0 ) {
                    return true;
                }
            } catch( SQLException sqle ) {
                throw new DataLayerException( "Error running DELETE statement: " + sql, sqle );
            }
        }
        return false;
    }

    @Override
    public boolean fullDelete() throws DataLayerException {
        //First check if we can just delete this doctor outright.
        if( delete() ) {
            return true;
        } else {
            //If we could not delete the doctor, delete any appointments this doctor may have.
            String sql = "DELETE FROM " + Appointment + " WHERE doctor_id = " + doctorId;
            try {
                int executeUpdateQuery = conn.executeUpdateQuery( sql );
                if( executeUpdateQuery > 0 ) {
                    //Now that we have deleted all associated children of this doctor, we should be able to delete
                    //the doctor's information.
                    return delete();
                }
            } catch( SQLException sqle ) {
                throw new DataLayerException( "Error running DELETE statement: " + sql, sqle );
            }
        }
        return false;
    }

    @Override
    public HashMap asMap( boolean includePrimaryKey ) {
        HashMap<String, String> map = new HashMap<String, String>();
        if( includePrimaryKey ) {
            map.put( "doctor_id", String.valueOf( doctorId ) );
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
    private static String asInsertColumns() {
        return "firstName, lastName, gender, email, phone, username";
    }

    /**
     * Populates this Doctor object with the specified data. The data must contain all fields of the table
     * we are pulling from.
     * @param data The data from a "SELECT *" statement.
     * @return True if the given data is: not null and correctly populates this Doctor object, else false.
     */
    private boolean buildThisDoctor( ArrayList<String> data ) {
        if( data == null || data.isEmpty() || data.size() < 7 ) {
            return false;
        }
        try {
            this.doctorId = Integer.valueOf( data.get( 0 ) );
        } catch( NumberFormatException ignore ) {
            //Since the data we received is invalid, we should return false.
            return false;
        }
        this.username = data.get( 1 );
        this.firstName = data.get( 2 );
        this.lastName = data.get( 3 );
        this.gender = data.get( 4 );
        this.email = data.get( 5 );
        this.phone = data.get( 6 );
        return true;
    }
}
