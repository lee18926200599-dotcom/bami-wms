package com.common.base.enums;

public enum FileUploadType {

    FILE("/file", "文件"),
    IMAGE("/image", "图片"),
    VIDEO("/video", "视频");

    private String prefix;

    private String name;

    FileUploadType(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
