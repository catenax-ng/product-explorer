package net.catenax.explorer.core.aasregistry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true)
public class QueryCommand {
    @Schema(description = "Aas objects with source meta information from multiple providers")
    List<ShellLookup> shellLookup;
    @Schema(description = "The bpn to get the Aas Descriptors for", example = "BPN102384028F")
    String bpn;
}
