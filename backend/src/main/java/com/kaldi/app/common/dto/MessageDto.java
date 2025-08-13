package com.kaldi.app.common.dto;

public class MessageDto {

    private String content;
    private UserDto sender;
    private ConversationDto conversationDto;

    public String getContent() {
        return content;
    }

    public MessageDto setContent(String content) {
        this.content = content;
        return this;
    }

    public UserDto getSender() {
        return sender;
    }

    public MessageDto setSender(UserDto sender) {
        this.sender = sender;
        return this;
    }

    public ConversationDto getConversation() {
        return conversationDto;
    }

    public MessageDto setConversation(ConversationDto conversationDto) {
        this.conversationDto = conversationDto;
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "MessageDto{content='%s', sender=%s, conversation=%s}",
                content,
                sender,
                conversationDto
        );
    }
}
