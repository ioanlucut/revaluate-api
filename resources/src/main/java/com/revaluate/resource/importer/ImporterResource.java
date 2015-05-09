package com.revaluate.resource.importer;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.MintExpenseProfileDTO;
import com.revaluate.domain.importer.profile.SpendeeExpenseProfileDTO;
import com.revaluate.importer.ImporterException;
import com.revaluate.importer.ImporterService;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Path(ImporterResource.IMPORTER)
@Component
public class ImporterResource extends Resource {

    public static final String IMPORTER = "importer";
    public static final String IMPORT_MINT = "mintImport";
    public static final String IMPORT_SPENDEE = "spendeeImport";
    public static final String CSV_FILE = "file";

    //-----------------------------------------------------------------
    // Predefined import profiles
    //-----------------------------------------------------------------
    public static final MintExpenseProfileDTO MINT_EXPENSE_PROFILE_DTO = new MintExpenseProfileDTO();
    public static final SpendeeExpenseProfileDTO SPENDEE_EXPENSE_PROFILE_DTO = new SpendeeExpenseProfileDTO();

    @Autowired
    private ImporterService importerService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Path(IMPORT_MINT)
    public Response trace(@NotNull @FormDataParam(CSV_FILE) InputStream stream) throws ImporterException, IOException {
        String received = IOUtils.toString(stream);

        return Responses.respond(Response.Status.OK, received);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Path(IMPORT_MINT)
    public Response importMint(@NotNull @FormDataParam(CSV_FILE) InputStream stream) throws ImporterException {
        List<ExpenseDTO> expenses = importerService.importFrom(new InputStreamReader(stream), MINT_EXPENSE_PROFILE_DTO);

        return Responses.respond(Response.Status.OK, expenses);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Path(IMPORT_SPENDEE)
    public Response importSpendee(@NotNull @FormDataParam(CSV_FILE) InputStream stream) throws ImporterException {
        List<ExpenseDTO> expenses = importerService.importFrom(new InputStreamReader(stream), SPENDEE_EXPENSE_PROFILE_DTO);

        return Responses.respond(Response.Status.OK, expenses);
    }
}