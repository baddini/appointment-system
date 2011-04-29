package edu.rit.rdi.as.services;

import edu.rit.rdi.as.database.DatabaseConnection;
import edu.rit.rdi.as.utilities.ExceptionUtils;
import javax.swing.JOptionPane;
import org.apache.xmlrpc.WebServer;

/**
 * Runs the XML RPC Service for the Appointment System.
 * @date Apr 27, 2011
 * @author Eric Kisner
 */
public class AppointmentSystemRPCService {

    private static int port = 8888;

    public static void main( String[] args ) {
        String portStr = JOptionPane.showInputDialog( null, "What port would you like to run the server on?" );
        if( portStr == null ) {
            System.err.println( "No input. Exiting program." );
            System.exit( 0 );
        }

        try {
            port = Integer.parseInt( portStr );
        } catch( NumberFormatException nfe ) {
            //Log the error, but the default will be port 8888
            ExceptionUtils.handleException( nfe );
        }

        try {
            //Initial load for DatabaseConnection instance - to cut down on time when the user actually
            //sees the interface.
            DatabaseConnection.instance();
            WebServer server = new WebServer( port );
            server.addHandler( "appointment_service", new AppointmentHandler() );
            server.start();
            System.out.println( "Server started on port " + port + "." );
        } catch( Exception e ) {
            ExceptionUtils.handleException( e );
            System.exit( -1 );
        }
    }
}
