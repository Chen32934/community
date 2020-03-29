package com.ithome.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找到问题不存在！"),
    TARGET_NOT_FOUND(2002,"未选择任何问题或者评论进行回复"),
    NOT_LOGIN(2003,"当前操走需要登陆 请登陆再试试"),
    SRVER_EXCEPTION(2005,"服务器崩溃了。。。。。。。"),
    TYPE_NOT_FOUND(2004,"评论类型为错误或者不存在"),
    COMMENT_NOT_FOUND(2004,"回复的评论不存在"),
    CONTENT_NOT_NULL(2004,"回复内容不能为空"),
    TAG_NOT_NULL(2005,"标签内容不能为空"),
    Notification_NOTALL(2006,"信息不见了"),
    NOTREAD_Notification(2007,"不要读取别人的信息");


    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private String message;
    private Integer code;

    CustomizeErrorCode( Integer code,String message) {
        this.message = message;
        this.code = code;
    }

}

