package com.ms.ebangw.bean;

/**
 * 首页， 推荐的开发商
 * User: WangKai(123940232@qq.com)
 * 2015-11-03 09:08
 */
public class RecommendedDeveoper {


    /**
     * logo :
     * developer_id : 513
     * company_name : 哦哦哦哦哦哦哦
     */

    private String logo;
    private String developer_id;
    private String company_name;

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setDeveloper_id(String developer_id) {
        this.developer_id = developer_id;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getLogo() {
        return logo;
    }

    public String getDeveloper_id() {
        return developer_id;
    }

    public String getCompany_name() {
        return company_name;
    }

}
