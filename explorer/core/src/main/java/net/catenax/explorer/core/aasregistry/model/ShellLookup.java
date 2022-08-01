package net.catenax.explorer.core.aasregistry.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class ShellLookup {
    @Valid
    @NotNull
    private ShellLookupQuery query;

    public ShellLookup query(ShellLookupQuery query) {
        this.query = query;
        return this;
    }
}
