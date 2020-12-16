package com.warehouse.news.homefragment.beans;

import com.warehouse.network.beans.TecentBaseResponse;

public class LoginResponse extends TecentBaseResponse {


    /**
     * msg : success
     * code : 0
     * expire : 43200
     * userId : 1
     * token : 817652bd85f048899492ca7ecc19bb51
     */

    private String msg;
    private String code;
    private String expire;
    private String userId;
    private String token;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
