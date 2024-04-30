package com.project.identity.services;

import com.nimbusds.jose.JOSEException;
import com.project.identity.dtos.requests.AuthenticationRequest;
import com.project.identity.dtos.requests.LogoutRequest;
import com.project.identity.dtos.requests.RefreshRequest;
import com.project.identity.dtos.requests.VerifyTokenRequest;
import com.project.identity.dtos.responses.AuthenticationResponse;
import com.project.identity.dtos.responses.VerifyTokenResponse;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    VerifyTokenResponse verifyToken(VerifyTokenRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;

}
