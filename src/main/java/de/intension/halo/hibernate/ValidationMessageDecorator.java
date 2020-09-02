package de.intension.halo.hibernate;

import java.util.Map;

/**
 * Provides utility methods for validation message processing.
 */
public interface ValidationMessageDecorator
{

    /**
     * Get multi-language message from map with identifier like:
     * 
     * <pre>
     * {Example.message}
     * </pre>
     * 
     * @param message Message that may be surrounded by curly braces.
     * @param locales Map of identifier to multi-language message.
     * @return Multi-language message or <b>null</b> if it cannot be found.
     */
    default Map<String, String> getValidationMessage(String message, Map<String, Map<String, String>> locales)
    {
        if (message == null || locales == null) {
            return null;
        }
        return locales.get(message.replaceAll("^\\{|\\}$", ""));
    }
}
