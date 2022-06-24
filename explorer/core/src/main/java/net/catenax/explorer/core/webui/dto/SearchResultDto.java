package net.catenax.explorer.core.webui.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class SearchResultDto {
    private String shortId;
    private String identification;
    private Map<String, String> specificAssetIds;
}
