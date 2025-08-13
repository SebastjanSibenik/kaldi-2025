package com.kaldi.app.model.message;

import com.kaldi.app.common.enums.Role;
import com.kaldi.app.model.BaseEntity;
import com.kaldi.app.model.conversation.Conversation;
import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

    @Column(name = "content", nullable = false, updatable = false)
    private String content; // message data

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation; // connection to the conversation

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, updatable = false)
    private Role userRole; // information about the user who sent the message

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public Message setConversation(Conversation conversation) {
        this.conversation = conversation;
        return this;
    }

    public Role getUserRole() {
        return userRole;
    }

    public Message setUserRole(Role userRole) {
        this.userRole = userRole;
        return this;
    }
}
