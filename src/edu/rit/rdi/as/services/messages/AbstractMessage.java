package edu.rit.rdi.as.services.messages;

import java.util.EnumMap;

/**
 * An abstract Message. This holds the tag-to-value pairs of each subclass.
 * @date Apr 30, 2011
 * @author Eric Kisner
 */
public abstract class AbstractMessage implements Message {

    private static final String TAG_TO_VAL_DELIM = "=>";
    public static final String TAG_DELIM = "|";
    protected EnumMap<ValidTag, String> tagsToValues;

    public AbstractMessage() {
        tagsToValues = new EnumMap<ValidTag, String>( ValidTag.class );
    }

    @Override
    public String getValue( ValidTag tag ) {
        if( tagsToValues.containsKey( tag ) ) {
            return tagsToValues.get( tag );
        }
        return null;
    }

    @Override
    public void setValue( ValidTag tag, String value ) {
        tagsToValues.put( tag, value );
    }

    @Override
    public EnumMap<ValidTag, String> getValues() {
        return tagsToValues;
    }

    @Override
    public String serialize() {
        StringBuilder build = new StringBuilder();
        for( ValidTag tag : tagsToValues.keySet() ) {
            build.append( tag.name() );
            build.append( TAG_TO_VAL_DELIM );
            build.append( tagsToValues.get( tag ) );
            build.append( TAG_DELIM );
        }
        //Remove the last delimiter
        build.deleteCharAt( build.lastIndexOf( TAG_DELIM ) );
        return build.toString();
    }
}
