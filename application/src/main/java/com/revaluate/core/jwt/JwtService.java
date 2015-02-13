package com.revaluate.core.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.revaluate.account.exception.UserNotFoundException;
import com.revaluate.core.ConfigProperties;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.Date;

@Component
public class JwtService {

    @Autowired
    private ConfigProperties configProperties;

    public String createTokenForUserWithId(Integer userId) throws JOSEException, ParseException {
        // Generate random 256-bit (32-byte) shared secret
        // Create HMAC signer
        JWSSigner signer = new MACSigner(configProperties.getShared());

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet();
        claimsSet.setSubject(String.valueOf(userId));
        claimsSet.setIssueTime(new Date());

        // One month later
        claimsSet.setExpirationTime(new LocalDate().plusMonths(1).toDate());
        claimsSet.setIssuer(configProperties.getIssuer());

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        // Apply the HMAC
        signedJWT.sign(signer);

        // To serialize to compact form, produces something like
        // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
        return signedJWT.serialize();
    }

    public int parseToken(String token) throws JwtException {
        try {
            return tryToParseFromToken(token);
        } catch (JwtException ex) {
            throw new JwtException("JWT invalid", ex);
        }
    }

    private int tryToParseFromToken(String token) throws JwtException {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(token);
        } catch (ParseException e) {
            throw new JwtException("The jwt is invalid");
        }

        JWSVerifier verifier = new MACVerifier(configProperties.getShared());

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
            return Integer.parseInt(subject);
        } catch (NumberFormatException ex) {
            throw new JwtException("Subject could not be parsed");
        }
    }

    public Response returnJwtResponse(Integer id, Object entity) {
        try {
            String jwtToken = createTokenForUserWithId(id);

            return Response
                    .status(Response.Status.OK)
                    .entity(entity)
                    .header(configProperties.getAuthTokenHeaderKey(), jwtToken).build();
        } catch (JOSEException | ParseException ex) {
            throw new UserNotFoundException("Invalid email or password");
        }
    }
}
