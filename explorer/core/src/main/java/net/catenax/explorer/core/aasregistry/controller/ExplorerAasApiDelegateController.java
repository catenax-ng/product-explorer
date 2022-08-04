/*
 * Copyright (c) 2021-2022 Robert Bosch Manufacturing Solutions GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.catenax.explorer.core.aasregistry.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.aasregistry.model.AasShellWithMetaDataResponse;
import net.catenax.explorer.core.aasregistry.model.ShellLookup;
import net.catenax.explorer.core.twinregistry.TwinRegistryService;
import net.catenax.explorer.core.twinregistry.TwinRegistryService.SearchShellDescriptorResults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/shell-descriptors")
public class ExplorerAasApiDelegateController {

    final TwinRegistryService twinRegistryService;
    final ObjectMapper mapper;

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @PostMapping("/query")
    @Operation(summary = "Returns shell descriptors for the given query.")
    @ApiResponse(responseCode = "201", description = "Aas Objects with meta information")
    public ResponseEntity<List<AasShellWithMetaDataResponse>> postFetchAasDescriptorsWithMetaData(@Parameter(description = "Aas objects with source meta information from multiple providers") List<ShellLookup> shellLookup, @Parameter(description = "The bpn to get the Aas Descriptors for") @Schema(example = "BPN102384028F") String bpn) {
        log.info("Querying for Asset by: " + shellLookup.get(0).getQuery().getAssetIds().get(0).getValue());

        final List<SearchShellDescriptorResults> searchShellDescriptorResults = twinRegistryService
                .searchTwinRegistries(mapper.writeValueAsString(shellLookup.get(0).getQuery().getAssetIds().get(0)), bpn); //TODO ask Fethullah: 1. multiple query objects ? 2. How we want to retrieve by many query ?

        return ResponseEntity.ok(mapper.convertValue(searchShellDescriptorResults, List.class));
    }
}
