package com.revaluate.converter;

import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailTokenRepository;
import com.revaluate.domain.email.SendTo;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Converter
public final class EmailTokenConverter {

    @Autowired
    private EmailTokenRepository emailTokenRepository;

    @Converter
    public EmailToken toEmailToken(SendTo sendTo, Exchange exchange) throws Exception {
        EmailToken emailToken = emailTokenRepository.findOne(sendTo.getEmailTokenId());
        emailToken.setValidated(Boolean.TRUE);

        return emailToken;
    }


    @Converter
    public SendTo toSendTo(EmailToken emailToken, Exchange exchange) throws Exception {
        SendTo sendTo = new SendTo();
        sendTo.setId(emailToken.getUser().getId());
        sendTo.setFirstName(emailToken.getUser().getFirstName());
        sendTo.setLastName(emailToken.getUser().getLastName());
        sendTo.setEmail(emailToken.getUser().getEmail());
        sendTo.setEmailToken(emailToken.getToken());
        sendTo.setEmailTokenId(emailToken.getId());
        sendTo.setEmailType(emailToken.getEmailType());

        return sendTo;
    }


}
