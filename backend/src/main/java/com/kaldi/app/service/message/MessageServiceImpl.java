package com.kaldi.app.service.message;

import com.kaldi.app.common.enums.Role;
import com.kaldi.app.model.conversation.Conversation;
import com.kaldi.app.model.message.Message;
import com.kaldi.app.repository.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = Logger.getLogger(MessageServiceImpl.class);

    @Inject
    private MessageRepository messageRepository;

    @Override
    @Transactional
    public Message createMessageAndSave(Conversation conversation, Role userRole, String content) {
        Message message = new Message()
                .setConversation(conversation)
                .setUserRole(userRole)
                .setContent(content);

        messageRepository.persist(message);
        return message;
    }

    @Override
    public Message getFirstMessage(UUID conversationUuid) {
        LOGGER.debug("GET first message operation.");
        try {
            return messageRepository.getFirstMessage(conversationUuid);
        } catch (Exception e) {
            LOGGER.errorf(e, "Exception during GET first message operation.");
            return null;
        }
    }

    @Override
    public List<Message> getConversationMessages(UUID conversationUuid) {
        LOGGER.debug("GET first message operation.");
        try {
            return messageRepository.getConversationMessages(conversationUuid);
        } catch (Exception e) {
            LOGGER.errorf(e, "Exception during GET first message operation.");
            return null;
        }
    }
}
