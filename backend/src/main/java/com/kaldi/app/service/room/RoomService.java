package com.kaldi.app.service.room;

import com.kaldi.app.common.enums.RoomType;
import jakarta.ws.rs.core.Response;

public interface RoomService {

    Response getByType(RoomType roomType) throws Exception;

    Response list() throws Exception;
}
