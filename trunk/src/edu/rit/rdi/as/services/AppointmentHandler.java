package edu.rit.rdi.as.services;

import edu.rit.rdi.as.services.messages.ErrorMessage;
import edu.rit.rdi.as.services.messages.Message;
import edu.rit.rdi.as.services.messages.NullMessage;
import edu.rit.rdi.as.services.messages.ServiceMessage;

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
     * and patients is pre-assigned. This is because, for this project, we didn't want to expand the scope
     * to handle new patients or new doctors trying to use the service. Instead, doctors and patients will
     * already have their username and passwords given to them.
     * @param username The doctor or patient username.
     * @param password The doctor or patient password.
     * @return A {@link Message} that represents whether this user is a doctor or patient, and their Identification
     *         number. {@link NullMessage} will be passed back if the username/password combination is
     *         incorrect/invalid. {@link ErrorMessage} will be passed back if an error occurred.
     *         A valid {@link ServiceMessage} will have the tag "PATIENT" or "DOCTOR" and the value will be that
     *         person's identification number.
     */
    public Message login( String username, String password ) {
        return null;
    }

    /**
     * Gets all appointments for a patient.
     * @param patientId The patient's Identification number. This will be held by the client once the client logs
     *                  into the service.
     * @return A {@link Message} that represents a list of appointments for the specified patient. {@link NullMessage}
     *         will be passed back if the patient does not have any appointments. {@link ErrorMessage} will be passed
     *         back if an error occurred.
     *         A valid {@link ServiceMessage} will have a tag "APPOINTMENT_#" that will contain a String that is
     *         formatted like such: DOCTOR_ID,TIME_STRING
     */
    public Message getAppointments( int patientId ) {
        return null;
    }

    /**
     * Gets all appointments for a doctor.
     * @param doctorId The doctor's Identification number. This will be held by the client once the doctor logs into
     *                 the service.
     * @param garbage Since there is a {@link AppointmentHandler#getAppointments(int)}, the signature cannot be the
     *                same, so we can pass in a garbage string.
     * @return A {@link Message} that represents a list of appointments for the specified doctor. {@link NullMessage}
     *         will be passed back if the doctor does not have any appointments. {@link ErrorMessage} will be passed
     *         back if an error occurred.
     *         A valid {@link ServiceMessage} will have a tag "APPOINTMENT_#" that will contain a String that is
     *         formatted like such: PATIENT_ID,TIME_STRING
     */
    public Message getApppointments( int doctorId, String garbage ) {
        return null;
    }

    /**
     * Get a certain appointment for a specified time and date.
     * @param timeframe The time that we want to look for an appointment. A valid timeframe string will look like:
     *                  "Day-Month-Year Hours-Minutes-Seconds"
     * @return A {@link Message} that represents an appointment for the specified timeframe. {@link NullMessage}
     *         will be passed back if there are no appointments for the given time. {@link ErrorMessage} will be passed
     *         back if an error occurred.
     *         A valid {@link ServiceMessage} will have a tag "APPOINTMENT" that will contain a String that is
     *         formatted like such: DOCTOR_ID,PATIENT_ID
     */
    public Message getAppointment( String timeframe ) {
        return null;
    }

    /**
     * Get a list of appointments for a given time frame.
     * Valid timeframe strings will look like: "Day-Month-Year Hours-Minutes-Seconds"
     * @param startTimeframe The start, inclusive, of when we want to start looking for appointments.
     * @param endTimeframe The end, inclusive, of when we want to stop looking for appointments.
     * @return A {@link Message} that represents a list of appointments for the specified timeframe. {@link NullMessage}
     *         will be passed back if there are no appointments for the given frame. {@link ErrorMessage} will be passed
     *         back if an error occurred.
     *         A valid {@link ServiceMessage} will have a tag "APPOINTMENT_#" that will contain a String that is
     *         formatted like such: "DOCTOR_ID,PATIENT_ID,Day-Month-Year Hours-Minutes-Seconds"
     */
    public Message getAppointments( String startTimeframe, String endTimeframe ) {
        return null;
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
