package com.thai.doan.graphql.datafetcher;

import com.thai.doan.dao.model.Session;
import com.thai.doan.dao.repository.SessionRepository;
import com.thai.doan.exception.ErrorCode;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@Data
public class SessionFetcher implements DataFetcher {
    private final SessionRepository sessionRepo;

    @Override
    public Session get(DataFetchingEnvironment environment) throws Exception {
        Integer sessionId = environment.getArgument("id");
        return sessionRepo.findById(sessionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
            ErrorCode.SESSION_NOT_FOUND));
    }
}
