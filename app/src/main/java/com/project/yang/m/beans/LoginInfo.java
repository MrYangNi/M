package com.project.yang.m.beans;

/**
 * @Author: NiYang
 * @Date: 2017/4/8.
 */
public class LoginInfo {
    private String clientId;
    private String username;
    private String password;

    public LoginInfo() {}

    public LoginInfo(String clientId, String username, String password) {
        this.clientId = clientId;
        this.username = username;
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
