package com.sandeep.podstream.core.config;

import com.sandeep.podstream.service.Auth0JwtService;
import graphql.com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;
import java.util.Set;

@Component
public class AuthGraphQLInterceptor implements WebGraphQlInterceptor {
    private final Set<String> unauthenticatedQueries = Set.of("IntrospectionQuery", "__schema", "userLogin", "userSignup");

    @Autowired
    Auth0JwtService jwtService;

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        String query = request.getDocument();
        if(!Strings.isNullOrEmpty(query) && (query.contains("__schema")||query.contains("__type"))){        //for Introspection query
            return chain.next(request);
        }

        String operationName = request.getOperationName();                  //for queries not requiring token
        if (unauthenticatedQueries.contains(operationName)) {
            return chain.next(request);
        }

        try {
            String authHeader = request.getHeaders().getFirst("Authorization");
            String token = authHeader.substring(7);
            if (jwtService.isTokenValid(token)) {
                String username = jwtService.getJwtClaimValue(token, "username");
                request.getAttributes().put("username", username);
            }
        } catch (AuthenticationException e) {
            throw new RuntimeException("Missing/Invalid Authorization token.");
        }
        return chain.next(request);
    }
}
