package com.revaluate.resource.category;

import com.revaluate.category.exception.CategoryException;
import com.revaluate.category.service.CategoryService;
import com.revaluate.core.annotations.PaymentRequired;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path(CategoryResource.CATEGORIES)
@Component
public class CategoryResource extends Resource {

    //-----------------------------------------------------------------
    // Path
    //-----------------------------------------------------------------
    public static final String CATEGORIES = "categories";

    //-----------------------------------------------------------------
    // Sub paths
    //-----------------------------------------------------------------
    private static final String IS_UNIQUE_CATEGORY = "isUniqueCategory";
    private static final String RETRIEVE_EXPENSES = "retrieve";
    private static final String REMOVE_CATEGORY = "{categoryId}";
    private static final String BULK_CREATE = "bulkCreate";
    private static final String SETUP_BULK_CREATE_CATEGORIES = "setupBulkCreateCategories";
    private static final String BULK_DELETE = "bulkDelete";

    //-----------------------------------------------------------------
    // Path params
    //-----------------------------------------------------------------
    private static final String NAME = "name";
    private static final String CATEGORY_ID = "categoryId";

    //-----------------------------------------------------------------
    // Json keys
    //-----------------------------------------------------------------
    public static final String IS_UNIQUE_CATEGORY_JSON_KEY = "isUniqueCategory";

    @Autowired
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(IS_UNIQUE_CATEGORY)
    public Response isUnique(@QueryParam(NAME) @NotBlank @Size(min = 2) String name) throws CategoryException {
        Map<String, Boolean> response = new HashMap<>();
        response.put(IS_UNIQUE_CATEGORY_JSON_KEY, categoryService.isUnique(name, getCurrentUserId()));

        return Responses.respond(Response.Status.OK, response);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @PaymentRequired
    public Response create(@Valid CategoryDTO categoryDTO) throws CategoryException {
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, createdCategoryDTO);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @PaymentRequired
    public Response update(@Valid CategoryDTO categoryDTO) throws CategoryException {
        CategoryDTO createdCategoryDTO = categoryService.update(categoryDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, createdCategoryDTO);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REMOVE_CATEGORY)
    @PaymentRequired
    public Response remove(@PathParam(CATEGORY_ID) @NotNull int categoryId) throws CategoryException {
        categoryService.remove(categoryId, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "Category successfully removed");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(RETRIEVE_EXPENSES)
    public Response retrieveAll() {
        List<CategoryDTO> allCategoriesFor = categoryService.findAllCategoriesFor(getCurrentUserId());

        return Responses.respond(Response.Status.OK, allCategoriesFor);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(BULK_CREATE)
    public Response bulkCreate(@Valid List<CategoryDTO> categoryDTOs) throws CategoryException {
        List<CategoryDTO> all = categoryService.bulkCreate(categoryDTOs, getCurrentUserId());

        return Responses.respond(Response.Status.OK, all);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(SETUP_BULK_CREATE_CATEGORIES)
    public Response setupBulkCreateCategories(@Valid List<CategoryDTO> categoryDTOs) throws CategoryException {
        List<CategoryDTO> all = categoryService.setupBulkCreateCategories(categoryDTOs, getCurrentUserId());

        return Responses.respond(Response.Status.OK, all);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(BULK_DELETE)
    public Response bulkDelete(@Valid List<CategoryDTO> categoryDTOs) throws CategoryException {
        categoryService.bulkDelete(categoryDTOs, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "Bulk delete action complete");
    }

}