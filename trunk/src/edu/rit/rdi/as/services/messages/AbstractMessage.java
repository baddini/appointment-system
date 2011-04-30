package edu.rit.rdi.as.services.messages;

import java.util.HashMap;

/**
 * An abstract Message. This holds the tag-to-value pairs of each subclass.
 * @date Apr 30, 2011
 * @author Eric Kisner
 */
public abstract class AbstractMessage implements Message {

    protected HashMap<String, String> tagsToValues;

    public AbstractMessage() {
        tagsToValues = new HashMap<String, String>();
    }
}
