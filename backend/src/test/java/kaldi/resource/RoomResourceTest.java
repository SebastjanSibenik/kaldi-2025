package com.kaldi.app.resource;

import com.kaldi.app.common.enums.RoomType;
import com.kaldi.app.service.room.RoomService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomResourceTest {

    private RoomResource roomResource;
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        roomService = mock(RoomService.class);
        roomResource = new RoomResource();
        roomResource.roomService = roomService;
    }

    @Test
    void getRoom_returnsResponse() throws Exception {
        RoomType roomType = RoomType.TECHNICAL;
        Response expectedResponse = Response.ok("room details").build();

        when(roomService.getByType(roomType)).thenReturn(expectedResponse);

        Response response = roomResource.getRoom(roomType);

        assertEquals(expectedResponse, response);
        verify(roomService).getByType(roomType);
    }

    @Test
    void getRooms_returnsResponse() throws Exception {
        Response expectedResponse = Response.ok("rooms list").build();

        when(roomService.list()).thenReturn(expectedResponse);

        Response response = roomResource.getRooms();

        assertEquals(expectedResponse, response);
        verify(roomService).list();
    }
}
