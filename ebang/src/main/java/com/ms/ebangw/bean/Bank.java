package com.ms.ebangw.bean;

import java.io.Serializable;

/**
 * 银行
 * User: WangKai(123940232@qq.com)
 * 2015-09-24 16:34
 */
public class Bank implements Serializable{
    private String id;
    private String bank_name;
    private String bank_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_image() {
        return bank_image;
    }

    public void setBank_image(String bank_image) {
        this.bank_image = bank_image;
    }

    @Override
    public String toString() {
        return bank_name;
    }
}
