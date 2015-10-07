package com.ms.ebangw.event;

/**
 * 首页底部栏的点击
 * User: WangKai(123940232@qq.com)
 * 2015-10-07 09:43
 */
public class BottomTitleClickEvent {
    /**
     * 所在RadioGroup中的index
     */
    private int index;

    public BottomTitleClickEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
