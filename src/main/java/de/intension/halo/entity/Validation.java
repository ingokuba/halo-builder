package de.intension.halo.entity;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Validation
{

    /**
     * 
     */
    @NonNull
    private String              name;
    /**
     * Validation object.
     */
    @NonNull
    private Object              value;
    /**
     * Multi-language message of the validation.
     * 
     * @param message Set mappings of language code to translation.
     * @return Mappings of language code to translation.
     */
    private Map<String, String> message;
}
