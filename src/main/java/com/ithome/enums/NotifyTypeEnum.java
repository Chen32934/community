package com.ithome.enums;

public enum NotifyTypeEnum {
    Reply_Questions(1, "回复了问题"),
    Reply_Comment(2, "回复了评论");

    private Integer type;
    private String name;

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotifyTypeEnum(Integer status, String name) {
        this.type = status;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotifyTypeEnum notifyTypeEnum : NotifyTypeEnum.values()) {
            if (notifyTypeEnum.getType() == type) {
                return notifyTypeEnum.getName();
            }
        }
        return " ";
    }


}
