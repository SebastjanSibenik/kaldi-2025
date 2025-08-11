package com.kaldi.app.service.conversation;

import com.kaldi.app.common.adapters.ConversationAdapter;
import com.kaldi.app.common.adapters.MessageAdapter;
import com.kaldi.app.common.dto.ConversationDto;
import com.kaldi.app.common.dto.MessageDto;
import com.kaldi.app.common.enums.ConversationStatus;
import com.kaldi.app.common.enums.ResponseStatus;
import com.kaldi.app.common.enums.Role;
import com.kaldi.app.common.requests.InitConversationRequest;
import com.kaldi.app.common.response.BuildResponse;
import com.kaldi.app.common.response.ClaimResponse;
import com.kaldi.app.model.conversation.Conversation;
import com.kaldi.app.model.message.Message;
import com.kaldi.app.model.room.Room;
import com.kaldi.app.model.user.User;
import com.kaldi.app.repository.ConversationRepository;
import com.kaldi.app.repository.RoomRepository;
import com.kaldi.app.repository.UserRepository;
import com.kaldi.app.common.requests.MessageRequest;
import com.kaldi.app.service.message.MessageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.logging.Logger;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ConversationServiceImpl implements ConversationService {

    private static final Logger LOGGER = Logger.getLogger(ConversationServiceImpl.class);

    @Inject
    private BuildResponse buildResponse;

    @Inject
    private ConversationRepository conversationRepository;

    @Inject
    private ConversationAdapter conversationAdapter;

    @Inject
    private RoomRepository roomRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private MessageService messageService;

    @Inject
    private MessageAdapter messageAdapter;

    @Override
    @Transactional
    public Response claim(UUID conversationUuid, Principal principal) {
        LOGGER.debugf("POST /claim conversation %s operation started by %s.", conversationUuid, principal.getName());
        try {
            Message message = messageService.getFirstMessage(conversationUuid);
            // 1) Check authorization & authentication
            User operator = userRepository.getByUsername(principal.getName());
            if (operator == null || !operator.isOperator()) {
                return buildResponse.createResponse(ResponseStatus.BAD_REQUEST, "Unauthorized: only operators may claim conversations.");
            }

            // 2) Atomic claim conversation
            boolean claimed = conversationRepository.claimConversation(conversationUuid, operator);
            if (!claimed) {
                return buildResponse.createResponse(ResponseStatus.BAD_REQUEST, String.format("Conversation with UUID: %s does not exist or is already claimed.", conversationUuid));
            }

            // 3) Re-fetch conversation for response DTO
            MessageDto messageDto  = messageAdapter.toDto(message);
            ClaimResponse claimResponse = new ClaimResponse()
                    .setContent(messageDto.getContent())
                    .setUserDto(messageDto.getConversation().getUserDto())
                    .setRoomType(messageDto.getConversation().getRoomType());

            return buildResponse.createResponse(ResponseStatus.SUCCESS, claimResponse);
        } catch (Exception e) {
            LOGGER.error("Exception during claim conversation.", e);
            return buildResponse.createResponse(ResponseStatus.ERROR, "Internal server error.");
        }
    }

    @Override
    @Transactional
    public Response getMessages(UUID conversationUuid, SecurityContext securityContext) {
        LOGGER.debugf("GET /{uuid}/messages operation started.", conversationUuid);
        try {

            Conversation conversation = conversationRepository.getByUuid(conversationUuid);
            if (conversation.getOperator() != null && !conversation.getOperator().getUsername().equals(securityContext.getUserPrincipal().getName())) {
                String errorMessage = "Cannot view this conversation messages.";
                return buildResponse.createResponse(ResponseStatus.BAD_REQUEST, errorMessage);
            }
            List<Message> messages = messageService.getConversationMessages(conversationUuid);
            return buildResponse.createResponse(ResponseStatus.SUCCESS, messages
                    .stream()
                    .map(messageAdapter::toDto)
                    .collect(Collectors.toList()));

        } catch (Exception e) {
            LOGGER.error("Exception during claim conversation.", e);
            return buildResponse.createResponse(ResponseStatus.ERROR, "Internal server error.");
        }
    }

    @Transactional
    public ConversationDto create(InitConversationRequest request, Room room) {
        Conversation conversation = new Conversation()
                .setCustomerUsername(request.getUserDto().getUsername())
                .setCustomerRole(request.getUserDto().getRole())
                .setStatus(ConversationStatus.PENDING)
                .setRoom(room);
        conversationRepository.persist(conversation);
        return conversationAdapter.toDto(conversation);
    }

    @Override
    public Response list() {
        LOGGER.debug("GET /conversations operation started.");
        List<Conversation> conversations = conversationRepository.list();
        return buildResponse.createResponse(ResponseStatus.SUCCESS, conversations
                .stream()
                .map(conversationAdapter::toDto)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public Response reply(UUID conversationUuid, SecurityContext context, MessageRequest request) {
        LOGGER.debugf("POST /conversations/%s/reply operation started.", conversationUuid);
        try {
            Conversation conversation = conversationRepository.getByUuid(conversationUuid);
            if (conversation == null) {
                return buildResponse.createResponse(ResponseStatus.BAD_REQUEST, String.format("Conversation with UUID: %s does not exist.", conversationUuid));
            }

            if (!Role.USER.equals(request.getUserDto().getRole())) {
                User operator = userRepository.getByUsername(context.getUserPrincipal().getName());
                User conversationOperator = conversation.getOperator();
                if (conversationOperator == null || operator == null || !operator.getUuid().equals(conversationOperator.getUuid())) {
                    return buildResponse.createResponse(ResponseStatus.BAD_REQUEST, String.format("Conversation with UUID: %s is not claimed by you.", conversationUuid));
                }
            }

            Message message = messageService.createMessageAndSave(conversation, request.getUserDto().getRole(), request.getContent());
            return buildResponse.createResponse(ResponseStatus.SUCCESS, messageAdapter.toDto(message));
        } catch (Exception e) {
            LOGGER.errorf(e, "Exception during replying to message.");
            return buildResponse.createResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response start(InitConversationRequest request) throws Exception {
        LOGGER.debug("POST /conversation/start operation started.");
        try {
            Room room = roomRepository.getByType(request.getRoomType());
            if (room == null) {
                throw new IllegalArgumentException(String.format("Room not found by type: %s", request.getRoomType()));
            }

            ConversationDto conversationDto = create(request, room);

            Conversation conversation = conversationRepository.getByUuid(conversationDto.getUuid());
            Message message = messageService.createMessageAndSave(conversation, request.getUserDto().getRole(), request.getContent());

            return buildResponse.createResponse(ResponseStatus.SUCCESS, messageAdapter.toDto(message));
        } catch (Exception e) {
            LOGGER.errorf(e, "Exception during post message operation.");
            throw e;
        }
    }
}
