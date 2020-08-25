package de.intension.halo.hibernate;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import de.intension.halo.entity.DataType;
import de.intension.halo.entity.Link;
import de.intension.halo.entity.Property;
import de.intension.halo.entity.Template;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

/**
 * Can be used to build a HALO template from an object with Hibernate and Jakarta EE annotations.
 */
@Log
@Accessors(chain = true)
@RequiredArgsConstructor
public class HibernateTemplateBuilder
{

    /**
     * Name of the generated template.
     * 
     * @param templateName Set name of the generated template.
     */
    @Setter
    @NonNull
    private String                            templateName;
    /**
     * Mappings of property identifier to multi-language mapping.
     * <br/>
     * <br/>
     * Property identifier should consist of object class and field name, separated by a dot.
     * For example {@code "Car.brand"}.
     * 
     * @param locales Set mappings of property identifier to multi-language mapping.
     */
    @Setter
    private Map<String, Map<String, String>>  locales;
    private Map<String, Link>                 nestedLinks;
    private List<Class<? extends Annotation>> readOnlyAnnotations  = Arrays.asList(Id.class);
    private List<Class<? extends Annotation>> requiredAnnotations  = Arrays.asList(NotNull.class, NotEmpty.class);
    private List<Class<? extends Annotation>> transientAnnotations = Arrays.asList(Transient.class);

    /**
     * Set annotations that define weither a property is write protected.
     * 
     * @param annotations Set annotations that define write protection.
     * @return Annotations that define write protection.
     */
    @SafeVarargs
    public final HibernateTemplateBuilder setReadOnlyAnnotations(Class<? extends Annotation>... annotations)
    {
        this.readOnlyAnnotations = Arrays.asList(annotations);
        return this;
    }

    /**
     * Set annotations that define weither a property is required.
     * 
     * @param annotations Set annotations that define weither a property is required.
     * @return Annotations that define weither a property is required.
     */
    @SafeVarargs
    public final HibernateTemplateBuilder setRequiredAnnotations(Class<? extends Annotation>... annotations)
    {
        this.requiredAnnotations = Arrays.asList(annotations);
        return this;
    }

    /**
     * Set annotations that define weither a property should be ignored.
     * 
     * @param annotations Set annotations that define transiency.
     * @return Annotations that define transiency.
     */
    @SafeVarargs
    public final HibernateTemplateBuilder setTransientAnnotations(Class<? extends Annotation>... annotations)
    {
        this.transientAnnotations = Arrays.asList(annotations);
        return this;
    }

    /**
     * Set nested link for a given property.
     * 
     * @param propertyName Property to add the link to.
     * @param link Link that should be added to the property.
     */
    public final HibernateTemplateBuilder addNestedLink(String propertyName, Link link)
    {
        if (nestedLinks == null) {
            nestedLinks = new HashMap<>();
        }
        nestedLinks.put(propertyName, link);
        return this;
    }

    /**
     * Build HALO template from an object instance.
     * 
     * @param entity Object instance.
     * @return Generated HALO template.
     */
    public Template build(Object entity)
    {
        Class<?> entityType = entity.getClass();
        Map<String, Object> attributes = getAttributes(entity);
        List<Property> properties = new ArrayList<>();
        for (Field field : entityType.getDeclaredFields()) {
            Property property = buildProperty(entityType, field);
            property.setValue(attributes.get(field.getName()));
            properties.add(property);
        }
        return new Template(templateName).setProperties(properties);
    }

    private Map<String, Object> getAttributes(Object entity)
    {
        Map<String, Object> attributes = new HashMap<>();
        try {
            for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(entity.getClass()).getPropertyDescriptors()) {
                attributes.put(propertyDescriptor.getName(), propertyDescriptor.getReadMethod().invoke(entity));
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.log(Level.SEVERE, "HibernateTemplateBuilder failed to read object.", e);
        }
        return attributes;
    }

    /**
     * Build HALO template from an objects class.
     * 
     * @param entityType Class of the object.
     * @return Generated HALO template.
     */
    public Template build(Class<?> entityType)
    {
        return new Template(templateName).setProperties(buildProperties(entityType));
    }

    private List<Property> buildProperties(Class<?> entityType)
    {
        List<Property> properties = new ArrayList<>();
        for (Field field : entityType.getDeclaredFields()) {
            if (isTransient(field)) {
                continue;
            }
            Property property = buildProperty(entityType, field);
            properties.add(property);
        }
        return properties;
    }

    private Property buildProperty(Class<?> entityType, Field field)
    {
        Property property = new Property(field.getName());
        if (locales != null) {
            property.setTitle(locales.get(entityType.getSimpleName() + "." + field.getName()));
        }
        setDataType(property, field.getGenericType());
        for (Annotation annotation : field.getAnnotations()) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (Pattern.class.equals(annotationType)) {
                Pattern patternAnnotation = (Pattern)annotation;
                property.setRegex(patternAnnotation.regexp());
            }
            if (checkAnnotation(annotationType, requiredAnnotations)) {
                property.setRequired(true);
            }
            if (checkAnnotation(annotationType, readOnlyAnnotations)) {
                property.setReadOnly(true);
            }
        }
        return property;
    }

    private void setDataType(Property property, Type type)
    {
        if (type instanceof ParameterizedType) {
            property.setMultivalued(true);
            type = ((ParameterizedType)type).getActualTypeArguments()[0];
        }
        if (type.equals(Boolean.class)) {
            property.setType(DataType.BOOLEAN);
            return;
        }
        if (type.equals(String.class)) {
            property.setType(DataType.STRING);
            return;
        }
        if (type.equals(Integer.class) || type.equals(Long.class)) {
            property.setType(DataType.INTEGER);
            return;
        }
        if (type.equals(Float.class) || type.equals(Double.class)) {
            property.setType(DataType.FLOAT);
            return;
        }
        if (type.equals(LocalDateTime.class)) {
            property.setType(DataType.DATE);
            property.setRegex("yyyy-MM-dd'T'HH:mm:ss.SSS");
            return;
        }
        property.setType(DataType.OBJECT);
        property.setProperties(buildProperties((Class<?>)type));
        if (nestedLinks != null) {
            property.setLink(nestedLinks.get(property.getName()));
        }
    }

    private boolean isTransient(Field field)
    {
        for (Annotation annotation : field.getAnnotations()) {
            if (transientAnnotations.contains(annotation.annotationType())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkAnnotation(Class<? extends Annotation> annotation, List<Class<? extends Annotation>> allowed)
    {
        for (Class<? extends Annotation> a : allowed) {
            if (a.equals(annotation)) {
                return true;
            }
        }
        return false;
    }
}
