package edu.rit.rdi.as.services.messages;

import java.io.Serializable;
import java.util.HashMap;

/**
 * An Error Message represents that an error has occurred. The tag for an error message should be "ERROR" and the
 * value should have full details about the error. The error message should implement a method that allows the caller
 * to specify what information should be displayed to the user (since this message will most likely be used on the
 * front-end). Or, the object can have a separate tag of "DISPLAY_ERROR" with less details to be displayed to the user.
 * @date Apr 30, 2011
 * @author Eric Kisner
 */
public class ErrorMessage extends AbstractMessage implements Serializable {

    public ErrorMessage() {
        super();
    }
}
