package com.kaldi.app.service.conversation;

import com.kaldi.app.common.requests.InitConversationRequest;
import com.kaldi.app.common.requests.MessageRequest;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;
import java.util.UUID;

public interface ConversationService {

    Response claim(UUID conversationUuid, Principal principal) throws Exception;

    Response getMessages(UUID conversationUuid, SecurityContext securityContext) throws Exception;

    Response list() throws Exception;

    Response reply(UUID conversationUuid, SecurityContext context, MessageRequest request) throws Exception;

    Response start(InitConversationRequest request) throws Exception;
}
