package net.catenax.explorer.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;

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
        if (queryParams.size() != 1) return Optional.empty();
        final QueryCommandParam param = queryParams.get(0);
        if (param.getKey().equals("ID")) {
            return Optional.ofNullable(param.getValue());
        }
        if (param.getKey().equals("id")) {
            return Optional.ofNullable(param.getValue());
        }
        return Optional.empty();
    }

    @SneakyThrows
    public String toStringParams(ObjectMapper objectMapper) {
        return objectMapper.writeValueAsString(queryParams);
    }
}
