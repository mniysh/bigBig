package com.ms.ebangw.bean;

/**
 * 积分
 * User: WangKai(123940232@qq.com)
 * 2015-11-13 15:40
 */
public class JiFen {

    /**
     * action_title : 注册
     * action_describe : null
     * score_value : 1
     * created_at : 2015.11.10
     */

    private String action_title;
    private String action_describe;
    private String score_value;
    private String created_at;

    public void setAction_title(String action_title) {
        this.action_title = action_title;
    }



    public void setScore_value(String score_value) {
        this.score_value = score_value;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAction_title() {
        return action_title;
    }

    public String getAction_describe() {
        return action_describe;
    }

    public void setAction_describe(String action_describe) {
        this.action_describe = action_describe;
    }

    public String getScore_value() {
        return score_value;
    }

    public String getCreated_at() {
        return created_at;
    }
}
