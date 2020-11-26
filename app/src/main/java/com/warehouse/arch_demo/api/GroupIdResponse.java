package com.warehouse.arch_demo.api;

public class GroupIdResponse {

    /**
     * code : 0
     * message : string
     * result : {"createdTime":"2020-03-23T04:33:26.183Z","creator":"string","creatorId":0,"email":"string","groupId":0,"groupName":"string","id":0,"isChangedPwd":0,"lastLoginIp":"string","lastLoginLocation":"string","lastLoginTime":"2020-03-23T04:33:26.183Z","lastOperator":"string","lastOperatorId":0,"loginName":"string","loginPwd":"string","mobileNo":"string","orderBy":"string","pageNum":0,"pageSize":0,"pwdErrorCount":0,"pwdErrorTime":"2020-03-23T04:33:26.183Z","remark":"string","roleCode":"string","roleId":0,"roleName":"string","salt":"string","status":"string","type":"string","updateTime":"2020-03-23T04:33:26.183Z","userCode":"string","userName":"string","userSource":"string","version":0}
     */

    private String code;
    private String message;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * createdTime : 2020-03-23T04:33:26.183Z
         * creator : string
         * creatorId : 0
         * email : string
         * groupId : 0
         * groupName : string
         * id : 0
         * isChangedPwd : 0
         * lastLoginIp : string
         * lastLoginLocation : string
         * lastLoginTime : 2020-03-23T04:33:26.183Z
         * lastOperator : string
         * lastOperatorId : 0
         * loginName : string
         * loginPwd : string
         * mobileNo : string
         * orderBy : string
         * pageNum : 0
         * pageSize : 0
         * pwdErrorCount : 0
         * pwdErrorTime : 2020-03-23T04:33:26.183Z
         * remark : string
         * roleCode : string
         * roleId : 0
         * roleName : string
         * salt : string
         * status : string
         * type : string
         * updateTime : 2020-03-23T04:33:26.183Z
         * userCode : string
         * userName : string
         * userSource : string
         * version : 0
         */
        private String groupId;
        private Long id;
        private String userName;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
    }
}
