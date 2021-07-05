package com.thai.doan.graphql.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
public class ClassFilterFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
        Map<String, Object> classFilter = dataFetchingEnvironment.getArguments();
        return null;
    }
}
