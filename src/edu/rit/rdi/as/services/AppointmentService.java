package edu.rit.rdi.as.services;

import edu.rit.rdi.as.services.messages.Message;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author Eric Kisner
 */
@Path( "appointmentservice" )
public class AppointmentService {

    /**
     * A '~' that distinguishes the end of a Message string.
     */
    private static final String MESSAGE_DELIM = "~";
    private AppointmentHandler handler;
    @Context
    private UriInfo context;

    /** Creates a new instance of AppointmentService */
    public AppointmentService() {
        handler = new AppointmentHandler();
    }

    /**
     * @see AppointmentHandler#login(java.lang.String, java.lang.String)
     */
    @GET
    @Produces( "text/plain" )
    @Consumes( "text/plain" )
    @Path( "Login" )
    public String login( @QueryParam( "username" ) String username,
                          @QueryParam( "password" ) String password ) {
        return handler.login( username, password ).serialize();
    }

    /**
     * @see AppointmentHandler#getPatientName(int) 
     */
    @GET
    @Produces( "text/plain" )
    @Consumes( "text/plain" )
    @Path( "PatientName" )
    public String getPatientName( @QueryParam( "patientId" ) int patientId ) {
        return handler.getPatientName( patientId ).serialize();
    }

    /**
     * @see AppointmentHandler#getPatientAppointments(int)
     */
    @GET
    @Produces( "text/plain" )
    @Consumes( "text/plain" )
    @Path( "PatientAppointments" )
    public String getPatientAppointments( @QueryParam( "patientId" ) int patientId ) {
        List<Message> messages = handler.getPatientAppointments( patientId );
        return getMessagesString( messages );
    }

    /**
     * @see AppointmentHandler#getDoctorAppointments(int)
     */
    @GET
    @Produces( "text/plain" )
    @Consumes( "text/plain" )
    @Path( "DoctorAppointments" )
    public String getDoctorAppointments( @QueryParam( "doctorId" ) int doctorId ) {
        List<Message> messages = handler.getDoctorAppointments( doctorId );
        return getMessagesString( messages );
    }

    /**
     * @see AppointmentHandler#getAppointmentsByDay(java.lang.String)
     */
    @GET
    @Produces( "text/plain" )
    @Consumes( "text/plain" )
    @Path( "DayAppointments" )
    public String getAppointmentsByDay( @QueryParam( "timeframe" ) String timeframe ) {
        List<Message> messages = handler.getAppointmentsByDay( timeframe );
        return getMessagesString( messages );
    }

    /**
     * @see AppointmentHandler#getAppointmentsByTimespan(java.lang.String, java.lang.String)
     */
    @GET
    @Produces( "text/plain" )
    @Consumes( "text/plain" )
    @Path( "TimespanAppointments" )
    public String getAppointmentsByTimespan( @QueryParam( "startTime" ) String startTimeframe,
                                             @QueryParam( "endTime" ) String endTimeframe ) {
        List<Message> messages = handler.getAppointmentsByTimespan( startTimeframe, endTimeframe );
        return getMessagesString( messages );
    }

    /**
     * @see AppointmentHandler#changeAppointment(java.lang.String)
     */
    @GET
    @Produces( "text/plain" )
    @Consumes( "text/plain" )
    @Path( "ChangeAppointment" )
    public String changeAppointment( @QueryParam( "appointment" ) String appointmentString ) {
        return handler.changeAppointment( appointmentString ).serialize();
    }

    /**
     * @see AppointmentHandler#deleteAppointment(java.lang.String)
     */
    @GET
    @Produces( "text/plain" )
    @Consumes( "text/plain" )
    @Path( "DeleteAppointment" )
    public String deleteAppointment( @QueryParam( "appointment" ) String appointmentString ) {
        return handler.deleteAppointment( appointmentString ).serialize();
    }

    /**
     * @see AppointmentHandler#deleteAppointment(java.lang.String)
     */
    @GET
    @Produces( "text/plain" )
    @Consumes( "text/plain" )
    @Path( "AddAppointment" )
    public String addAppointment( @QueryParam( "appointment" ) String appointmentString ) {
        return handler.addAppointment( appointmentString ).serialize();
    }

    /**
     * Builds a string from a list of messages.
     * @param messages The list of messages we want to serialize.
     * @return A string that contains all string representations of each Message in <code>messages</code>.
     *         Each Message string is separated by a {@link AppointmentService#MESSAGE_DELIM}.
     */
    private String getMessagesString( List<Message> messages ) {
        if( messages.size() == 1 ) {
            return messages.get( 0 ).serialize();
        } else {
            StringBuilder build = new StringBuilder();
            for( Message m : messages ) {
                build.append( m.serialize() );
                build.append( MESSAGE_DELIM );
            }
            build.deleteCharAt( build.lastIndexOf( MESSAGE_DELIM ) );
            return build.toString();
        }
    }
}
