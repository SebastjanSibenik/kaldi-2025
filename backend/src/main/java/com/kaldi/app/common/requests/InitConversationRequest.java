package com.kaldi.app.common.requests;

import com.kaldi.app.common.enums.RoomType;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InitConversationRequest extends MessageRequest{

    public RoomType roomType;

    public RoomType getRoomType() {
        return roomType;
    }
}
