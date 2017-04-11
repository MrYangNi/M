package com.project.yang.m.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author: NiYang
 * @Date: 2017/4/8.
 */
public class UserAuth {
    private Long userId;
    private String name;
    private String username;
    private String authToken;

    public UserAuth() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String authToken) {
        this.authToken = authToken;
    }

    public String getToken() {
        return this.authToken;
    }

    @Override
    public String toString() {
        return "LoginInfoRsp{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
