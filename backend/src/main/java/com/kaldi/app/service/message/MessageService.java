package com.kaldi.app.service.message;

import com.kaldi.app.common.enums.Role;
import com.kaldi.app.model.conversation.Conversation;
import com.kaldi.app.model.message.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    Message createMessageAndSave(Conversation conversation, Role userRole, String content) throws Exception;

    Message getConversationFirstMessage(UUID conversationUuid) throws Exception;

    List<Message> getConversationMessages(UUID conversationUuid) throws Exception;
}
