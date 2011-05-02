package edu.rit.rdi.as.services.messages;

import java.io.Serializable;

/**
 * Service Message represents a successful message after calling a method on the server. The service message will
 * contain tag/value pairs depending on which method the client called.
 * @date Apr 30, 2011
 * @author Eric Kisner
 */
public class ServiceMessage extends AbstractMessage implements Serializable {

    public ServiceMessage() {
        super();
    }
}
