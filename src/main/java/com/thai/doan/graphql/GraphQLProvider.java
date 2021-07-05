package com.thai.doan.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.thai.doan.graphql.datafetcher.AllSessionFetcher;
import com.thai.doan.graphql.datafetcher.ClassFilterFetcher;
import com.thai.doan.graphql.datafetcher.SessionFetcher;
import graphql.GraphQL;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Data
@Component
public class GraphQLProvider {
    private GraphQL graphQL;

    private final AllSessionFetcher allSessionFetcher;
    private final SessionFetcher sessionFetcher;
    private final ClassFilterFetcher classFilterFetcher;

    @Bean
    private GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("graphql/schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
            .scalar(ExtendedScalars.Date)
            .scalar(ExtendedScalars.DateTime)
            .type(newTypeWiring("Query")
                .dataFetcher("sessionById", sessionFetcher))
            .type(newTypeWiring("Query")
                .dataFetcher("session", allSessionFetcher))
            .type(newTypeWiring("Query")
                .dataFetcher("classes", classFilterFetcher))
            .build();
    }
}