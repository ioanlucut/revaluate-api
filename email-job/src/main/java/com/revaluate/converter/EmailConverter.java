package com.revaluate.converter;

import com.revaluate.account.persistence.Email;
import com.revaluate.account.persistence.EmailRepository;
import com.revaluate.domain.email.SendTo;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Converter
public final class EmailConverter {

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private EmailRepository emailRepository;

    @Converter
    public Email toEmail(SendTo sendTo, Exchange exchange) throws Exception {
        Email email = emailRepository.findOne(sendTo.getEmailId());
        email.setTokenValidated(Boolean.TRUE);

        return email;
    }

    @Converter
    public SendTo toSendTo(Email email, Exchange exchange) throws Exception {

        return dozerBeanMapper.map(email, SendTo.class);
    }

}
