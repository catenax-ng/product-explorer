package net.catenax.explorer.core.twinregistry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
public class QueryCommand {
    @Schema(description = "Aas objects with source meta information from multiple providers")
    List<ShellLookup> shellLookup;
    @Schema(description = "The bpn to get the Aas Descriptors for", example = "BPN102384028F")
    String bpn;
}
