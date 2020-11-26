package com.warehouse.news.homefragment.api;

import com.warehouse.network.beans.TecentBaseResponse;

public class LoginResponse extends TecentBaseResponse {
    /**
     * Retn : 0000
     * Token : d86a7247347bc08175cdf736144f77cf
     * Desc : login success
     * UserIndex : 79
     */

    private String code;
    private String message;
    private LoginResult result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginResult getResult() {
        return result;
    }

    public void setResult(LoginResult result) {
        this.result = result;
    }

    public class LoginResult {
        private String access_token;
        private String token_type;
        private String refresh_token;
        private String timestamp;
        private String loginName;
//        public String expires_in;
//        public String scope;
//        public String jti;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getTimeStamp() {
            return timestamp;
        }

        public void setTimeStamp(String timestamp) {
            this.timestamp = timestamp;
        }
        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }
    }
}
