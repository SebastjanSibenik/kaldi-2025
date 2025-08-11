package com.kaldi.app.common.dto;

import com.kaldi.app.common.enums.RoomType;

public class RoomDto {

    private String description;
    private String name;
    private RoomType roomType;

    public String getDescription() {
        return description;
    }

    public RoomDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoomDto setName(String name) {
        this.name = name;
        return this;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public RoomDto setRoomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "RoomDto{name='%s', description='%s', roomType=%s}",
                name,
                description,
                roomType
        );
    }
}
