package com.app.core.resource;

import com.app.core.config.AppConfig;
import com.app.core.model.UserRegistrationRequest;
import com.app.core.repository.UserRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRegistrationResource {

    private UserRepository userRepository = new UserRepository();

    @POST
    @Path("/user")
    public Response registerUser(UserRegistrationRequest registrationRequest) {
        try {
            // Validazione dei campi obbligatori
            if (registrationRequest.getUsername() == null || registrationRequest.getUsername().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"Username è obbligatorio\"}")
                        .build();
            }

            if (registrationRequest.getPassword() == null || registrationRequest.getPassword().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"Password è obbligatoria\"}")
                        .build();
            }

            if (registrationRequest.getEmail() == null || registrationRequest.getEmail().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"Email è obbligatoria\"}")
                        .build();
            }

            // Verifica se l'username esiste già
            if (userRepository.usernameExists(registrationRequest.getUsername())) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"error\":\"Username già in uso\"}")
                        .build();
            }

            // Verifica se l'email esiste già
            if (userRepository.emailExists(registrationRequest.getEmail())) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"error\":\"Email già in uso\"}")
                        .build();
            }

            // Registra l'utente
            String result = userRepository.registerUser(
                    registrationRequest.getUsername(),
                    registrationRequest.getPassword(),
                    registrationRequest.getEmail(),
                    registrationRequest.getPhone(),
                    registrationRequest.getRole() != null ? registrationRequest.getRole() : "USER"
            );
            // Controlla se la registrazione è abilitata
            if (!AppConfig.isRegistrationEnabled()) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\":\"Registrazione disabilitata\"}")
                        .build();
            }
            if (result.contains("registrato correttamente")) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\":\"Utente registrato correttamente\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"" + result + "\"}")
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Errore durante la registrazione: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/user/{username}")
    public Response deleteUser(@PathParam("username") String username) {
        try {
            if (username == null || username.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"Username è obbligatorio\"}")
                        .build();
            }

            if (userRepository.deleteUser(username)) {
                return Response.status(Response.Status.OK)
                        .entity("{\"message\":\"Utente eliminato correttamente\"}")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Utente non trovato\"}")
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Errore durante l'eliminazione: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}
