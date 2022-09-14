package net.catenax.explorer.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Value
@AllArgsConstructor
public class QueryCommand {
    List<QueryCommandParam> queryParams;

    public static QueryCommand create(List<QueryCommandParam> queryParams) {
        return new QueryCommand(queryParams);
    }
    public static QueryCommand create(String key, String value) {
        return new QueryCommand(List.of(new QueryCommandParam(key, value)));
    }

    @JsonIgnore
    public Optional<String> getIdValue() {
        return Optional.ofNullable(queryParams).orElse(Collections.emptyList()).stream()
                .filter(queryCommandParam -> "id".equalsIgnoreCase(queryCommandParam.getKey()))
                .map(QueryCommandParam::getValue)
                .findFirst();
    }

    @SneakyThrows
    public String toStringParams(ObjectMapper objectMapper) {
        return objectMapper.writeValueAsString(queryParams);
    }
}
