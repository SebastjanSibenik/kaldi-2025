package com.kaldi.app.resource;

import com.kaldi.app.common.requests.InitConversationRequest;
import com.kaldi.app.common.requests.MessageRequest;
import com.kaldi.app.service.conversation.ConversationService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;
import java.util.UUID;

@Path("api/v1/conversations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConversationResource {

    @Inject
    ConversationService conversationService;

    @POST
    @Path("/{uuid}/claim")
    @RolesAllowed("OPERATOR")
    public Response claim(@PathParam("uuid") UUID conversationUuid, @Context SecurityContext securityContext) throws Exception {
        Principal principal = securityContext.getUserPrincipal();
        return conversationService.claim(conversationUuid, principal);
    }

    @GET
    @Path("/{uuid}/messages")
    @RolesAllowed("OPERATOR")
    public Response getMessages(@PathParam("uuid") UUID conversationUuid, @Context SecurityContext securityContext) throws Exception {
        return conversationService.getMessages(conversationUuid, securityContext);
    }

    @GET
    @RolesAllowed("OPERATOR")
    public Response list() throws Exception {
        return conversationService.list();
    }

    @POST
    @Path("/{uuid}/reply")
    @PermitAll
    public Response reply(@PathParam("uuid") UUID conversationUuid, @Context SecurityContext securityContext, MessageRequest request) throws Exception {
        return conversationService.reply(conversationUuid, securityContext, request);
    }

    @POST
    @Path("/start")
    @PermitAll
    public Response start(InitConversationRequest request) throws Exception {
        return conversationService.start(request);
    }
}
