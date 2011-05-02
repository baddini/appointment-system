package edu.rit.rdi.as.services.messages;

import java.util.EnumMap;

/**
 * A Message object is an information holder for passing back messages between a server and client through a REST
 * service. The message will contain tags (specified by the method we are calling) and its value.
 * @date April 30, 2011
 * @author Eric Kisner
 */
public interface Message {

    /**
     * Get the value of a tag.
     * @param tag The tag we are looking for in this message.
     * @return The value of the tag, or null if the tag doesn't exist.
     */
    public String getValue( ValidTags tag );

    /**
     * Puts a tag and its value into the message.
     * @param tag The tag to store in the message.
     * @param value The tag's value.
     */
    public void setValue( ValidTags tag, String value );

    /**
     * Returns all tag/value pairs that are contained in this message.
     * @return The tag-to-value mapping.
     */
    public EnumMap<ValidTags, String> getValues();

    public enum ValidTags {
        DOCTOR, PATIENT, APPOINTMENT, ERROR, DISPLAY_ERROR
    }
}
