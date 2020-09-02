package de.intension.halo.hibernate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class HibernateDecoratorTest
    implements ValidationMessageDecorator
{

    @Test
    void should_find_message_with_curly_braces()
    {
        Map<String, Map<String, String>> locales = new HashMap<>();
        locales.put("Test.value", testValue());

        Map<String, String> message = getValidationMessage("{Test.value}", locales);

        assertThat(message, notNullValue());
    }

    @Test
    void should_find_message_without_curly_braces()
    {
        Map<String, Map<String, String>> locales = new HashMap<>();
        locales.put("Test.value", testValue());

        Map<String, String> message = getValidationMessage("Test.value", locales);

        assertThat(message, notNullValue());
    }

    @Test
    void should_return_null_if_message_not_found()
    {
        Map<String, Map<String, String>> locales = new HashMap<>();
        locales.put("Test.value", testValue());

        Map<String, String> message = getValidationMessage("nothing", locales);

        assertThat(message, nullValue());
    }

    @Test
    void should_return_null_if_key_is_null()
    {
        Map<String, Map<String, String>> locales = new HashMap<>();
        locales.put("Test.value", testValue());

        Map<String, String> message = getValidationMessage(null, locales);

        assertThat(message, nullValue());
    }

    @Test
    void should_return_null_if_map_is_null()
    {
        Map<String, String> message = getValidationMessage("Test.value", null);

        assertThat(message, nullValue());
    }

    private static Map<String, String> testValue()
    {
        Map<String, String> value = new HashMap<>();
        value.put("en", "test");
        return value;
    }
}
