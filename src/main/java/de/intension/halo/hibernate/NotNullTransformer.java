package de.intension.halo.hibernate;

import java.util.Map;

import javax.validation.constraints.NotNull;

import de.intension.halo.AnnotationTransformer;
import de.intension.halo.entity.Property;
import de.intension.halo.entity.Validation;

/**
 * Sets {@link Property#setRequired(Boolean)} to <b>true</b> for annotation {@link NotNull}.
 */
public class NotNullTransformer
        extends AnnotationTransformer<NotNull>
    implements ValidationMessageDecorator
{

    public NotNullTransformer()
    {
        super(NotNull.class);
    }

    @Override
    public void transformProperty(NotNull annotation, Property property)
    {
        property.addValidation(new Validation("required", true));
    }

    @Override
    public void transformProperty(NotNull annotation, Property property, Map<String, Map<String, String>> locales)
    {
        property.addValidation(new Validation("required", true)
            .setMessage(getValidationMessage(annotation.message(), locales)));
    }
}
