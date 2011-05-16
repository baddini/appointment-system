package edu.rit.rdi.as.datalayer;

import edu.rit.rdi.as.exceptions.DataLayerException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public Appointment( int patientId ) {
        this();
        this.patientId = patientId;
    }

    public Appointment( int doctorId, String garbage ) {
        this();
        this.doctorId = doctorId;
    }

    public Appointment( int doctorId, int patientId, String date, int duration ) {
        this();
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.duration = duration;
    }

    public void setAppointmentId( int appointmentId ) {
        this.appointmentId = appointmentId;
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

    @Override
    public boolean put() throws DataLayerException {
        if( fetch() == null ) {
            String sql = "INSERT INTO " + Appointment + "(" + asInsertColumns() + ") VALUES ("
                         + doctorId + ","
                         + patientId + ","
                         + "'" + date + "',"
                         + duration + ")";
            try {
                conn.executeUpdateQuery( sql );
            } catch( SQLException sqle ) {
                throw new DataLayerException( "Couldn't run INSERT statement: " + sql, sqle );
            }
            return true;
        }
        return false;
    }

    @Override
    public Object fetch( int primaryKeyId ) throws DataLayerException {
        String sql = "SELECT * FROM " + Appointment + " WHERE appointment_id = " + primaryKeyId;
        try {
            ResultSet rs = conn.executeQuery( sql );
            if( buildThisAppointment( conn.getSingleRow( rs ) ) ) {
                return this;
            }
        } catch( SQLException sqle ) {
            throw new DataLayerException( "Error running SELECT statement: " + sql, sqle );
        }
        return null;
    }

    @Override
    public Object fetch() throws DataLayerException {
        return fetch( appointmentId );
    }

    /**
     * Fetches a list of appointments for a given patient.
     * @param patientId The patient's Identification number.
     * @return A list of appointment objects that are directly related to the patient, or an empty list
     *         if there are no appointments for the patient.
     * @throws DataLayerException
     */
    public List<Appointment> fetchByPatient( int patientId ) throws DataLayerException {
        String sql = "SELECT * FROM " + Appointment + " WHERE patient_id = " + patientId;
        return retrieveAppointments( sql );
    }

    /**
     * Fetches a list of appointments for a given doctor.
     * @param doctorId The doctor's Identification number.
     * @return A list of appointment objects that are directly related to the doctor or an empty list
     *         if there are no appointments for the doctor.
     * @throws DataLayerException
     */
    public List<Appointment> fetchByDoctor( int doctorId ) throws DataLayerException {
        String sql = "SELECT * FROM " + Appointment + " WHERE doctor_id = " + doctorId;
        return retrieveAppointments( sql );
    }

    /**
     * Fetches a list of a appointments for a given day and time.
     * @param day Either a day string 'YYYY-MM-DD' or day and time string 'YYYY-MM-DD HH:MM'
     * @return A list of appointments on that day, or a single appointment for a specific day and time or an
     *         empty list if there are no appointments on that day.
     * @throws DataLayerException
     */
    public List<Appointment> fetchByDay( String day ) throws DataLayerException {
        String sql = "SELECT * FROM " + Appointment + " WHERE date LIKE '" + day + "%'";
        return retrieveAppointments( sql );
    }

    /**
     * Fetches a list of appointments for a given date span. A date must be formatted like: 'YYYY-MM-DD' or
     * 'YYYY-MM-DD HH:MM'.
     * @param startTime The start date/time.
     * @param endTime The end date/time (non-inclusive).
     * @return A list of appointments that fall in between <code>startTime</code> and <code>endTime</code> or
     *         an empty list if there are no appointments between those times.
     * @throws DataLayerException
     */
    public List<Appointment> fetchByDaySpan( String startTime, String endTime ) throws DataLayerException {
        String sql = "SELECT * FROM " + Appointment + " WHERE CAST(date AS DATE) "
                     + "BETWEEN '" + startTime + "'% AND '" + endTime + "%'";
        return retrieveAppointments( sql );
    }

    /**
     * Fetches a single appointment. This is retrieved by using the patient id, the doctor id, and the time of the
     * appointment. This should only fetch a single appointment because it is using very specific information.
     * @param patientId The patient's identification number who has the appointment.
     * @param doctorId The doctor's identification number who is administering the appointment.
     * @param day The exact day and time of the appointment.
     * @return The {@link Appointment} object that represents this information, or <code>null</code> if no appointment
     *         was found.
     * @throws DataLayerException
     */
    public Appointment fetchBySpecific( int patientId, int doctorId, String day ) throws DataLayerException {
        String sql = "SELECT * FROM " + Appointment + " WHERE doctor_id = " + doctorId
                     + " AND patient_id = " + patientId + " AND date LIKE '" + day + "%'";
        List<Appointment> appointments = retrieveAppointments( sql );
        if( appointments.isEmpty() ) {
            return null;
        } else {
            return retrieveAppointments( sql ).get( 0 );
        }
    }

    @Override
    public boolean post() throws DataLayerException {
        int executeUpdateQuery = -1;
        //We aren't updating the primary key of this table, so we don't want to include it in the map we receive.
        HashMap<String, String> columnsToData = asMap( false );
        for( String key : columnsToData.keySet() ) {
            boolean isNum = false;
            try {
                int parsedKey = Integer.parseInt( columnsToData.get( key ) );
                isNum = true;
            } catch( NumberFormatException ignore ) {
                //If the field we are putting into the database is a number, don't put any single quotes in
                //the sql update query. We can ignore this exception because some fields are not numbers,
                //so the expected result is a NumberFormatException, which means the update SQL query requires
                //single quotations.
            }
            String sql = "";
            if( isNum ) {
                sql = "UPDATE " + Appointment + " SET " + key + " = " + columnsToData.get( key )
                      + " WHERE appointment_id = " + appointmentId;
            } else {
                sql = "UPDATE " + Appointment + " SET " + key + " = '" + columnsToData.get( key ) + "'"
                      + " WHERE appointment_id = " + appointmentId;
            }
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
        //There are no children for an appointment, so we can just delete this appointment outright.
        String sql = "DELETE FROM " + Appointment + " WHERE appointment_id = " + appointmentId;
        try {
            int executeUpdateQuery = conn.executeUpdateQuery( sql );
            if( executeUpdateQuery > 0 ) {
                return true;
            }
        } catch( SQLException sqle ) {
            throw new DataLayerException( "Error running DELETE statement: " + sql, sqle );
        }
        return false;
    }

    @Override
    public boolean fullDelete() throws DataLayerException {
        //Since there are no children for an appointment, we can just run the delete method.
        return delete();
    }

    @Override
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
    private static String asInsertColumns() {
        return "doctor_id, patient_id, date, duration";
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

    /**
     * Retrieve a list of appointments based on a sql statement.
     * @param sql A SQL statement that may produce more than one Appointment result.
     * @return A list of appointments for the given statement.
     * @throws DataLayerException
     */
    private List<Appointment> retrieveAppointments( String sql ) throws DataLayerException {
        try {
            ResultSet rs = conn.executeQuery( sql );
            ArrayList<Appointment> appointments = new ArrayList<Appointment>();
            for( ArrayList<String> row : conn.getData( rs ) ) {
                if( buildThisAppointment( row ) ) {
                    //Need to create a new object filled with this object's data since we are adding appointments
                    //to an array by reference: if we add 'this' to the array, and then change 'this' object's
                    //values, it will change the pre-existing object in the array, thus creating duplicate entries.
                    Appointment app = new Appointment( appointmentId, doctorId, patientId, date, duration );
                    appointments.add( app );
                }
            }
            return appointments;
        } catch( SQLException sqle ) {
            throw new DataLayerException( "Error running SELECT statement: " + sql, sqle );
        }
    }
}
