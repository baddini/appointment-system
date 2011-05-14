package edu.rit.rdi.as.services;

import edu.rit.rdi.as.datalayer.Appointment;
import edu.rit.rdi.as.datalayer.Doctor;
import edu.rit.rdi.as.datalayer.Patient;
import edu.rit.rdi.as.datalayer.Security;
import edu.rit.rdi.as.exceptions.DataLayerException;
import edu.rit.rdi.as.services.messages.AbstractMessage;
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
        Message m = new NullMessage();
        Security security = new Security();
        int id = Integer.MIN_VALUE;

        try {
            id = security.loginDoctor( username, password );
            if( id != -1 ) {
                m = new ServiceMessage();
                m.setValue( DOCTOR, String.valueOf( id ) );
                return m;
            }
            id = security.loginPatient( username, password );
            if( id != -1 ) {
                m = new ServiceMessage();
                m.setValue( PATIENT, String.valueOf( id ) );
                return m;
            }
        } catch( DataLayerException dle ) {
            m = new ErrorMessage();
            m.setValue( ERROR, dle.getMessage() + "\n" + stackTraceAsString( dle ) );
            m.setValue( DISPLAY_ERROR, "There was an error trying to log in to the service." );
        }

        return m;
    }

    /**
     * Gets a patient's first and last name based on their unique identification number.
     * @param patientId The {@link Patient}'s identification number.
     * @return A {@link Message} that represents whether the fetch was successful. A successful fetch will return
     *         a {@link ServiceMessage} with a tag of "PATIENT" and the value will be formatted like:
     *         "LastName, FirstName." A {@link NullMessage} will be returned if the fetch did not return a
     *         {@link Patient}. A {@link ErrorMessage} will be returned if the fetch produced an exception.
     */
    public Message getPatientName( int patientId ) {
        Message m = null;
        try {
            Patient patient = new Patient( patientId );
            patient = (Patient) patient.fetch();
            if( patient == null ) {
                m = new NullMessage();
            } else {
                m = new ServiceMessage();
                m.setValue( PATIENT, patient.getLastName() + ", " + patient.getFirstName() );
            }
        } catch( DataLayerException dle ) {
            m = new ErrorMessage();
            m.setValue( ERROR, dle.getMessage() + "\n" + stackTraceAsString( dle ) );
            m.setValue( DISPLAY_ERROR, "There was an error trying to get the patient's name." );
        }

        return m;
    }

    /**
     * Gets a doctor's first and last name based on their unique identification number.
     * @param doctorId The {@link Doctor}'s identification number.
     * @return A {@link Message} that represents whether the fetch was successful. A successful fetch will return
     *         a {@link ServiceMessage} with a tag of "DOCTOR" and the value will be formatted like:
     *         "LastName, FirstName." A {@link NullMessage} will be returned if the fetch did not return a
     *         {@link Doctor}. A {@link ErrorMessage} will be returned if the fetch produced an exception.
     */
    public Message getDoctorName( int doctorId ) {
        Message m = null;
        try {
            Doctor doctor = new Doctor( doctorId );
            doctor = (Doctor) doctor.fetch();
            if( doctor == null ) {
                m = new NullMessage();
            } else {
                m = new ServiceMessage();
                m.setValue( DOCTOR, doctor.getLastName() + ", " + doctor.getFirstName() );
            }
        } catch( DataLayerException dle ) {
            m = new ErrorMessage();
            m.setValue( ERROR, dle.getMessage() + "\n" + stackTraceAsString( dle ) );
            m.setValue( DISPLAY_ERROR, "There was an error trying to get the doctor's name." );
        }

        return m;
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
     *                          <code>Doctor_id,Patient_id,time|Doctor_id,Patient_id,time,duration</code>
     *                          where pre-delimiter is the original appointment data, and post-delimiter is new
     *                          appointment data.
     * @return A {@link Message} that represents whether this change was successful or not. 
     *         <br/>{@link NullMessage} will be passed back if there are no appointments found for the given
     *         doctor and patient, or if the appointment is for a different patient, or if the update was unsuccessful.
     *         <br/>{@link ErrorMessage} will be passed back if an error occurred.
     *         <br/>A valid {@link ServiceMessage} will have an Appointment tag with the NEW information filled in:
     *         "APPOINTMENT_ID,PATIENT_ID,DOCTOR_ID,Year-Day-Month Hours:Minutes:Seconds,duration"
     */
    public Message changeAppointment( String appointmentString ) {
        Message m = null;
        //Split the parameter into OLD and NEW appointment strings.
        String[] split = appointmentString.split( AbstractMessage.TAG_DELIM );
        if( split.length > 2 ) {
            m = new NullMessage();
        } else {
            try {
                //Get the OLD appointment
                Appointment oldAppointment = parseAppointment( split[0], false );
                //Get NEW appointment values
                Appointment newAppointment = parseAppointment( split[1], true );
                //Set the new appointment's id number to the same as the old appointment.
                newAppointment.setAppointmentId( oldAppointment.getAppointmentId() );
                //Update the old appointment's values with the new appointment's values.
                boolean success = newAppointment.post();
                if( success ) {
                    m = new ServiceMessage();
                    m.setValue( APPOINTMENT, newAppointment.getAppointmentId()
                                             + "," + newAppointment.getPatientId()
                                             + "," + newAppointment.getDoctorId()
                                             + "," + newAppointment.getDate()
                                             + "," + newAppointment.getDuration() );
                } else {
                    m = new NullMessage();
                }
            } catch( DataLayerException dle ) {
                m = new ErrorMessage();
                m.setValue( ERROR, dle.getMessage() + "\n" + stackTraceAsString( dle ) );
                m.setValue( DISPLAY_ERROR, "There was an error trying to update an appointment's information." );
            }
        }
        return m;
    }

    /**
     * Delete an appointment.
     * @param appointmentString Information string for an appointment. This string must be formatted like:
     *                          <code>Doctor_id,Patient_id,time</code>.
     * @return A {@link Message} that represents whether this delete was successful or not.
     *         <br/>{@link NullMessage} will be passed back if there are no appointments found for the given
     *         doctor and patient, or if the delete was unsuccessful.
     *         <br/>{@link ErrorMessage} will be passed back if an error occurred.
     *         <br/>A valid {@link ServiceMessage} will have an Appointment tag with the deleted information filled in:
     *         "APPOINTMENT_ID,PATIENT_ID,DOCTOR_ID,Year-Day-Month Hours:Minutes:Seconds,duration"
     */
    public Message deleteAppointment( String appointmentString ) {
        Message m = null;

        try {
            //Get the appointment to delete
            Appointment toDelete = parseAppointment( appointmentString, false );
            boolean success = toDelete.delete();
            if( success ) {
                m = new ServiceMessage();
                m.setValue( APPOINTMENT, toDelete.getAppointmentId()
                                         + "," + toDelete.getPatientId()
                                         + "," + toDelete.getDoctorId()
                                         + "," + toDelete.getDate()
                                         + "," + toDelete.getDuration() );
            } else {
                m = new NullMessage();
            }
        } catch( DataLayerException dle ) {
            m = new ErrorMessage();
            m.setValue( ERROR, dle.getMessage() + "\n" + stackTraceAsString( dle ) );
            m.setValue( DISPLAY_ERROR, "There was an error trying to update an appointment's information." );
        }

        return m;
    }

    /**
     * Add an appointment.
     * @param appointmentString Information string for an appointment. This string must be formatted like:
     *                          <code>Doctor_id,Patient_id,time,duration</code>.
     * @return A {@link Message} that represents whether this add was successful or not.
     *         <br/>{@link NullMessage} will be passed back if the add was unsuccessful.
     *         <br/>{@link ErrorMessage} will be passed back if an error occurred.
     *         <br/>A valid {@link ServiceMessage} will have an Appointment tag with the added information filled in:
     *         "APPOINTMENT_ID,PATIENT_ID,DOCTOR_ID,Year-Day-Month Hours:Minutes:Seconds,duration"
     */
    public Message addAppointment( String appointmentString ) {
        Message m = null;
        
        try {
            Appointment appointment = parseAppointment( appointmentString, true );
            boolean success = appointment.put();
            if( success ) {
                m = new ServiceMessage();
                m.setValue( APPOINTMENT, appointment.getAppointmentId()
                                         + "," + appointment.getPatientId()
                                         + "," + appointment.getDoctorId()
                                         + "," + appointment.getDate()
                                         + "," + appointment.getDuration() );
            } else {
                m = new NullMessage();
            }
        } catch( DataLayerException dle ) {
            m = new ErrorMessage();
            m.setValue( ERROR, dle.getMessage() + "\n" + stackTraceAsString( dle ) );
            m.setValue( DISPLAY_ERROR, "There was an error trying to update an appointment's information." );
        }
        return m;
    }

    /**
     * Parses an appointment string into an {@link Appointment} object.
     * @param appointmentString The appointment string to parse an {@link Appointment} from. Valid appointment strings
     *                          should only include the doctorId, the patientId, the date/time, and the duration, in
     *                          that specific order and delimited by <code>,</code>. <code>appointmentString</code>
     *                          does not have to include the duration.
     * @param hasDuration Flag to say whether <code>appointmentString</code> has duration or not.
     * @return An {@link Appointment} object after parsing <code>appointmentString</code>.
     * @throws DataLayerException If <code>appointmentString</code> does not contain the correct amount of data, or
     *                            if the doctor id, patient id, or duration are not numbers.
     */
    private Appointment parseAppointment( String appointmentString, boolean hasDuration ) throws DataLayerException {
        try {
            String[] appointmentData = appointmentString.split( "," );
            int doctorId = Integer.parseInt( appointmentData[0] );
            int patientId = Integer.parseInt( appointmentData[1] );
            String day = appointmentData[2];
            if( hasDuration ) {
                int duration = Integer.parseInt( appointmentData[3] );
                return new Appointment( doctorId, patientId, day, duration );
            } else {
                return new Appointment().fetchBySpecific( patientId, doctorId, day );
            }
        } catch( NumberFormatException nfe ) {
            throw new DataLayerException( "There was a number in the appointment string at an incorrect location."
                                          + "\n" + appointmentString, nfe );
        } catch( ArrayIndexOutOfBoundsException oobe ) {
            throw new DataLayerException( "The appointment string does not contain the correct amount of data."
                                          + "\n" + appointmentString, oobe );
        }
    }
}
