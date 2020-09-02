package de.intension.halo;

import java.lang.annotation.Annotation;
import java.util.Map;

import de.intension.halo.entity.Property;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Transforms a property based on the given annotation.
 * <br/>
 * <br/>
 * Implement this class with a simple constructor that calls sets the annotation type.
 * 
 * <pre>
 * public class ExampleTransformer 
 *          extends AnnotationTransformer&lt;ExampleAnnotation&gt;
 * {
 *      public ExampleTransformer()
 *      {
 *          super(ExampleAnnotation.class);
 *      }
 * ...
 * }
 * </pre>
 */
@RequiredArgsConstructor
public abstract class AnnotationTransformer<T extends Annotation>
{

    @NonNull
    private Class<T> annotationType;

    /**
     * Check whether annotation class matches <b>&lt;T&gt;</b>.
     * 
     * @param type Class of the annotation that is checked.
     * @return <b>true</b> if annotation matches.
     */
    public final boolean matchesAnnotation(Class<? extends Annotation> type)
    {
        return annotationType.equals(type);
    }

    /**
     * Transform the property based on the information of an annotation.
     * 
     * @param annotation Annotation to take information from.
     * @param property Property to transform.
     */
    public abstract void transformProperty(T annotation, Property property);

    /**
     * Transform the property based on the information of an annotation and use localization.
     * Override this method if localization is needed for the transformation.
     * 
     * @param annotation Annotation to take information from.
     * @param property Property to transform.
     * @param locales Localization provider.
     */
    public void transformProperty(T annotation, Property property, Map<String, Map<String, String>> locales)
    {
        transformProperty(annotation, property);
    }
}
