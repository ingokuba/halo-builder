package de.intension.halo.hibernate;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import de.intension.halo.AnnotationTransformer;
import de.intension.halo.entity.Property;
import de.intension.halo.entity.Validation;

/**
 * Sets {@link Property#setRequired(Boolean)} to <b>true</b> for annotation {@link NotEmpty}.
 */
public class NotEmptyTransformer
        extends AnnotationTransformer<NotEmpty>
    implements ValidationMessageDecorator
{

    public NotEmptyTransformer()
    {
        super(NotEmpty.class);
    }

    @Override
    public void transformProperty(NotEmpty annotation, Property property)
    {
        property.addValidation(new Validation("required", true));
    }

    @Override
    public void transformProperty(NotEmpty annotation, Property property, Map<String, Map<String, String>> locales)
    {
        property.addValidation(new Validation("required", true)
            .setMessage(getValidationMessage(annotation.message(), locales)));
    }
}
