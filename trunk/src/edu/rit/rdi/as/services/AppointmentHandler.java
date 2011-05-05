package edu.rit.rdi.as.services;

import edu.rit.rdi.as.datalayer.Appointment;
import edu.rit.rdi.as.datalayer.Security;
import edu.rit.rdi.as.exceptions.DataLayerException;
import edu.rit.rdi.as.services.messages.ErrorMessage;
import edu.rit.rdi.as.services.messages.Message;
import edu.rit.rdi.as.services.messages.NullMessage;
import edu.rit.rdi.as.services.messages.ServiceMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static edu.rit.rdi.as.services.messages.Message.ValidTag.*;
import static edu.rit.rdi.as.utilities.ExceptionUtils.*;

/**
 * Appointment Handler handles calls to the REST Service that hosts the Appointment Request System.
 * These methods are exposed through REST as a way to communicate with multiple client interfaces.
 *
 * The handler passes back {@link Message} objects when a client executes a service method.
 * @date Apr 27, 2011
 * @author Eric Kisner
 */
public class AppointmentHandler {

    public AppointmentHandler() {
    }

    /**
     * Logs a doctor or patient into the Appointment Request System. The username and password for doctors
     * and patients are pre-assigned. This is because, for this project, we didn't want to expand the scope
     * to handle new patients or new doctors trying to use the service. Instead, doctors and patients will
     * already have their username and passwords given to them.
     * @param username The doctor or patient username.
     * @param password The doctor or patient password. The password should be hashed prior to calling this.
     * @return A {@link Message} that represents whether this user is a doctor or patient, and their Identification
     *         number. {@link NullMessage} will be passed back if the username/password combination is
     *         incorrect/invalid. {@link ErrorMessage} will be passed back if an error occurred.
     *         A valid {@link ServiceMessage} will have the tag "PATIENT" or "DOCTOR" and the value will be that
     *         person's identification number.
     */
    public Message login( String username, String password ) {
        Message ret = new NullMessage();
        Security security = new Security();
        int id = Integer.MIN_VALUE;

        try {
            id = security.loginDoctor( username, password );
            if( id != -1 ) {
                ret = new ServiceMessage();
                ret.setValue( DOCTOR, String.valueOf( id ) );
                return ret;
            }
            id = security.loginPatient( username, password );
            if( id != -1 ) {
                ret = new ServiceMessage();
                ret.setValue( PATIENT, String.valueOf( id ) );
                return ret;
            }
        } catch( DataLayerException dle ) {
            ret = new ErrorMessage();
            ret.setValue( ERROR, dle.getMessage() + "\n" + stackTraceAsString( dle ) );
            ret.setValue( DISPLAY_ERROR, "There was an error trying to log in to the service." );
            return ret;
        }

        return ret;
    }

    /**
     * Gets all appointments for a patient.
     * @param patientId The patient's Identification number. This will be held by the client once the client logs
     *                  into the service.
     * @return A list of {@link Message}s that represents a list of appointments for the specified patient.
     *         {@link NullMessage} will be passed back if the patient does not have any appointments.
     *         {@link ErrorMessage} will be passed back if an error occurred.
     *         A valid list of {@link ServiceMessage}s will have a tag "APPOINTMENT" that will contain a String that is
     *         formatted like such: DOCTOR_ID,TIME_STRING.
     *         This method will only return a list of size greater than 1 if there are more than one appointment for
     *         the patient.
     */
    public List<Message> getPatientAppointments( int patientId ) {
        Message m = null;
        Appointment appointment = new Appointment();
        try {
            List<Appointment> appointments = appointment.fetchByPatient( patientId );
            if( appointments.isEmpty() ) {
                m = new NullMessage();
            } else {
                List<Message> ret = new ArrayList<Message>();
                for( int i = 0; i < appointments.size(); i++ ) {
                    m = new ServiceMessage();
                    Appointment a = appointments.get( i );
                    m.setValue( APPOINTMENT, a.getDoctorId() + "," + a.getDate() );
                    ret.add( m );
                }
                return ret;
            }
        } catch( DataLayerException dle ) {
            m = new ErrorMessage();
            m.setValue( ERROR, dle.getMessage() + "\n" + stackTraceAsString( dle ) );
            m.setValue( DISPLAY_ERROR, "There was an error trying to get appointments for the patient." );
        }
        return Collections.singletonList( m );
    }

    /**
     * Gets all appointments for a doctor.
     * @param doctorId The doctor's Identification number. This will be held by the client once the doctor logs into
     *                 the service.
     * @return A list of {@link Message}s that represents a list of appointments for the specified doctor.
     *         {@link NullMessage} will be passed back if the doctor does not have any scheduled appointments.
     *         {@link ErrorMessage} will be passed back if an error occurred.
     *         A valid list of {@link ServiceMessage}s will have a tag "APPOINTMENT" that will contain a String that is
     *         formatted like such:  PATIENT_ID,TIME_STRING
     *         This method will only return a list of size greater than 1 if there are more than one appointment for
     *         the doctor.
     */
    public List<Message> getDoctorAppointments( int doctorId ) {
        Message m = null;
        Appointment appointment = new Appointment();
        try {
            List<Appointment> appointments = appointment.fetchByDoctor( doctorId );
            if( appointments.isEmpty() ) {
                m = new NullMessage();
            } else {
                List<Message> ret = new ArrayList<Message>();
                for( int i = 0; i < appointments.size(); i++ ) {
                    m = new ServiceMessage();
                    Appointment a = appointments.get( i );
                    m.setValue( APPOINTMENT, a.getPatientId() + "," + a.getDate() );
                    ret.add( m );
                }
                return ret;
            }
        } catch( DataLayerException dle ) {
            m = new ErrorMessage();
            m.setValue( ERROR, dle.getMessage() + "\n" + stackTraceAsString( dle ) );
            m.setValue( DISPLAY_ERROR, "There was an error trying to get appointments for the doctor." );
        }
        return Collections.singletonList( m );
    }

    /**
     * Get a certain appointment for a specified date.
     * @param timeframe The time that we want to look for an appointment. A valid timeframe string will look like:
     *                  "Year-Month-Day" or "Year-Month-Day Hours:Minutes"
     * @return A list of {@link Message}s that represents a list of appointments for the specified day.
     *         {@link NullMessage} will be passed back if there are no schedules appointments for the day.
     *         {@link ErrorMessage} will be passed back if an error occurred.
     *         A valid list of {@link ServiceMessage}s will have a tag "APPOINTMENT" that will contain a String that is
     *         formatted like such:  PATIENT_ID,DOCTOR_ID
     *         This method will only return a list of size greater than 1 if there are more than one appointment for
     *         the day.
     */
    public List<Message> getAppointmentsByDay( String timeframe ) {
        Message m = null;
        Appointment appointment = new Appointment();
        try {
            List<Appointment> appointments = appointment.fetchByDay( timeframe );
            if( appointments.isEmpty() ) {
                m = new NullMessage();
            } else {
                List<Message> ret = new ArrayList<Message>();
                for( int i = 0; i < appointments.size(); i++ ) {
                    m = new ServiceMessage();
                    Appointment a = appointments.get( i );
                    m.setValue( APPOINTMENT, a.getPatientId() + "," + a.getDoctorId() );
                    ret.add( m );
                }
                return ret;
            }
        } catch( DataLayerException dle ) {
            m = new ErrorMessage();
            m.setValue( ERROR, dle.getMessage() + "\n" + stackTraceAsString( dle ) );
            m.setValue( DISPLAY_ERROR, "There was an error trying to get appointments for the day." );
        }
        return Collections.singletonList( m );
    }

    /**
     * Get a list of appointments for a given time frame.
     * Valid timeframe strings will look like: "Year-Month-Day" or "Year-Month-Day Hours:Minutes"
     * @param startTimeframe The start, inclusive, of when we want to start looking for appointments.
     * @param endTimeframe The end, inclusive, of when we want to stop looking for appointments.
     * @return A list of {@link Message}s that represents a list of appointments for the specified time frame.
     *         {@link NullMessage} will be passed back if there are no schedules appointments for the time frame.
     *         {@link ErrorMessage} will be passed back if an error occurred.
     *         A valid list of {@link ServiceMessage}s will have a tag "APPOINTMENT" that will contain a String that is
     *         formatted like such:  "PATIENT_ID,DOCTOR_ID,Year-Day-Month Hours:Minutes:Seconds"
     *         This method will only return a list of size greater than 1 if there are more than one appointment for
     *         the time frame.
     */
    public List<Message> getAppointmentsByTimespan( String startTimeframe, String endTimeframe ) {
        Message m = null;
        Appointment appointment = new Appointment();
        try {
            List<Appointment> appointments = appointment.fetchByDaySpan( startTimeframe, endTimeframe );
            if( appointments.isEmpty() ) {
                m = new NullMessage();
            } else {
                List<Message> ret = new ArrayList<Message>();
                for( int i = 0; i < appointments.size(); i++ ) {
                    m = new ServiceMessage();
                    Appointment a = appointments.get( i );
                    m.setValue( APPOINTMENT, a.getPatientId() + "," + a.getDoctorId() + "," + a.getDate() );
                    ret.add( m );
                }
                return ret;
            }
        } catch( DataLayerException dle ) {
            m = new ErrorMessage();
            m.setValue( ERROR, dle.getMessage() + "\n" + stackTraceAsString( dle ) );
            m.setValue( DISPLAY_ERROR, "There was an error trying to get appointments for the time frame." );
        }
        return Collections.singletonList( m );
    }

    /**
     * Change an appointment's time period, the doctor overseeing the appointment, or the patient who has the
     * appointment.
     * @param appointmentString Information string for an appointment. This string must be formatted like:
     *                          ORIGINAL:Doctor_name,Patient_fName,Patient_lName,time|NEW:Doctor_name=[name]...
     * @return A {@link Message} that represents whether this change was successful or not. {@link NullMessage} will
     *         be passed back if there are no appointments found for the given doctor and patient, or if the appointment
     *         is for a different patient. {@link ErrorMessage} will be passed back if an error occurred.
     *         A valid {@link ServiceMessage} will have the following tags with the NEW information filled in:
     *         "DOCTOR NAME", "PATIENT NAME", "TIME"
     */
    public Message changeAppointment( String appointmentString ) {
        return null;
    }

    /**
     * Deletes an appointment.
     * @param appointmentString Information string for an appointment. This string must be formatted like:
     *                          ORIGINAL:Doctor_name,Patient_fName,Patient_lName,time|NEW:Doctor_name=[name]...
     * @return A {@link Message} that represents whether this change was successful or not. {@link NullMessage} will
     *         be passed back if there are no appointments found for the given doctor and patient, or if the appointment
     *         is for a different patient/doctor. {@link ErrorMessage} will be passed back if an error occurred.
     *         A valid {@link ServiceMessage} will have the following tags with the deleted appointment information
     *         filled in: "DOCTOR NAME", "PATIENT NAME", "TIME"
     */
    public Message deleteAppointment( String appointmentString ) {
        return null;
    }
}
