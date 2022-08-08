package net.catenax.explorer.core.twinregistry.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
public class ShellLookup {
    @Valid
    @NotNull
    private ShellLookupQuery query;
}
