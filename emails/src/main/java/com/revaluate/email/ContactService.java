package com.revaluate.email;

import com.revaluate.domain.contact.ContactDTO;
import com.revaluate.domain.email.MandrillEmailStatus;

import java.util.concurrent.Future;

public interface ContactService {

    Future<MandrillEmailStatus> sendEmailFrom(ContactDTO contactDTO) throws SendEmailException;
}
