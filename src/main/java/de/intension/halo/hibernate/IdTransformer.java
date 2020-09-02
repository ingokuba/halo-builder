package de.intension.halo.hibernate;

import javax.persistence.Id;

import de.intension.halo.AnnotationTransformer;
import de.intension.halo.entity.Property;

/**
 * Sets {@link Property#setReadOnly(Boolean)} to <b>true</b> for annotation {@link Id}.
 */
public class IdTransformer
        extends AnnotationTransformer<Id>
{

    public IdTransformer()
    {
        super(Id.class);
    }

    @Override
    public void transformProperty(Id annotation, Property property)
    {
        property.setReadOnly(true);
    }
}
