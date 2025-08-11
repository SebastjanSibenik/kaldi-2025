package com.kaldi.app.service.room;

import com.kaldi.app.common.response.BuildResponse;
import com.kaldi.app.common.adapters.RoomAdapter;
import com.kaldi.app.common.dto.RoomDto;
import com.kaldi.app.common.enums.ResponseStatus;
import com.kaldi.app.common.enums.RoomType;
import com.kaldi.app.model.room.Room;
import com.kaldi.app.repository.RoomRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoomServiceImpl implements RoomService {

    private static final Logger LOGGER = Logger.getLogger(RoomServiceImpl.class);

    @Inject
    private BuildResponse buildResponse;

    @Inject
    private RoomAdapter roomAdapter;

    @Inject
    private RoomRepository roomRepository;

    @Override
    public Response getByType(RoomType roomType) {
        LOGGER.debug("GET /rooms/{roomType} operation started.");
        try {
            Room room = roomRepository.getByType(roomType);
            return buildResponse.createResponse(ResponseStatus.SUCCESS, roomAdapter.toDto(room));
        } catch (Exception e) {
            LOGGER.errorf(e, "Exception during GET room by type: %s", roomType);
            throw e;
        }
    }

    @Override
    public Response list() {
        LOGGER.debug("GET /rooms operation started.");
        try {
            List<RoomDto> roomDtoList = roomRepository.list()
                    .stream()
                    .map(roomAdapter::toDto)
                    .collect(Collectors.toList());
            return buildResponse.createResponse(ResponseStatus.SUCCESS, roomDtoList);
        } catch (Exception e) {
            LOGGER.error("Exception during list rooms.", e);
            throw e;
        }
    }
}
