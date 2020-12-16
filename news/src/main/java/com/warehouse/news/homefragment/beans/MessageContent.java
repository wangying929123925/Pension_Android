package com.warehouse.news.homefragment.beans;

public class MessageContent {

    private Long id;
    private int status;
    private String messageTopic;
    private String messageTag;
    private String messageBody;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessageTopic() {
        return messageTopic;
    }

    public void setMessageTopic(String messageTopic) {
        this.messageTopic = messageTopic;
    }

    public String getMessageTag() {
        return messageTag;
    }

    public void setMessageTag(String messageTag) {
        this.messageTag = messageTag;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public static class MessageBody {
        private String userId;
        private MsgBodyDto msgBodyDto;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public MsgBodyDto getMsgBodyDto() {
            return msgBodyDto;
        }

        public void setMsgBodyDto(MsgBodyDto msgBodyDto) {
            this.msgBodyDto = msgBodyDto;
        }

        public static class MsgBodyDto {
            private int status;
            private String statusMsg;
            private Long taskId;

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
        }
        }
    }
