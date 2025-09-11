package com.app.core.resource;

import com.app.core.model.Token;
import com.app.core.model.User;
import com.app.core.model.UserCredentials;
import com.app.core.repository.UserRepository;
import com.app.core.security.TokenManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    @POST
    @Path("/login")
    public Response login(@Context HttpServletRequest request) {
        try {
            String contentType = request.getContentType();
            String username = null;
            String password = null;
            
            if (contentType != null && contentType.contains(MediaType.APPLICATION_JSON)) {
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = null;
                try {
                    InputStream inputStream = request.getInputStream();
                    if (inputStream != null) {
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        char[] charBuffer = new char[128];
                        int bytesRead = -1;
                        while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                            stringBuilder.append(charBuffer, 0, bytesRead);
                        }
                    }
                } catch (Exception ex) {
                    throw ex;
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception ex) {
                            throw ex;
                        }
                    }
                }
                
                String body = stringBuilder.toString();
                UserCredentials credentials = objectMapper.readValue(body, UserCredentials.class);
                username = credentials.getUsername();
                password = credentials.getPassword();
            } else {
                username = request.getParameter("username");
                password = request.getParameter("password");
            }
            
            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"Username e password sono obbligatori\"}")
                        .build();
            }
            
            UserRepository repo = new UserRepository();
            User user = repo.authenticate(username, password);
            
            if (user != null) {
                Token token = TokenManager.createToken(user.getId());
                return Response.ok()
                        .entity("{\"token\":\"" + token.getTokenId() + "\"}")
                        .build();
            }
            
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Credenziali non valide\"}")
                    .build();
                    
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Errore interno del server\"}")
                    .build();
        }
    }
    
    @GET
    @Path("/secure-data")
    public Response getSecureData(@HeaderParam("Authorization") String tokenId) {
        Token token = TokenManager.validateToken(tokenId);
        
        if (token != null) {
            return Response.ok("{\"data\":\"Dati sensibili protetti\"}").build();
        }
        
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}