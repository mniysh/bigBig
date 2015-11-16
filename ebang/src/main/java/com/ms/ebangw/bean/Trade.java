package com.ms.ebangw.bean;

/**
 * 交易明细
 * User: WangKai(123940232@qq.com)
 * 2015-11-12 09:31
 */
public class Trade {


    /**
     * title : 找维修工
     * money : -200元
     * type : pay(支出)
     * created_at : 2015-11-11 10:26:22
     * describe : 去支付
     * project_info : {"id":1,"title":"测试工程","description":"工程描述","total_money":300,"project_money":800,"image":"http://labour.object.com/uploads/images/8c1ada42c4f3d534056860838db73fbc.jpg"}
     */

    private String title;
    private String money;
    private String type;
    private String created_at;
    private String describe;

    /**
     * id : 1
     * title : 测试工程
     * description : 工程描述
     * total_money : 300
     * project_money : 800
     * image : http://labour.object.com/uploads/images/8c1ada42c4f3d534056860838db73fbc.jpg
     */
    private ReleaseProject project_info;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public ReleaseProject getProject_info() {
        return project_info;
    }

    public void setProject_info(ReleaseProject project_info) {
        this.project_info = project_info;
    }
}
