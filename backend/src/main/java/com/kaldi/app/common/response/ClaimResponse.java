package com.kaldi.app.common.response;

import com.kaldi.app.common.dto.UserDto;
import com.kaldi.app.common.enums.RoomType;

public class ClaimResponse {

    private String content;
    private UserDto sender;
    private RoomType roomType;

    public String getContent() {
        return content;
    }

    public ClaimResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public UserDto getSender() {
        return sender;
    }

    public ClaimResponse setSender(UserDto sender) {
        this.sender = sender;
        return this;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public ClaimResponse setRoomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }
}
