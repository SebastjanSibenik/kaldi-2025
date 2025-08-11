package com.kaldi.app.common.dto;

import com.kaldi.app.common.enums.RoomType;

public class MessageDto {

    private ConversationDto conversationDto;
    private String content;
    private RoomType roomType;
    private UserDto userDto;

    public ConversationDto getConversation() {
        return conversationDto;
    }

    public MessageDto setConversation(ConversationDto conversationDto) {
        this.conversationDto = conversationDto;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageDto setContent(String content) {
        this.content = content;
        return this;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public MessageDto setRoomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public MessageDto setUserDto(UserDto userDto) {
        this.userDto = userDto;
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "MessageDto{content='%s', conversation=%s, userDto=%s, roomType=%s}",
                content,
                conversationDto,
                userDto,
                roomType
        );
    }
}
