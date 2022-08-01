package net.catenax.explorer.core.aasregistry.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class IdentifierKeyValuePair {
    @NotNull
    @Size(min = 1, max = 200)
    private String key;

    @NotNull
    @Size(min = 1, max = 200)
    private String value;

    public IdentifierKeyValuePair key(String key) {
        this.key = key;
        return this;
    }

    public IdentifierKeyValuePair value(String value) {
        this.value = value;
        return this;
    }
}
