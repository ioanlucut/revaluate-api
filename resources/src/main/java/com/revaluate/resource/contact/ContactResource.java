package com.revaluate.resource.contact;

import com.revaluate.core.annotations.Public;
import com.revaluate.domain.contact.ContactDTO;
import com.revaluate.email.ContactService;
import com.revaluate.email.SendEmailException;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ContactResource.CONTACT)
@Component
public class ContactResource extends Resource {

    public static final String CONTACT = "contact";

    @Autowired
    private ContactService contactService;

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response isUnique(@Valid ContactDTO contactDTO) throws SendEmailException {
        contactService.sendEmailFrom(contactDTO);

        return Responses.respond(Response.Status.OK, "Email sent.");
    }
}