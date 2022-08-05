package net.catenax.explorer.core.aasregistry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Value
@NoArgsConstructor(force = true)
public class Reference {
    @NotNull
    @Size(min = 1, max = 1)
    @Schema(example = "urn:uuid:882fc530-b69b-4707-95f6-5dbc5e9baaa8")
    private List<String> value = new ArrayList<>();
}
