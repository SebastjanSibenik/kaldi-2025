package com.kaldi.app.common.adapters;

import com.kaldi.app.common.dto.ConversationDto;
import com.kaldi.app.common.dto.UserDto;
import com.kaldi.app.model.conversation.Conversation;
import com.kaldi.app.model.user.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConversationAdapter {

    public ConversationDto toDto(Conversation entity) {
        if (entity == null) {
            return null;
        }

        ConversationDto dto = new ConversationDto()
                .setRoomType(entity.getRoom() != null ? entity.getRoom().getRoomType() : null)
                .setUuid(entity.getUuid())
                .setStatus(entity.getStatus());

        User operator = entity.getOperator();
        if (operator != null) {
            dto.setOperator(new UserDto()
                    .setUsername(operator.getUsername())
                    .setRole(operator.getRoleEnum()));
        }

        return dto;
    }
}
