package com.kaldi.app.common.requests;

import com.kaldi.app.common.dto.UserDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class MessageRequest {

    @NotBlank(message = "Content cannot be null")
    private String content;

    @Valid
    @NotNull(message = "UserDto cannot be null")
    private UserDto userDto;

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
