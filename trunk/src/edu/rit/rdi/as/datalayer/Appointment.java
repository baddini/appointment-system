package edu.rit.rdi.as.datalayer;

import edu.rit.rdi.as.exceptions.DataLayerException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static edu.rit.rdi.as.datalayer.Tables.*;

/**
 * POJO for Appointment table.
 * This class exposes methods to deal with CRUD operations on an Appointment object.
 * @date Apr 24, 2011
 * @author Eric Kisner
 */
public class Appointment extends AbstractDatabasePOJO {

    private int appointmentId;
    private int doctorId;
    private int patientId;
    private String date;
    private int duration;

    public Appointment() {
        super();
    }

    public Appointment( int appointmentId, int doctorId, int patientId, String date, int duration ) {
        this();
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.duration = duration;
    }

    public Appointment( int appointmentId, int doctorId, int patientId ) {
        this();
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public Appointment( int patientId ) {
        this();
        this.patientId = patientId;
    }

    public Appointment( int doctorId, String garbage ) {
        this();
        this.doctorId = doctorId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getDate() {
        return date;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getDuration() {
        return duration;
    }

    public int getPatientId() {
        return patientId;
    }

    @Override
    public String toString() {
        return "Appointment{" + "appointmentId=" + appointmentId
               + "\tdoctorId=" + doctorId
               + "\tpatientId=" + patientId
               + "\tdate=" + date
               + "\tduration=" + duration + '}';
    }

    public boolean put() throws DataLayerException {
        if( fetch() == null ) {
            String sql = "INSERT INTO " + Appointment + "(" + asColumns() + ") VALUES ("
                         + appointmentId + ","
                         + doctorId + ","
                         + patientId + ","
                         + date + ","
                         + duration + ")";
            try {
                conn.executeUpdateQuery( sql );
            } catch( SQLException sqle ) {
                throw new DataLayerException( "Couldn't run INSERT statement: " + sql );
            }
            return true;
        } else {
            return post();
        }
    }

    public Object fetch( int primaryKeyId ) throws DataLayerException {
        String sql = "SELECT * FROM " + Appointment + " WHERE appointment_id = " + primaryKeyId;
        try {
            ResultSet rs = conn.executeQuery( sql );
            if( buildThisAppointment( conn.getSingleRow( rs ) ) ) {
                return this;
            }
        } catch( SQLException sqle ) {
            throw new DataLayerException( "Error running SELECT statement: " + sql );
        }
        return null;
    }

    public Object fetch() throws DataLayerException {
        return fetch( appointmentId );
    }

    public boolean post() throws DataLayerException {
        int executeUpdateQuery = -1;
        //We aren't updating the primary key of this table, so we don't want to include it in the map we receive.
        HashMap<String, String> columnsToData = asMap( false );
        for( String key : columnsToData.keySet() ) {
            String sql = "UPDATE " + Appointment + " SET " + key + " = '" + columnsToData.get( key ) + "'"
                         + " WHERE appointment_id = " + appointmentId;
            try {
                executeUpdateQuery = conn.executeUpdateQuery( sql );
            } catch( SQLException sqle ) {
                throw new DataLayerException( "Error running UPDATE statement: " + sql );
            }
        }
        if( executeUpdateQuery < 0 ) {
            return false;
        }
        return true;
    }

    public boolean delete() throws DataLayerException {
        //There are no children for an appointment, so we can just delete this appointment outright.
        String sql = "DELETE FROM " + Appointment + " WHERE appointment_id = " + appointmentId;
        try {
            int executeUpdateQuery = conn.executeUpdateQuery( sql );
            if( executeUpdateQuery > 0 ) {
                return true;
            }
        } catch( SQLException sqle ) {
            throw new DataLayerException( "Error running DELETE statement: " + sql );
        }
        return false;
    }

    public boolean fullDelete() throws DataLayerException {
        //Since there are no children for an appointment, we can just run the delete method.
        return delete();
    }

    public HashMap asMap( boolean includePrimaryKey ) {
        //Untyped hashmap, since we will be dealing with integers and Strings - easier to cast the information
        //since we know what we are getting
        HashMap map = new HashMap();
        if( includePrimaryKey ) {
            map.put( "appointment_id", String.valueOf( appointmentId ) );
        }
        map.put( "doctor_id", String.valueOf( doctorId ) );
        map.put( "patient_id", String.valueOf( patientId ) );
        map.put( "date", date );
        map.put( "duration", duration );
        return map;
    }

    /**
     * Returns this object's fields as a String of comma-separated values.
     */
    private static String asColumns() {
        return "appointment_id, doctor_id, patient_id, date, duration";
    }

    /**
     * Populates this Appointment object with the specified data. The data must contain all fields of the table
     * we are pulling from.
     * @param data The data from a "SELECT *" statement.
     * @return True if the given data is: not null and correctly populates this Appointment object, else false.
     */
    private boolean buildThisAppointment( ArrayList<String> data ) {
        if( data == null || data.isEmpty() || data.size() < 5 ) {
            return false;
        }
        try {
            this.appointmentId = Integer.valueOf( data.get( 0 ) );
            this.doctorId = Integer.valueOf( data.get( 1 ) );
            this.patientId = Integer.valueOf( data.get( 2 ) );
            this.duration = Integer.valueOf( data.get( 4 ) );
        } catch( NumberFormatException ignore ) {
            //Since the data we have received is invalid, we should return false.
            return false;
        }
        this.date = data.get( 3 );

        return true;
    }
}
