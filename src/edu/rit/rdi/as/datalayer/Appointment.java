package edu.rit.rdi.as.datalayer;

import java.sql.SQLException;
import java.util.HashMap;

/**
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

    public boolean put() throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public Object fetch( int primaryKeyId ) throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public Object fetch() throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean post() throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean delete() throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean fullDelete() throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public HashMap asMap( boolean includePrimaryKey ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
}
