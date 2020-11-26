package com.warehouse.news.homefragment.api;

public class MessageEntity {

    /**
     * topic : MDMC_TOPIC
     * tag : MDMC_TASK_STATUS_CHANGED
     * content : {"userId":834872363731387392,"msgBodyDto":{"status":2,"statusMsg":"维修申请提交后，进入审核","taskId":845039733452053504,"userId":834872363731387392}}
     */

    private String topic;
    private String tag;
    private ContentBean content;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * userId : 834872363731387392
         * msgBodyDto : {"status":2,"statusMsg":"维修申请提交后，进入审核","taskId":845039733452053504,"userId":834872363731387392}
         */

        private Long userId;
        private MsgBodyDtoBean msgBodyDto;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public MsgBodyDtoBean getMsgBodyDto() {
            return msgBodyDto;
        }

        public void setMsgBodyDto(MsgBodyDtoBean msgBodyDto) {
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
            private String taskId;
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

            public String getTaskId() {
                return taskId;
            }

            public void setTaskId(String taskId) {
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
}
