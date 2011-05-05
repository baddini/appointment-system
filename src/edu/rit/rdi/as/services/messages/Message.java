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

    /**
     * Serializes the Message object into a String.
     * It appears that the REST service is not allowing a Message object to be serialized, so the easiest
     * work-around for this problem is to just return a String that represents a Message object.
     * @return The string representation of a Message object. The returned value will consist of each tag, followed
     * by a "=>" delimiter, followed by the tag's value, followed by a "|" delimiter to separate each
     * tag entry.
     */
    public String serialize();

    /**
     * Enum for valid tags to be placed in a Message object.
     */
    public enum ValidTags {
        /**
         * Doctor tag. Used for categorizing information about a doctor.
         */
        DOCTOR,
        /**
         * Patient tag. Used for categorizing information about a patient.
         */
        PATIENT,
        /**
         * Appointment tag. Used for categorizing information about an appointment.
         */
        APPOINTMENT,
        /**
         * Error tag. Used for categorizing fully-detailed information about an error that has occurred.
         */
        ERROR,
        /**
         * Display error tag. Used for categorizing simple information about an error.
         */
        DISPLAY_ERROR
    }
}
