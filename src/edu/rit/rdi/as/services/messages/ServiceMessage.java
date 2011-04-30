package edu.rit.rdi.as.services.messages;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Service Message represents a successful message after calling a method on the server. The service message will
 * contain tag/value pairs depending on which method the client called.
 * @date Apr 30, 2011
 * @author Eric Kisner
 */
public class ServiceMessage extends AbstractMessage implements Serializable {

    public ServiceMessage() {
    }

    public String getValue( String key ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public void setValue( String key, String value ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public HashMap<String, String> getValues() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
}
