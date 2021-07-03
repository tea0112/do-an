package com.thai.doan.graphql.datafetcher;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.repository.SessionRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class AllSessionFetcher implements DataFetcher {
    private final SessionRepository sessionRepo;

    @Override
    public List<Session> get(DataFetchingEnvironment environment) throws Exception {
        return sessionRepo.findAll();
    }
}
