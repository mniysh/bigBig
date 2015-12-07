package com.ms.ebangw.bean;

/**
 * 系统消息
 * User: WangKai(123940232@qq.com)
 * 2015-12-07 16:33
 */
public class SystemMessage {
    /**
     * id : 编号
     * title : 测试
     * content : 消息
     * created_at : 时间
     * is_read : 0
     */

    private String id;
    private String title;
    private String content;
    private String created_at;
    private String is_read;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getIs_read() {
        return is_read;
    }

}  
