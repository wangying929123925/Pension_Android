package com.warehouse.news.homefragment.beans;



public class MessageBodyGson {
    private Long userId;
    private MessageEntity.ContentBean.MsgBodyDtoBean msgBodyDto;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public MessageEntity.ContentBean.MsgBodyDtoBean getMsgBodyDto() {
        return msgBodyDto;
    }

    public void setMsgBodyDto(MessageEntity.ContentBean.MsgBodyDtoBean msgBodyDto) {
        this.msgBodyDto = msgBodyDto;
    }

    public static class MsgBodyDtoBean {
        /**
         * status : 2
         * statusMsg : 维修申请提交后，进入审核
         * taskId : 845039733452053504
         * userId : 834872363731387392
         */

        private int status;
        private String statusMsg;
        private Long taskId;
        private Long userId;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusMsg() {
            return statusMsg;
        }

        public void setStatusMsg(String statusMsg) {
            this.statusMsg = statusMsg;
        }

        public Long getTaskId() {
            return taskId;
        }

        public void setTaskId(Long taskId) {
            this.taskId = taskId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
}
