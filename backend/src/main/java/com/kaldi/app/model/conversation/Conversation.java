package com.kaldi.app.model.conversation;

import com.kaldi.app.common.enums.ConversationStatus;
import com.kaldi.app.model.BaseEntity;
import com.kaldi.app.model.room.Room;
import com.kaldi.app.model.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "conversations")
public class Conversation extends BaseEntity {

    @Column(name = "conversation_initiator", nullable = false)
    private String conversationInitiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id")
    private User operator;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConversationStatus status;

    public String getConversationInitiator() {
        return conversationInitiator;
    }

    public Conversation setConversationInitiator(String conversationInitiator) {
        this.conversationInitiator = conversationInitiator;
        return this;
    }

    public User getOperator() {
        return operator;
    }

    public Conversation setOperator(User operator) {
        this.operator = operator;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Conversation setRoom(Room room) {
        this.room = room;
        return this;
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public Conversation setStatus(ConversationStatus status) {
        this.status = status;
        return this;
    }
}
