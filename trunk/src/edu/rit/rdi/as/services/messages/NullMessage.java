package edu.rit.rdi.as.services.messages;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;

/**
 * Null Message represents a null value. The null message has no tag/value pairs, and thus returns empty
 * for all methods.
 * @date Apr 30, 2011
 * @author Eric Kisner
 */
public class NullMessage extends AbstractMessage implements Serializable {

    public NullMessage() {
    }

    @Override
    public String getValue( ValidTag tag ) {
        return new String();
    }

    @Override
    public void setValue( ValidTag tag, String value ) {
        //Does not do anything
    }

    @Override
    public EnumMap<ValidTag, String> getValues() {
        return new EnumMap<ValidTag, String>( ValidTag.class );
    }

    @Override
    public String serialize() {
        return new String();
    }
}
