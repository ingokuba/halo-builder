package de.intension.halo.hibernate;

import java.util.Map;

import javax.validation.constraints.Pattern;

import de.intension.halo.AnnotationTransformer;
import de.intension.halo.entity.Property;
import de.intension.halo.entity.Validation;

/**
 * Maps {@link Pattern} annotation to 'regex' validation.
 */
public class RegexValidationMapper
        extends AnnotationTransformer<Pattern>
    implements ValidationMessageDecorator
{

    public RegexValidationMapper()
    {
        super(Pattern.class);
    }

    @Override
    public void transformProperty(Pattern annotation, Property property)
    {
        property.addValidation(new Validation("regex", annotation.regexp()));
    }

    @Override
    public void transformProperty(Pattern annotation, Property property, Map<String, Map<String, String>> locales)
    {
        property.addValidation(new Validation("regex", annotation.regexp())
            .setMessage(getValidationMessage(annotation.message(), locales)));
    }
}
