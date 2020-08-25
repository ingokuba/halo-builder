package de.intension.halo.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A template describes an action for a {@link Link}, including the HTTP method,
 * media type and properties of the ressource.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Template
{

    /**
     * Unique name of the action per link.
     * 
     * @param name Set name of the action.
     * @return Name of the action.
     */
    @NonNull
    private String              name;
    /**
     * The HTTP method to be used for the call, as defined in
     * [<a href="https://tools.ietf.org/html/rfc2616">RFC2616</a>].
     * 
     * @param method Set method for this action.
     * @return HTTP-Method of the action.
     */
    private String              method;
    /**
     * Multi-language title of the action.
     * 
     * @param title Set mappings of language code to translation.
     * @return Mappings of language code to translation.
     */
    private Map<String, String> title;
    /**
     * Media type of the payload body that is sent with the call.
     * <br/>
     * <br/>
     * Must only be set if {@link #query} is not <b>true</b>.
     * If this field is not set, the media type
     * <a href="https://tools.ietf.org/html/rfc4627">application/json</a> should be assumed.
     * 
     * @param contentType Set media type of the payload body.
     * @return Media type of the payload body.
     */
    private String              contentType;
    /**
     * Properties of the payload body or query parameters.
     * 
     * @param properties Set properties of the payload body or query parameters.
     * @return Properties of the payload body or query parameters.
     */
    private List<Property>      properties;
    /**
     * Defines weither the {@link #properties} should be interpreted as query parameters.
     * <br/>
     * <br/>
     * A value of <b>null</b> should be interpreted as <b>false</b>.
     * 
     * @param query Set weither this action has query parameters.
     * @return <b>true</b> | <b>false</b> | <b>null</b>.
     */
    private Boolean             query;

    /**
     * Set title for a given locale.
     * 
     * @param locale Language code for this title translation.
     * @param value Translation of the title in the given language.
     */
    public Template setTitle(String locale, String value)
    {
        if (title == null) {
            title = new HashMap<>();
        }
        title.put(locale, value);
        return this;
    }

    /**
     * Add given properties to the list of properties.
     * 
     * @param properties Properties to add to {@link #properties}.
     */
    public Template addProperties(Property... properties)
    {
        if (this.properties == null) {
            this.properties = new ArrayList<>();
        }
        this.properties.addAll(Arrays.asList(properties));
        return this;
    }
}
