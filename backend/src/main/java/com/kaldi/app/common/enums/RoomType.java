package com.kaldi.app.common.enums;

public enum RoomType {
    CONVERSATIONAL("CONVERSATIONAL"),
    SERVICES("SERVICES"),
    TECHNICAL("TECHNICAL");

    private final String code;

    RoomType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
