package com.kf.touchbase.security;

import io.micronaut.context.annotation.Primary;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.TokenAuthenticationFetcher;
import io.micronaut.security.token.reader.TokenResolver;
import io.micronaut.security.token.validator.TokenValidator;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Primary
@Singleton
public class CognitoTokenAuthenticationFetcher extends TokenAuthenticationFetcher {

    @Inject
    public CognitoTokenAuthenticationFetcher(Collection<TokenValidator> tokenValidators, TokenResolver tokenResolver, ApplicationEventPublisher eventPublisher) {
        super(tokenValidators, tokenResolver, eventPublisher);
    }

    public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {
        return super.fetchAuthentication(request);
    }
}
