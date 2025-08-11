package com.kaldi.app.common.adapters;

import com.kaldi.app.common.dto.ConversationDto;
import com.kaldi.app.common.dto.MessageDto;
import com.kaldi.app.common.dto.UserDto;
import com.kaldi.app.common.enums.Role;
import com.kaldi.app.model.conversation.Conversation;
import com.kaldi.app.model.message.Message;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MessageAdapter {

    @Inject
    private ConversationAdapter conversationAdapter;

    public Message toEntity(MessageDto dto, Conversation managedConversation) {
        if (dto == null) {
            return null;
        }
        return new Message()
                .setContent(dto.getContent())
                .setConversation(managedConversation)
                .setUserRole(dto.getUserDto().getRole());
    }

    public MessageDto toDto(Message entity) {
        if (entity == null) {
            return null;
        }

        ConversationDto convDto = conversationAdapter.toDto(entity.getConversation());

        UserDto userDto = new UserDto();
        userDto.setRole(entity.getUserRole());

        if (entity.getUserRole().equals(Role.USER)) {
            userDto.setUsername(entity.getConversation().getCustomerUsername());
        } else {
            userDto.setUsername(entity.getConversation().getOperator().getUsername());
        }

        return new MessageDto()
                .setContent(entity.getContent())
                .setUserDto(userDto)
                .setRoomType(convDto.getRoomType())
                .setConversation(convDto);
    }
}
