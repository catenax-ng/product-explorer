package net.catenax.explorer.core.aasregistry.model;

import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.Valid;
import java.util.List;

@Value
@NoArgsConstructor(force = true)
public class ShellLookupQuery {
    @Valid
    private List<IdentifierKeyValuePair> assetIds;
}
