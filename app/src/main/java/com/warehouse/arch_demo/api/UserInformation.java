package com.warehouse.arch_demo.api;

import java.util.List;

public class UserInformation {

    /**
     * code : 200
     * message : 操作成功
     * result : {"userId":"782517846944000001","roles":[{"id":"781805692947268608","version":null,"creator":null,"creatorId":null,"createdTime":null,"lastOperator":null,"lastOperatorId":null,"updateTime":null,"pageNum":null,"pageSize":null,"orderBy":null,"roleCode":"user_watcher","roleName":"用户值机员","status":"DISABLE","remark":"账户由\u201c用户管理员\u201d创建，配置报修、维修维护工单验收、维修维护任务评价等权限"}]}
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
         * userId : 782517846944000001
         * roles : [{"id":"781805692947268608","version":null,"creator":null,"creatorId":null,"createdTime":null,"lastOperator":null,"lastOperatorId":null,"updateTime":null,"pageNum":null,"pageSize":null,"orderBy":null,"roleCode":"user_watcher","roleName":"用户值机员","status":"DISABLE","remark":"账户由\u201c用户管理员\u201d创建，配置报修、维修维护工单验收、维修维护任务评价等权限"}]
         */

        private String id;
        private List<RolesBean> roles;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<RolesBean> getRoles() {
            return roles;
        }

        public void setRoles(List<RolesBean> roles) {
            this.roles = roles;
        }

        public static class RolesBean {
            /**
             * id : 781805692947268608
             * version : null
             * creator : null
             * creatorId : null
             * createdTime : null
             * lastOperator : null
             * lastOperatorId : null
             * updateTime : null
             * pageNum : null
             * pageSize : null
             * orderBy : null
             * roleCode : user_watcher
             * roleName : 用户值机员
             * status : DISABLE
             * remark : 账户由“用户管理员”创建，配置报修、维修维护工单验收、维修维护任务评价等权限
             */

            private String roleName;
            private String roleCode;

            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }

            public String getRoleCode() {
                return roleCode;
            }

            public void setRoleCode(String roleCode) {
                this.roleCode = roleCode;
            }
        }
    }
}