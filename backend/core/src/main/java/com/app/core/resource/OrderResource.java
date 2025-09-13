package com.app.core.resource;

import com.app.core.model.Order;
import com.app.core.repository.OrderRepository;
import com.app.core.security.TokenManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    
    private OrderRepository orderRepository = new OrderRepository();
    
    @GET
    public Response getAllOrders(@HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        List<Order> orders = orderRepository.getAllOrders();
        return Response.ok(orders).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") int id, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        Order order = orderRepository.getOrderById(id);
        if (order != null) {
            return Response.ok(order).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("/customer/{customerId}")
    public Response getOrdersByCustomer(@PathParam("customerId") int customerId, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        List<Order> orders = orderRepository.getOrdersByCustomer(customerId);
        return Response.ok(orders).build();
    }
    
    @POST
    public Response createOrder(Order order, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        int id = orderRepository.createOrder(order);
        if (id != -1) {
            order.setId(id);
            return Response.status(Response.Status.CREATED).entity(order).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateOrder(@PathParam("id") int id, Order order, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        order.setId(id);
        if (orderRepository.updateOrder(order)) {
            return Response.ok(order).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @PATCH
    @Path("/{id}/status")
    public Response updateOrderStatus(@PathParam("id") int id, 
                                     @FormParam("status") String status,
                                     @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        if (orderRepository.updateOrderStatus(id, status)) {
            return Response.ok().entity("{\"message\":\"Status updated successfully\"}").build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") int id, @HeaderParam("Authorization") String tokenId) {
        // Verifica token
        TokenManager.validateToken(tokenId);
        
        if (orderRepository.deleteOrder(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}