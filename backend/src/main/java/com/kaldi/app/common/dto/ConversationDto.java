package com.kaldi.app.common.dto;

import com.kaldi.app.common.enums.ConversationStatus;
import com.kaldi.app.common.enums.RoomType;

import java.util.UUID;

public class ConversationDto {

    private UUID uuid;
    private UserDto operator;
    private ConversationStatus status;
    private RoomType roomType;

    public UUID getUuid() {
        return uuid;
    }

    public ConversationDto setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UserDto getOperator() {
        return operator;
    }

    public ConversationDto setOperator(UserDto operator) {
        this.operator = operator;
        return this;
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public ConversationDto setStatus(ConversationStatus conversationStatus) {
        this.status = conversationStatus;
        return this;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public ConversationDto setRoomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "ConversationDto{uuid=%s, operator=%s, status=%s, roomType=%s}",
                uuid,
                operator,
                status,
                roomType
        );
    }
}
