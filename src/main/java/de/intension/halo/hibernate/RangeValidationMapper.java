package de.intension.halo.hibernate;

import java.util.Map;

import org.hibernate.validator.constraints.Range;

import de.intension.halo.AnnotationTransformer;
import de.intension.halo.entity.Property;
import de.intension.halo.entity.Validation;

/**
 * Maps {@link Range} annotation to 'minValue' and 'maxValue' validations.
 */
public class RangeValidationMapper
        extends AnnotationTransformer<Range>
    implements ValidationMessageDecorator
{

    public RangeValidationMapper()
    {
        super(Range.class);
    }

    @Override
    public void transformProperty(Range annotation, Property property)
    {
        if (annotation.min() > 0) {
            property.addValidation(new Validation("minValue", annotation.min()));
        }
        if (annotation.max() < Long.MAX_VALUE) {
            property.addValidation(new Validation("maxValue", annotation.max()));
        }
    }

    @Override
    public void transformProperty(Range annotation, Property property, Map<String, Map<String, String>> locales)
    {
        Map<String, String> message = getValidationMessage(annotation.message(), locales);
        if (annotation.min() > 0) {
            if (message != null) {
                message.replaceAll((key, value) -> value.replace("{min}", String.valueOf(annotation.min())));
            }
            property.addValidation(new Validation("minValue", annotation.min()).setMessage(message));
        }
        if (annotation.max() < Long.MAX_VALUE) {
            if (message != null) {
                message.replaceAll((key, value) -> value.replace("{max}", String.valueOf(annotation.max())));
            }
            property.addValidation(new Validation("maxValue", annotation.max()).setMessage(message));
        }
    }
}
