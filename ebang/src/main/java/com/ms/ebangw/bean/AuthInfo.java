package com.ms.ebangw.bean;

/**
 * 用户认证工头、开发商 等类型 的认证信息
 * User: WangKai(123940232@qq.com)
 * 2015-09-21 15:13
 */
public class AuthInfo {
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
     * 开户行名称
     */
    private String bankOfDeposit;
    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 省id
     */
    private String provinceId;
    /**
     * 市Id
     */
    private String cityId;



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

    public String getBankOfDeposit() {
        return bankOfDeposit;
    }

    public void setBankOfDeposit(String bankOfDeposit) {
        this.bankOfDeposit = bankOfDeposit;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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
}
