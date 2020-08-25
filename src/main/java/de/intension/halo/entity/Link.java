package de.intension.halo.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A link describes the location and available actions of a ressource.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Link
{

    /**
     * Unique name of the link per entity.
     * 
     * @param name Set name of the ressource.
     * @return Name of the ressource.
     */
    @NonNull
    private String              name;
    /**
     * URL that defines the location of the ressource.
     * 
     * @param href Set location of the ressource.
     * @return Location of the ressource.
     */
    @NonNull
    private String              href;
    /**
     * Multi-language title of the ressource.
     * 
     * @param title Set mappings of language code to translation.
     * @return Mappings of language code to translation.
     */
    private Map<String, String> title;
    /**
     * Available actions on the ressource.
     * 
     * @param templates Set actions for the ressource.
     * @return Available actions of the ressource.
     */
    private List<Template>      templates;

    /**
     * Set title for a given locale.
     * 
     * @param locale Language code for this title translation.
     * @param value Translation of the title in the given language.
     */
    public Link setTitle(String locale, String value)
    {
        if (title == null) {
            title = new HashMap<>();
        }
        title.put(locale, value);
        return this;
    }

    /**
     * Add a new action to the ressource.
     * 
     * @param template Action to add to {@link #templates}.
     */
    public Link addTemplate(Template template)
    {
        if (templates == null) {
            templates = new ArrayList<>();
        }
        templates.add(template);
        return this;
    }
}
