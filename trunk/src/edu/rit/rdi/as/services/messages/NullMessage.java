package edu.rit.rdi.as.services.messages;

import java.io.Serializable;
import java.util.EnumMap;

/**
 * Null Message represents a null value. The null message has no tag/value pairs, and thus throws an exception if
 * any method is called on it.
 * @date Apr 30, 2011
 * @author Eric Kisner
 */
public class NullMessage extends AbstractMessage implements Serializable {

    public NullMessage() {
    }

    @Override
    public String getValue( ValidTags tag ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public void setValue( ValidTags tag, String value ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public EnumMap<ValidTags, String> getValues() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
}
