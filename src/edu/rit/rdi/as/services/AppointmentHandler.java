package edu.rit.rdi.as.services;

/**
 * Appointment Handler handles calls to the XML RPC Service that hosts the Appointment Request System.
 * These methods are exposed through XML RPC as a way to communicate with multiple client interfaces.
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
     * @return A String that represents whether this user is a doctor or patient, and their Identification
     *         number. Null will be passed back if the username/password combination is incorrect/invalid.
     *         A validly formatted return message will look like: PATIENT:ID#
     */
    public String login( String username, String password ) {
        return null;
    }
}
