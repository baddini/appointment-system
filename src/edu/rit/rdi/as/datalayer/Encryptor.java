package edu.rit.rdi.as.datalayer;

import edu.rit.rdi.as.utilities.logging.Logging;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Encryptor class handles the hashing of passwords coming from and going into the database.
 */
@SuppressWarnings( "StaticNonFinalUsedInInitialization" )
public class Encryptor {

    private static MessageDigest d = null;

    static {
        try {
            d = MessageDigest.getInstance( "SHA-1" );
            d.reset();
        } catch( NoSuchAlgorithmException nsae ) {
            Logging.log( "Error initializing encryption algorithm 'SHA1': \n" + nsae.getMessage() + "\n"
                         + "Terminating program.", Encryptor.class );
            System.exit( 1 );
        }
    }

    /**
     * You should only be able to access the computeHash method.
     */
    private Encryptor() { }

    /**
     * Creates a hash of the given string (in SHA1).
     * @param str The string to hash using the SHA1 algorithm.
     * @return The hashed string.
     */
    public static String computeHash( String str ) {
        d.update( str.getBytes() );
        return byteArrayToHex( d.digest() );
    }

    /**
     * Returns a byte array as a hex string.
     */
    private static String byteArrayToHex( byte[] b ) {
        StringBuilder sb = new StringBuilder( b.length * 2 );
        for( int i = 0; i < b.length; i++ ) {
            int v = b[i] & 0xff;
            if( v < 16 ) {
                sb.append( '0' );
            }
            sb.append( Integer.toHexString( v ) );
        }
        return sb.toString().toUpperCase();
    }
}
