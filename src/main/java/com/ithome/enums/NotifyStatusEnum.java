package com.ithome.enums;

public enum NotifyStatusEnum {
    READ(1),
    UNREAD(0);

    private Integer status;  //状态

    public Integer getStatus() {
        return status;
    }

    NotifyStatusEnum(Integer status) {
        this.status = status;
    }
}
