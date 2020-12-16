package com.warehouse.news.homefragment.beans;

import java.util.List;

public class OrderListResponse {

    /**
     * msg : success
     * total : 5
     * code : 0
     * rows : [{"searchValue":null,"createBy":null,"createTime":"2020-11-20 16:51:14","updateBy":null,"updateTime":null,"remark":null,"beginTime":null,"endTime":null,"params":{},"id":"2","clientId":"0","serverId":"11","employeeId":"1","taskState":-1,"taskType":0,"createdBy":"1","endedTime":1606723259000,"endedBy":"1"},{"searchValue":null,"createBy":null,"createTime":"2020-11-20 16:54:48","updateBy":null,"updateTime":null,"remark":null,"beginTime":null,"endTime":null,"params":{},"id":"3","clientId":"0","serverId":"11","employeeId":"1","taskState":0,"taskType":0,"createdBy":"1","endedTime":null,"endedBy":"1"},{"searchValue":null,"createBy":null,"createTime":"2020-12-09 21:30:03","updateBy":null,"updateTime":null,"remark":null,"beginTime":null,"endTime":null,"params":{},"id":"802aae59b5ca4dc3a22","clientId":"842164649578202112","serverId":"838497501970433024","employeeId":null,"taskState":0,"taskType":0,"createdBy":"智慧养老平台超级管理员","endedTime":null,"endedBy":null},{"searchValue":null,"createBy":null,"createTime":"2020-12-09 21:38:51","updateBy":null,"updateTime":null,"remark":null,"beginTime":null,"endTime":null,"params":{},"id":"a315c4d1a1584a45b10","clientId":"842164649578202112","serverId":"838497501970433024","employeeId":null,"taskState":0,"taskType":0,"createdBy":"智慧养老平台超级管理员","endedTime":null,"endedBy":null},{"searchValue":null,"createBy":null,"createTime":"2020-12-10 10:09:52","updateBy":null,"updateTime":null,"remark":null,"beginTime":null,"endTime":null,"params":{},"id":"d1a6d144c99b42ad99d","clientId":"842164649578202112","serverId":"838497501970433024","employeeId":null,"taskState":0,"taskType":0,"createdBy":"智慧养老平台超级管理员","endedTime":null,"endedBy":null}]
     * pageNum : 1
     */

    private String msg;
    private String total;
    private int code;
    private int pageNum;
    private List<AlarmOrderBean> rows;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<AlarmOrderBean> getRows() {
        return rows;
    }

    public void setRows(List<AlarmOrderBean> rows) {
        this.rows = rows;
    }

    public class AlarmOrderBean {

        /**
         * searchValue : null
         * createBy : null
         * createTime : 2020-11-20 16:51:14
         * updateBy : null
         * updateTime : null
         * remark : null
         * beginTime : null
         * endTime : null
         * params : {}
         * id : 2
         * clientId : 0
         * serverId : 11
         * employeeId : 1
         * taskState : -1
         * taskType : 0
         * createdBy : 1
         * endedTime : 1606723259000
         * endedBy : 1
         */

        private String searchValue;
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private String beginTime;
        private String endTime;
        private String id;
        private String clientId;
        private String serverId;
        private String employeeId;
        private int taskState;
        private int taskType;
        private String createdBy;
        private String endedTime;
        private String endedBy;

        public String getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(String searchValue) {
            this.searchValue = searchValue;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getServerId() {
            return serverId;
        }

        public void setServerId(String serverId) {
            this.serverId = serverId;
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

        public int getTaskState() {
            return taskState;
        }

        public void setTaskState(int taskState) {
            this.taskState = taskState;
        }

        public int getTaskType() {
            return taskType;
        }

        public void setTaskType(int taskType) {
            this.taskType = taskType;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getEndedTime() {
            return endedTime;
        }

        public void setEndedTime(String endedTime) {
            this.endedTime = endedTime;
        }

        public String getEndedBy() {
            return endedBy;
        }

        public void setEndedBy(String endedBy) {
            this.endedBy = endedBy;
        }
    }
}
