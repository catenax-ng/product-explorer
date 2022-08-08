package net.catenax.explorer.core.twinregistry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * LangString
 */
@Value
@NoArgsConstructor(force = true)
public class LangString {
    @NotNull
    @Size(min = 1, max = 10)
    @Schema(example = "en")
    private String language;

    @NotNull
    @Size(min = 1, max = 500)
    @Schema(example = "The shell for a vehicle")
    private String text;
}
