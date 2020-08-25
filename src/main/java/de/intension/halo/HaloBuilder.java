package de.intension.halo;

import de.intension.halo.entity.Entity;

/**
 * Implement this interface to build a HALO entity with data and links.
 */
public interface HaloBuilder
{

    /**
     * Build the HALO entity from a POJO.
     * 
     * @param entity Plain old Java object
     * @return HALO entity
     */
    public default Entity build(Object entity)
    {
        Entity halEntity = new Entity().setData(entity);
        addLinks(halEntity, entity);
        return halEntity;
    }

    /**
     * Implement this method to add links to the entity.
     * 
     * @param haloEntity HALO entity to add the links to
     * @param entity Plain old java object
     */
    public abstract void addLinks(Entity haloEntity, Object entity);

}
