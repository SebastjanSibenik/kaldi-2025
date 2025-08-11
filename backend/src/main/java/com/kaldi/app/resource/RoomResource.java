package com.kaldi.app.resource;

import com.kaldi.app.common.enums.RoomType;
import com.kaldi.app.service.room.RoomService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/v1/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @Inject
    RoomService roomService;

    @GET
    @PermitAll
    @Path("/{roomType}")
    public Response getRoom(@PathParam("roomType") String roomTypeStr) throws Exception {
        return roomService.getByType(RoomType.valueOf(roomTypeStr.toUpperCase()));
    }

    @GET
    @PermitAll
    public Response getRooms() throws Exception {
        return roomService.list();
    }
}
