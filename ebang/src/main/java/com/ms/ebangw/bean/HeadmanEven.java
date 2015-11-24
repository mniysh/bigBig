package com.ms.ebangw.bean;

/**
 * Created by yangshaohua .
 * Created by on 2015/11/24
 */
public class HeadmanEven {
    private SelectHeadman headman;
    private boolean ischeck;

    public boolean ischeck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public HeadmanEven(SelectHeadman headman,boolean ischeck) {
        this.headman = headman;
        this.ischeck = ischeck;
    }

    public SelectHeadman getHeadman() {
        return headman;
    }

    public void setHeadman(SelectHeadman headman) {
        this.headman = headman;
    }
}
