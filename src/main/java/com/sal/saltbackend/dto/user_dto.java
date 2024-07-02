package com.sal.saltbackend.dto;

import java.util.UUID;

public class user_dto {
    private String nickname;
    private String email;
    private String userUuid;

    public user_dto(String nickname, String email, String userUuid) {
        this.nickname = nickname;
        this.email = email;
        this.userUuid = userUuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }
}
