package com.ms.ebangw.bean;

import java.util.List;

/**
 * 账户
 * User: WangKai(123940232@qq.com)
 * 2015-11-13 14:20
 */
public class Account {


    /**
     * pay_money : 144.00
     * income_money : 145.00
     * page : 1
     * trade : [{"title":"发工资","money":"+200元","type":"income","created_at":"2015-11-11 10:26:22"},{"title":"找维修工","money":"-200元","type":"pay","created_at":"2015-11-11 10:26:22"}]
     */

    private String pay_money;
    private String income_money;
    private String  page;
    private List<Trade> trade;

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getIncome_money() {
        return income_money;
    }

    public void setIncome_money(String income_money) {
        this.income_money = income_money;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Trade> getTrade() {
        return trade;
    }

    public void setTrade(List<Trade> trade) {
        this.trade = trade;
    }
}
