package net.catenax.explorer.core;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class QueryCommandParam {
    String key;
    String value;
}
