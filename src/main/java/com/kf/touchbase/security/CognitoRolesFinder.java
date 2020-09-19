package com.kf.touchbase.security;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.context.annotation.Primary;
import io.micronaut.security.token.Claims;
import io.micronaut.security.token.DefaultRolesFinder;
import io.micronaut.security.token.config.TokenConfiguration;

import javax.inject.Singleton;
import java.util.List;

@Primary
@Singleton
public class CognitoRolesFinder extends DefaultRolesFinder {

    public CognitoRolesFinder(TokenConfiguration tokenConfiguration) {
        super(tokenConfiguration);
    }

    public List<String> findInClaims(@NonNull Claims claims) {
        List<String> roles = super.findInClaims(claims);
        roles.add(AuthoritiesConstants.USER);
        return roles;
    }
}
