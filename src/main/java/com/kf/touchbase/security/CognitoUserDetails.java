package com.kf.touchbase.security;

import io.micronaut.security.authentication.UserDetails;

import java.util.Collection;

public class CognitoUserDetails extends UserDetails {
    private String email;

    private String clientId;

    public CognitoUserDetails(String username, Collection<String> roles) {
        super(username, roles);
    }

    public CognitoUserDetails(String username, Collection<String> roles, String email, String client_id) {
        super(username, roles);
        this.email = email;
        this.clientId = client_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
