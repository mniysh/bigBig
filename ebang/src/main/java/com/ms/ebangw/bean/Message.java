package com.ms.ebangw.bean;

/**
 * 消息
 * Created by admin on 2015/9/22.
 */
public class Message {
    private String id;
    private String head;
    private String title;
    private String time;
    private String state;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", head='" + head + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", state='" + state + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
