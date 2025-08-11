package com.kaldi.app.common.adapters;

import com.kaldi.app.common.dto.RoomDto;
import com.kaldi.app.common.enums.RoomType;
import com.kaldi.app.model.room.Room;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.DiscriminatorValue;

@ApplicationScoped
public class RoomAdapter {

    public Room toEntity(RoomDto dto) {
        if (dto == null) {
            return null;
        }
        return new Room()
                .setDescription(dto.getDescription())
                .setName(dto.getName())
                .setRoomType(dto.getRoomType());
    }

    public RoomDto toDto(Room entity) {
        if (entity == null) {
            return null;
        }

        return new RoomDto()
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setRoomType(getRoomTypeFromDiscriminator(entity));
    }

    private RoomType getRoomTypeFromDiscriminator(Room entity) {
        DiscriminatorValue discriminatorValue =
                entity.getClass().getAnnotation(DiscriminatorValue.class);

        if (discriminatorValue != null) {
            try {
                return RoomType.valueOf(discriminatorValue.value());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
