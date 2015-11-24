package com.ms.ebangw.bean;

import com.ms.ebangw.utils.PinYinUtils;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-11-24 16:36
 */
public class People implements Comparable<People>{

    /**
     * login_id : 100068
     * phone : 18031192001
     * head_image : 2a6ad06ef6c4787908f60e29774ddf33.jpg
     * real_name : 时则会
     */

    private String login_id;
    private String phone;
    private String head_image;
    private String real_name;

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getLogin_id() {
        return login_id;
    }

    public String getPhone() {
        return phone;
    }

    public String getHead_image() {
        return head_image;
    }

    public String getReal_name() {
        return real_name;
    }

    public String getPinyin() {
        return PinYinUtils.getPinyin(real_name);
    }

    @Override
    public int compareTo(People another) {
        return PinYinUtils.getPinyin(real_name).compareTo(PinYinUtils.getPinyin(another
            .getReal_name()));
    }
}
