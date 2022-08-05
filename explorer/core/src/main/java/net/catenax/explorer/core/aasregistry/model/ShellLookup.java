package net.catenax.explorer.core.aasregistry.model;

import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Value
@NoArgsConstructor(force = true)
public class ShellLookup {
    @Valid
    @NotNull
    private ShellLookupQuery query;
}
