package com.app.core.resource;

import com.app.core.model.Product;
import com.app.core.repository.ProductRepository;
import com.app.core.security.TokenManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
    
    private ProductRepository productRepository = new ProductRepository();
    
    @GET
    public Response getAllProducts(@HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        List<Product> products = productRepository.getAllProducts();
        return Response.ok(products).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") int id, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        Product product = productRepository.getProductById(id);
        if (product != null) {
            return Response.ok(product).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("/category/{categoryId}")
    public Response getProductsByCategory(@PathParam("categoryId") int categoryId, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        List<Product> products = productRepository.getProductsByCategory(categoryId);
        return Response.ok(products).build();
    }
    
    @POST
    public Response createProduct(Product product, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        int id = productRepository.createProduct(product);
        if (id != -1) {
            product.setId(id);
            return Response.status(Response.Status.CREATED).entity(product).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") int id, Product product, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        product.setId(id);
        if (productRepository.updateProduct(product)) {
            return Response.ok(product).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") int id, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        if (productRepository.deleteProduct(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}