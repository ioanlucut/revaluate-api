package com.revaluate.category.resource;

import com.revaluate.category.domain.CategoryDTO;
import com.revaluate.category.exception.CategoryException;
import com.revaluate.category.service.CategoryService;
import com.revaluate.core.resource.Resource;
import com.revaluate.core.resource.Responses;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    private static final String CREATE_CATEGORY = "create";
    private static final String REMOVE_CATEGORY = "remove/{categoryId}";

    //-----------------------------------------------------------------
    // Path params
    //-----------------------------------------------------------------
    private static final String NAME = "name";
    private static final String CATEGORY_ID = "categoryId";

    @Autowired
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(IS_UNIQUE_CATEGORY)
    public Response isUnique(@QueryParam(NAME) @NotBlank @Size(min = 2) String name) throws CategoryException {
        if (!categoryService.isUnique(name)) {
            throw new CategoryException("Category name is not unique");
        }

        return Responses.respond(Response.Status.OK, "Category name is unique.");
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(CREATE_CATEGORY)
    public Response create(@Valid CategoryDTO categoryDTO) throws CategoryException {
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, createdCategoryDTO);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REMOVE_CATEGORY)
    public Response remove(@PathParam(CATEGORY_ID) @NotBlank int categoryId) throws CategoryException {
        categoryService.remove(categoryId, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "Category successfully removed");
    }
}