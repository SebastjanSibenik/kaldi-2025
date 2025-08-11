package com.kaldi.app.common.adapters;

import com.kaldi.app.common.dto.ConversationDto;
import com.kaldi.app.common.dto.UserDto;
import com.kaldi.app.model.conversation.Conversation;
import com.kaldi.app.model.room.Room;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConversationAdapter {

    public Conversation toEntity(ConversationDto dto, Room managedRoom) {
        if (dto == null) {
            return null;
        }
        return new Conversation()
                .setStatus(dto.getStatus())
                .setRoom(managedRoom)
                .setCustomerUsername(dto.getUserDto().getUsername())
                .setCustomerRole(dto.getUserDto().getRole());
    }

    public ConversationDto toDto(Conversation entity) {
        if (entity == null) {
            return null;
        }

        Room room = entity.getRoom();
        return new ConversationDto()
                .setRoomType(room != null ? room.getRoomType() : null)
                .setUuid(entity.getUuid())
                .setStatus(entity.getStatus())
                .setUserDto(new UserDto()
                        .setUsername(entity.getCustomerUsername())
                        .setRole(entity.getCustomerRole()));
    }
}
