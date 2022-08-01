package net.catenax.explorer.core.aasregistry.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * LangString
 */

@Data
public class LangString {
    @NotNull
    @Size(min = 1, max = 10)
    private String language;

    @NotNull
    @Size(min = 1, max = 500)
    private String text;
}

