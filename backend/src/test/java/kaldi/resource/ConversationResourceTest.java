package com.kaldi.app.resource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kaldi.app.common.dto.UserDto;
import com.kaldi.app.common.enums.Role;
import com.kaldi.app.common.requests.MessageRequest;
import com.kaldi.app.service.conversation.ConversationService;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Principal;
import java.util.UUID;

public class ConversationResourceTest {

    private ConversationResource conversationResource;
    private ConversationService conversationService;
    private SecurityContext securityContext;
    private Principal principal;

    private final UUID conversationUuid = UUID.randomUUID();

    @BeforeEach
    public void setup() {
        conversationService = mock(ConversationService.class);
        securityContext = mock(SecurityContext.class);
        principal = mock(Principal.class);

        conversationResource = new ConversationResource();
        conversationResource.conversationService = conversationService;

        when(securityContext.getUserPrincipal()).thenReturn(principal);
        when(principal.getName()).thenReturn("testUser");
    }

    @Test
    public void testReply_Success_UserRole() throws Exception {
        // Setup MessageRequest with User role (should bypass operator check)
        MessageRequest request = new MessageRequest();
        UserDto userDto = new UserDto();
        userDto.setRole(Role.USER);
        userDto.setUsername("testUser");
        request.setSender(userDto);
        request.setContent("Hello");

        // Mock service success response
        Response expectedResponse = Response.ok("Success").build();
        when(conversationService.reply(eq(conversationUuid), eq(securityContext), eq(request))).thenReturn(expectedResponse);

        Response response = conversationResource.reply(conversationUuid, securityContext, request);

        assertEquals(200, response.getStatus());
        verify(conversationService).reply(conversationUuid, securityContext, request);
    }

    @Test
    public void testReply_Failure_OperatorRole_NotClaimed() throws Exception {
        // Setup MessageRequest with OPERATOR role, simulate not claimed
        MessageRequest request = new MessageRequest();
        UserDto userDto = new UserDto();
        userDto.setRole(Role.OPERATOR);
        userDto.setUsername("operatorUser");
        request.setSender(userDto);
        request.setContent("Hello");

        // Mock service to return error response (simulate not claimed)
        Response errorResponse = Response.status(Response.Status.BAD_REQUEST)
                .entity("Conversation with UUID: " + conversationUuid + " is not claimed by you.")
                .build();

        when(conversationService.reply(eq(conversationUuid), eq(securityContext), eq(request))).thenReturn(errorResponse);

        Response response = conversationResource.reply(conversationUuid, securityContext, request);

        assertEquals(400, response.getStatus());
        assertEquals("Conversation with UUID: " + conversationUuid + " is not claimed by you.", response.getEntity());
        verify(conversationService).reply(conversationUuid, securityContext, request);
    }

    @Test
    public void testReply_ExceptionThrown() throws Exception {
        // Setup MessageRequest (role doesn't matter here)
        MessageRequest request = new MessageRequest();
        UserDto userDto = new UserDto();
        userDto.setRole(Role.USER);
        userDto.setUsername("anyUser");
        request.setSender(userDto);
        request.setContent("Hello");

        // Simulate service throwing exception
        when(conversationService.reply(any(), any(), any()))
                .thenThrow(new RuntimeException("Service failure"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            conversationResource.reply(conversationUuid, securityContext, request);
        });

        assertEquals("Service failure", exception.getMessage());
        verify(conversationService).reply(conversationUuid, securityContext, request);
    }
}
