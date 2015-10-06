package com.ms.ebangw.event;

import com.ms.ebangw.bean.WorkType;

/**
 * 工种事件
 * User: WangKai(123940232@qq.com)
 * 2015-10-06 16:03
 */
public class WorkTypeEvent {
    private WorkType workType;
    private boolean isAdd;

    public WorkTypeEvent(WorkType workType, boolean isAdd) {
        this.workType = workType;
        this.isAdd = isAdd;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setIsAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }
}
