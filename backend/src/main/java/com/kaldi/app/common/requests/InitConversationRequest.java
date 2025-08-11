package com.kaldi.app.common.requests;

import com.kaldi.app.common.enums.RoomType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class InitConversationRequest extends MessageRequest{

    @NotNull(message ="RoomType cannot be null")
    public RoomType roomType;

    public RoomType getRoomType() {
        return roomType;
    }
}
