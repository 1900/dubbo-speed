package com.funtime.dubbo.base;

public enum PublicResultConstant {

    /**
     * 异常
     */
    FAILED("90000001", "system error"),
    /**
     * 成功
     */
    SUCCESS("0000", "success"),
    /**
     * 未登录/token过期
     */
    UNAUTHORIZED("90000002", "token has expired"),
    /**
     * 失败
     */
    ERROR("90000000", "operate failure"),
    /**
     * 失败
     */
    PARAM_ERROR("90000003", "parameter error"),

    /**
     * 用户名或密码错误
     */
    INVALID_USERNAME_PASSWORD("10000003", "user or password error"),
    /**
     *
     */
    INVALID_RE_PASSWORD("10000010", "invalid password"),
    /**
     * 用户名或密码错误
     */
    INVALID_PASSWORD("10000009", "invalid password"),
    /**
     * 用户名重复
     */
    USERNAME_ALREADY_IN("10000002", "username already"),
    /**
     * 用户不存在
     */
    INVALID_USER("10000001", "invalid user"),
    /**
     * 角色不存在
     */
    INVALID_ROLE("10000004", "invalid role"),

    /**
     * 角色不存在
     */
    ROLE_USER_USED("10000008", "角色使用中，不可删除"),

    /**
     * 参数错误-已存在
     */
    INVALID_PARAM_EXIST("10000005", "invalid param exist"),
    /**
     * 参数错误
     */
    INVALID_PARAM_EMPTY("10000006", "invalid param empty"),
    /**
     * 没有权限
     */
    USER_NO_PERMITION("10000007", "user no permition"),
    /**
     * 校验码错误
     */
    VERIFY_PARAM_ERROR("10000011", "verify param error"),
    /*
     * 校验码过期
     */
    VERIFY_PARAM_PASS("10000012", "verify param pass"),

    /**
     * 用户没有添加、删除评论或回复的权限
     */
    USER_NO_AUTHORITY("10000013","user no authority"),

    /**
     * 用户没有添加、删除评论或回复的权限
     */
    MOBILE_ERROR("10000014","mobile error") ,
    /**
     * 数据更新或增加失败
     */
    DATA_ERROR("10000015","data error"),

    /**
     * 没有找到相关记录
     */
    RESULT_EMPTY("10000016","result empty"),

    INSUFFICIENT_BALANCE_ERROR("10000017","insufficient balance error"),

    ACCOUNT_DIFF_ERROR("10000018","account diff error");

    public String result;
    public String msg;

    PublicResultConstant(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
