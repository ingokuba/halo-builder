package de.intension.halo.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A property describes a field of a ressource.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Property
{

    /**
     * Unique name of the property per template.
     * 
     * @param name Set name of the property.
     * @return Name of the property.
     */
    @NonNull
    private String              name;
    /**
     * Value of the property. Instance type is defined in {@link #type}.
     * 
     * @param value Set value of the property.
     * @return Value of the property.
     */
    private Object              value;
    /**
     * Multi-language title of the property.
     * 
     * @param title Set mappings of language code to translation.
     * @return Mappings of language code to translation.
     */
    private Map<String, String> title;
    /**
     * Regular expression that restricts the {@link #value} of this property.
     * <br/>
     * <br/>
     * In case of {@link #type} {@link DataType#DATE} this should contain the date format.
     * 
     * @param regex Set restriction to property value.
     * @return Restriction in form of a regular expression.
     */
    private String              regex;
    /**
     * Defines weither this property is required on the object.
     * <br/>
     * <br/>
     * A value of <b>null</b> should be interpreted as <b>false</b>.
     * 
     * @param required Set weither this property is required.
     * @return <b>true</b> | <b>false</b> | <b>null</b>.
     */
    private Boolean             required;
    /**
     * Defines weither this property is write protected.
     * <br/>
     * <br/>
     * A value of <b>null</b> should be interpreted as <b>false</b>.
     * 
     * @param readOnly Set weither this property is write protected.
     * @return <b>true</b> | <b>false</b> | <b>null</b>.
     */
    private Boolean             readOnly;
    /**
     * Data type of the {@link #value}.
     * <br/>
     * <br/>
     * A value of <b>null</b> should be interpreted as {@link DataType#STRING}.
     * Some special cases include:
     * <ul>
     * <li>{@link DataType#STRING}: {@link #regex} may contain restriction.
     * <li>{@link DataType#DATE}: {@link #regex} should contain date format.
     * <li>{@link DataType#OBJECT}: {@link #properties} and/or {@link #link} should be set.
     * 
     * @param type Set data type of this property.
     * @return Data type of the property.
     */
    private DataType            type;
    /**
     * Defines weither this property contains a collection with elements of the given {@link #type}.
     * <br/>
     * <br/>
     * A value of <b>null</b> should be interpreted as <b>false</b>.
     * 
     * @param multivalued Set weither this property contains a collection.
     * @return <b>true</b> | <b>false</b> | <b>null</b>.
     */
    private Boolean             multivalued;
    /**
     * Properties of the complex value of {@link #type} {@link DataType#OBJECT}.
     * <br/>
     * <br/>
     * If this field is set, the complex value may be created during the creation of its parent.
     * 
     * @param properties Set properties of the complex value.
     * @return Properties of the complex value.
     */
    private List<Property>      properties;
    /**
     * Link of the complex value of {@link #type} {@link DataType#OBJECT}.
     * <br/>
     * <br/>
     * If this field is set, the complex value may not be created during the creation of its parent.
     * Instead, the templates of the link define weither the referenced object may be
     * created (method POST) or searched (method GET).
     * 
     * @param link Set link of the complex value.
     * @return Link of the complex value.
     */
    private Link                link;

    /**
     * Set title for a given locale.
     * 
     * @param locale Language code for this title translation.
     * @param value Translation of the title in the given language.
     */
    public Property setTitle(String locale, String value)
    {
        if (title == null) {
            title = new HashMap<>();
        }
        title.put(locale, value);
        return this;
    }
}
