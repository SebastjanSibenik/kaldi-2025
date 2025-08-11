package com.kaldi.app.model.room;

import com.kaldi.app.common.enums.RoomType;
import com.kaldi.app.model.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "room_type", discriminatorType = DiscriminatorType.STRING)
public class Room extends BaseEntity {

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", insertable = false, updatable = false)
    private RoomType roomType;

    public String getDescription() {
        return description;
    }

    public Room setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public Room setName(String name) {
        this.name = name;
        return this;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Room setRoomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }
}
