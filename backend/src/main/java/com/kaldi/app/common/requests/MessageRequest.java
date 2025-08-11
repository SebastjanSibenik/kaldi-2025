package com.kaldi.app.common.requests;

import com.kaldi.app.common.dto.UserDto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageRequest {

    private String content;

    private UserDto userDto;  // holds username and role

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
