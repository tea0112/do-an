package com.thai.doan.controller;

import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class GraphQLController {
    private final GraphQL graphQL;

    @PostMapping("/graphql")
    public ExecutionResult graphQl(@RequestBody String query) {
        return graphQL.execute(query);
    }
}
