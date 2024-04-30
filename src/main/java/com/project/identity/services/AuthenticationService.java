package com.project.identity.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.project.identity.dtos.requests.AuthenticationRequest;
import com.project.identity.dtos.requests.LogoutRequest;
import com.project.identity.dtos.requests.RefreshRequest;
import com.project.identity.dtos.requests.VerifyTokenRequest;
import com.project.identity.dtos.responses.AuthenticationResponse;
import com.project.identity.dtos.responses.VerifyTokenResponse;
import com.project.identity.entities.InvalidatedToken;
import com.project.identity.entities.User;
import com.project.identity.exceptions.AppException;
import com.project.identity.exceptions.ErrorCode;
import com.project.identity.repositories.InvalidatedTokenRepository;
import com.project.identity.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository userRepository;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Value("${jwt.signerKey}")
    protected String signerKey;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new AppException(ErrorCode.USERNAME_OR_PASSWORD_INCORRECT)
        );
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isAuthenticated) {
            throw new AppException(ErrorCode.USERNAME_OR_PASSWORD_INCORRECT);
        }
        String token = generateToken(user);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    @Override
    public VerifyTokenResponse verifyToken(VerifyTokenRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        boolean isTokenValid = true;
        try {
            parseToken(token);
        } catch (AppException e) {
            isTokenValid = false;
        }
        return VerifyTokenResponse.builder()
                .valid(isTokenValid)
                .build();
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = parseToken(request.getToken());
        String jti = signedJWT.getJWTClaimsSet().getJWTID(); // get jti
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime(); // get expiration time
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jti)
                .expiryDate(expirationTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = parseToken(request.getToken());
        String jti = signedJWT.getJWTClaimsSet().getJWTID(); // get jti
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime(); // get expiration time

        if(invalidatedTokenRepository.existsById(jti)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jti)
                .expiryDate(expirationTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);

        String username = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        String token = generateToken(user);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();

    }

    // Sau nay doi thanh verifyToken
    private SignedJWT parseToken(String token) throws ParseException, JOSEException {
        JWSVerifier jwsVerifier = new MACVerifier(signerKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(jwsVerifier);
        if (!verified || !expirationTime.after(new Date())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return signedJWT;

    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("identity-service")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS))) // 1 hour
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(
                header,
                payload
        );
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error signing JWT token", e);
            throw new RuntimeException(e);
        }

    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        }
        return stringJoiner.toString();
    }
}
