package edu.rit.rdi.as.services.messages;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Null Message represents a null value. The null message has no tag/value pairs, and thus throws an exception if
 * any method is called on it.
 * @date Apr 30, 2011
 * @author Eric Kisner
 */
public class NullMessage extends AbstractMessage implements Serializable {

    public NullMessage() {
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
