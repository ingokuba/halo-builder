package de.intension.halo.hibernate;

import java.util.Map;

import org.hibernate.validator.constraints.Length;

import de.intension.halo.AnnotationTransformer;
import de.intension.halo.entity.Property;
import de.intension.halo.entity.Validation;

/**
 * Maps {@link Length} annotation to 'minLength' and 'maxLength' validations.
 */
public class LengthValidationMapper
        extends AnnotationTransformer<Length>
    implements ValidationMessageDecorator
{

    public LengthValidationMapper()
    {
        super(Length.class);
    }

    @Override
    public void transformProperty(Length annotation, Property property)
    {
        if (annotation.min() > 0) {
            property.addValidation(new Validation("minLength", annotation.min()));
        }
        if (annotation.max() < Integer.MAX_VALUE) {
            property.addValidation(new Validation("maxLength", annotation.max()));
        }
    }

    @Override
    public void transformProperty(Length annotation, Property property, Map<String, Map<String, String>> locales)
    {
        Map<String, String> message = getValidationMessage(annotation.message(), locales);
        if (annotation.min() > 0) {
            if (message != null) {
                message.replaceAll((key, value) -> value.replace("{min}", String.valueOf(annotation.min())));
            }
            property.addValidation(new Validation("minLength", annotation.min()).setMessage(message));
        }
        if (annotation.max() < Integer.MAX_VALUE) {
            if (message != null) {
                message.replaceAll((key, value) -> value.replace("{max}", String.valueOf(annotation.max())));
            }
            property.addValidation(new Validation("maxLength", annotation.max()).setMessage(message));
        }
    }
}
