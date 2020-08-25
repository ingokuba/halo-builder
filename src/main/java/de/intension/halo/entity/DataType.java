package de.intension.halo.entity;

import java.time.temporal.Temporal;
import java.util.Date;

/**
 * Describes the allowed data type of a {@link Property}.
 */
public enum DataType
{
    /**
     * Value has class {@link String}.
     */
    STRING,
    /**
     * Value has class {@link Boolean} or primitive type <b>boolean</b>.
     */
    BOOLEAN,
    /**
     * Value has class {@link Integer}/{@link Short}/{@link Long}
     * or primitive type <b>int</b>/<b>short</b>/<b>long</b>.
     */
    INTEGER,
    /**
     * Value has class {@link Float}/{@link Double}
     * or primitive type <b>float</b>/<b>double</b>.
     */
    FLOAT,
    /**
     * Value has class {@link Date} or an implementation of {@link Temporal}.
     */
    DATE,
    /**
     * Value can be an {@link Object} of any class.
     */
    OBJECT
}
