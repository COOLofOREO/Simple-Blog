package com.pojo;

public class BlogType {

    private int typeId; // 博客类型 id
    private String typeName; // 博客类型名称

    // 构造方法
    public BlogType() {
    }

    public BlogType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    // 获取和设置博客类型的属性
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
