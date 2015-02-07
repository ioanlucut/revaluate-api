package com.revaluate.core.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public class JwtParser implements JwtConstants {

    public static long parseToken(String token) throws JwtException {
        try {
            return tryToParseFromToken(token);
        } catch (JwtException ex) {
            throw new JwtException("JWT invalid", ex);
        }
    }

    private static long tryToParseFromToken(String token) throws JwtException {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(token);
        } catch (ParseException e) {
            throw new JwtException("The jwt is invalid");
        }

        JWSVerifier verifier = new MACVerifier(SHARED_SECRET);

        try {
            boolean isValid = signedJWT.verify(verifier);
            if (!isValid) {
                throw new JwtException("Jwt is not valid");
            }
        } catch (JOSEException e) {
            throw new JwtException("Jwt could not be verified");
        }
        String subject;
        try {
            subject = signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new JwtException("Subject could not be obtained");
        }

        try {
            return Long.parseLong(subject);
        } catch (NumberFormatException ex) {
            throw new JwtException("Subject could not be parsed");
        }
    }
}
