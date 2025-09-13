package com.app.core.resource;

import com.app.core.model.Category;
import com.app.core.repository.CategoryRepository;
import com.app.core.security.TokenManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {
    
    private CategoryRepository categoryRepository = new CategoryRepository();
    
    @GET
    public Response getAllCategories(@HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        List<Category> categories = categoryRepository.getAllCategories();
        return Response.ok(categories).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getCategoryById(@PathParam("id") int id, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        Category category = categoryRepository.getCategoryById(id);
        if (category != null) {
            return Response.ok(category).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @POST
    public Response createCategory(Category category, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        int id = categoryRepository.createCategory(category);
        if (id != -1) {
            category.setId(id);
            return Response.status(Response.Status.CREATED).entity(category).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateCategory(@PathParam("id") int id, Category category, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        category.setId(id);
        if (categoryRepository.updateCategory(category)) {
            return Response.ok(category).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") int id, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        if (categoryRepository.deleteCategory(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}