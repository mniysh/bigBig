package com.ms.ebangw.event;

/**
 * 重新获取User信息
 * User: WangKai(123940232@qq.com)
 * 2015-10-07 17:10
 */
public class RefreshUserEvent {

    private String category;

    public RefreshUserEvent(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
