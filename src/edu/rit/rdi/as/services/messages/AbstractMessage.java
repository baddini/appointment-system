package edu.rit.rdi.as.services.messages;

import java.util.EnumMap;

/**
 * An abstract Message. This holds the tag-to-value pairs of each subclass.
 * @date Apr 30, 2011
 * @author Eric Kisner
 */
public abstract class AbstractMessage implements Message {

    private static final String TAG_TO_VAL_DELIM = "=>";
    private static final String TAG_DELIM = "|";
    protected EnumMap<ValidTags, String> tagsToValues;

    public AbstractMessage() {
        tagsToValues = new EnumMap<ValidTags, String>( ValidTags.class );
    }

    public String getValue( ValidTags tag ) {
        if( tagsToValues.containsKey( tag ) ) {
            return tagsToValues.get( tag );
        }
        return null;
    }

    public void setValue( ValidTags tag, String value ) {
        tagsToValues.put( tag, value );
    }

    public EnumMap<ValidTags, String> getValues() {
        return tagsToValues;
    }

    public String serialize() {
        StringBuilder build = new StringBuilder();
        for( ValidTags tag : tagsToValues.keySet() ) {
            build.append( tag.name() );
            build.append( TAG_TO_VAL_DELIM );
            build.append( tagsToValues.get( tag ) );
            build.append( TAG_DELIM );
        }
        //Remove the last delimiter
        build.deleteCharAt( build.length() );
        return build.toString();
    }
}
