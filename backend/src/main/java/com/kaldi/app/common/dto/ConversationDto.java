package com.kaldi.app.common.dto;

import com.kaldi.app.common.enums.ConversationStatus;
import com.kaldi.app.common.enums.RoomType;

import java.util.UUID;

public class ConversationDto {

    private ConversationStatus conversationStatus;
    private RoomType roomType;
    private UserDto userDto;
    private UUID uuid;

    public ConversationStatus getStatus() {
        return conversationStatus;
    }

    public ConversationDto setStatus(ConversationStatus conversationStatus) {
        this.conversationStatus = conversationStatus;
        return this;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public ConversationDto setRoomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public ConversationDto setUserDto(UserDto userDto) {
        this.userDto = userDto;
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ConversationDto setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "ConversationDto{roomType=%s, conversationStatus=%s, operator=%s}",
                roomType,
                conversationStatus,
                userDto
        );
    }
}
