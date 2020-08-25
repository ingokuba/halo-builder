package de.intension.halo.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * A message describes success or failure of a action.
 */
@Data
@FieldNameConstants
@Accessors(chain = true)
public class Message
{

    /**
     * Technical name of the message.
     * 
     * @param name Set technical name.
     * @return Technical name of the message.
     */
    private String              name;
    /**
     * Property name which caused an error.
     * 
     * @param property Set name of the property.
     * @return Name of the property.
     */
    private String              property;
    /**
     * Multi-language message.
     * 
     * @param title Set mappings of language code to translation.
     * @return Mappings of language code to translation.
     */
    private Map<String, String> value;

    /**
     * Set message for a given locale.
     * 
     * @param locale Language code for this message translation.
     * @param value Translation of the message in the given language.
     */
    public Message setValue(String locale, String message)
    {
        if (value == null) {
            value = new HashMap<>();
        }
        value.put(locale, message);
        return this;
    }
}
