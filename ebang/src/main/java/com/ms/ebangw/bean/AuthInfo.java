package com.ms.ebangw.bean;

import java.io.Serializable;

/**
 * 用户认证工头、开发商 等类型 的认证信息
 * User: WangKai(123940232@qq.com)
 * 2015-09-21 15:13
 */
public class AuthInfo implements Serializable{
    private String phone;
    private String gender;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证
     */
    private String identityCard;
    /**
     *银行卡号
     */
    private String bankCard;

    /**
     * 银行名称对应的Id
     */
    private String bankId;

    /**
     * 省id
     */
    private String provinceId;
    /**
     * 市Id
     */
    private String cityId;
    /**
     * 身份证正面上传后返回的id
     */
    private String frontImageId;
    /**
     * 身份证反面上传后返回的id
     */
    private String backImageId;

    /**
     *  开户行省id
     */
    private String bankProvinceId;
    /**
     * 开户行所在市的id
     */
    private String bankCityId;
    /**
     *  银行卡开户人姓名
     */
    private String accountName;



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankProvinceId() {
        return bankProvinceId;
    }

    public void setBankProvinceId(String bankProvinceId) {
        this.bankProvinceId = bankProvinceId;
    }

    public String getBankCityId() {
        return bankCityId;
    }

    public void setBankCityId(String bankCityId) {
        this.bankCityId = bankCityId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getFrontImageId() {
        return frontImageId;
    }

    public void setFrontImageId(String frontImageId) {
        this.frontImageId = frontImageId;
    }

    public String getBackImageId() {
        return backImageId;
    }

    public void setBackImageId(String backImageId) {
        this.backImageId = backImageId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
