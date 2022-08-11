package net.catenax.explorer.core.webui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.catenax.explorer.core.shell.ShellDescriptorResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDto {
    private ShellDescriptorResponse.ShellDescriptor descriptor;
    private String json;
}
