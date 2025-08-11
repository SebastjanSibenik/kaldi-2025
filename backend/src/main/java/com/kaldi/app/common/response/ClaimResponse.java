package com.kaldi.app.common.response;

import com.kaldi.app.common.dto.UserDto;
import com.kaldi.app.common.enums.RoomType;

public class ClaimResponse {

    private String content;
    private RoomType roomType;
    private UserDto userDto;

    public String getContent() {
        return content;
    }

    public ClaimResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public ClaimResponse setRoomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public ClaimResponse setUserDto(UserDto userDto) {
        this.userDto = userDto;
        return this;
    }
}
