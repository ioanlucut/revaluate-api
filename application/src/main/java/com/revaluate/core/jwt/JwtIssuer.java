package com.revaluate.core.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.util.Date;

public class JwtIssuer implements JwtConstants {

    public static String createTokenForUserWithId(Long userId) throws JOSEException, ParseException {
        // Generate random 256-bit (32-byte) shared secret
        // Create HMAC signer
        JWSSigner signer = new MACSigner(SHARED_SECRET);

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet();
        claimsSet.setSubject(String.valueOf(userId));
        claimsSet.setIssueTime(new Date());

        // One month later
        claimsSet.setExpirationTime(new LocalDate().plusMonths(1).toDate());
        claimsSet.setIssuer(ISSUER);

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        // Apply the HMAC
        signedJWT.sign(signer);

        // To serialize to compact form, produces something like
        // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
        return signedJWT.serialize();
    }
}
