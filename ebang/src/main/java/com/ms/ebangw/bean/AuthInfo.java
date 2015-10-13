package com.ms.ebangw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 用户认证工头、开发商 等类型 的认证信息
 * User: WangKai(123940232@qq.com)
 * 2015-09-21 15:13
 */
public class AuthInfo implements Parcelable, Serializable {
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

    /*开发商*/

    private String companyName;

    /**
     * 营业执照所在省
     */
    private String permitProvinceId;
    /**
     * 公司介绍
     */
    private String introduce;

    /**
     * 营业执照所在市
     */
    private String permitCityId;
    /**
     * 常住地址
     */
    private String oftenAddress;

    /**
     * 营业年限
     */
    private String businessAge;
    /**
     * 营业年限是否为长期  1：长期  2：非长期
     */
    private String timeState;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 组织机构代码证号
     */
    private String companyNumber;

    /**
     * 公司座机
     */
    private String companyPhone;

    /**
     * 营业执照注册号 business_license_number
     */
    private String businessLicenseNumber;

    /**
     * 组织机构代码证扫描件图片Id organization_certificate
     */
    private String organizationCertificate;
    /**
     * 企业联系人
     */
    private String linkman;

    /**
     * 企业联系人电话
     */
    private String linkmanPhone;

    /**
     * 对公帐户户名
     */
    private String publicAccountName;

    /**
     * 对公帐户
     */
    private String publicAccount;

    /**
     * 对公帐户银行Id
     */
    private String publicAccountBankId;
    /**
     * 对公帐户省Id
     */
    private String publicAccountProvinceId;
    /**
     * 对公帐户市Id
     */
    private String publicAccountCityId;


    /*=======工人  start*/

    /**
     * 工种格式[1,2,3]
     */
    private String crafts;


    /*=======工人  end*/




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

    public String getPermitProvinceId() {
        return permitProvinceId;
    }

    public void setPermitProvinceId(String permitProvinceId) {
        this.permitProvinceId = permitProvinceId;
    }

    public String getPermitCityId() {
        return permitCityId;
    }

    public void setPermitCityId(String permitCityId) {
        this.permitCityId = permitCityId;
    }

    public String getOftenAddress() {
        return oftenAddress;
    }

    public void setOftenAddress(String oftenAddress) {
        this.oftenAddress = oftenAddress;
    }

    public String getBusinessAge() {
        return businessAge;
    }

    public void setBusinessAge(String businessAge) {
        this.businessAge = businessAge;
    }

    public String getTimeState() {
        return timeState;
    }

    public void setTimeState(String timeState) {
        this.timeState = timeState;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getBusinessLicenseNumber() {
        return businessLicenseNumber;
    }

    public void setBusinessLicenseNumber(String businessLicenseNumber) {
        this.businessLicenseNumber = businessLicenseNumber;
    }

    public String getOrganizationCertificate() {
        return organizationCertificate;
    }

    public void setOrganizationCertificate(String organizationCertificate) {
        this.organizationCertificate = organizationCertificate;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkmanPhone() {
        return linkmanPhone;
    }

    public void setLinkmanPhone(String linkmanPhone) {
        this.linkmanPhone = linkmanPhone;
    }

    public String getPublicAccountName() {
        return publicAccountName;
    }

    public void setPublicAccountName(String publicAccountName) {
        this.publicAccountName = publicAccountName;
    }

    public String getPublicAccount() {
        return publicAccount;
    }

    public void setPublicAccount(String publicAccount) {
        this.publicAccount = publicAccount;
    }

    public String getPublicAccountBankId() {
        return publicAccountBankId;
    }

    public void setPublicAccountBankId(String publicAccountBankId) {
        this.publicAccountBankId = publicAccountBankId;
    }

    public String getPublicAccountProvinceId() {
        return publicAccountProvinceId;
    }

    public void setPublicAccountProvinceId(String publicAccountProvinceId) {
        this.publicAccountProvinceId = publicAccountProvinceId;
    }

    public String getPublicAccountCityId() {
        return publicAccountCityId;
    }

    public void setPublicAccountCityId(String publicAccountCityId) {
        this.publicAccountCityId = publicAccountCityId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCrafts() {
        return crafts;
    }

    public void setCrafts(String crafts) {
        this.crafts = crafts;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phone);
        dest.writeString(this.gender);
        dest.writeString(this.nativePlace);
        dest.writeString(this.realName);
        dest.writeString(this.identityCard);
        dest.writeString(this.bankCard);
        dest.writeString(this.bankId);
        dest.writeString(this.provinceId);
        dest.writeString(this.cityId);
        dest.writeString(this.frontImageId);
        dest.writeString(this.backImageId);
        dest.writeString(this.bankProvinceId);
        dest.writeString(this.bankCityId);
        dest.writeString(this.accountName);
        dest.writeString(this.companyName);
        dest.writeString(this.permitProvinceId);
        dest.writeString(this.introduce);
        dest.writeString(this.permitCityId);
        dest.writeString(this.oftenAddress);
        dest.writeString(this.businessAge);
        dest.writeString(this.timeState);
        dest.writeString(this.businessScope);
        dest.writeString(this.companyNumber);
        dest.writeString(this.companyPhone);
        dest.writeString(this.businessLicenseNumber);
        dest.writeString(this.organizationCertificate);
        dest.writeString(this.linkman);
        dest.writeString(this.linkmanPhone);
        dest.writeString(this.publicAccountName);
        dest.writeString(this.publicAccount);
        dest.writeString(this.publicAccountBankId);
        dest.writeString(this.publicAccountProvinceId);
        dest.writeString(this.publicAccountCityId);
        dest.writeString(this.crafts);
    }

    public AuthInfo() {
    }

    protected AuthInfo(Parcel in) {
        this.phone = in.readString();
        this.gender = in.readString();
        this.nativePlace = in.readString();
        this.realName = in.readString();
        this.identityCard = in.readString();
        this.bankCard = in.readString();
        this.bankId = in.readString();
        this.provinceId = in.readString();
        this.cityId = in.readString();
        this.frontImageId = in.readString();
        this.backImageId = in.readString();
        this.bankProvinceId = in.readString();
        this.bankCityId = in.readString();
        this.accountName = in.readString();
        this.companyName = in.readString();
        this.permitProvinceId = in.readString();
        this.introduce = in.readString();
        this.permitCityId = in.readString();
        this.oftenAddress = in.readString();
        this.businessAge = in.readString();
        this.timeState = in.readString();
        this.businessScope = in.readString();
        this.companyNumber = in.readString();
        this.companyPhone = in.readString();
        this.businessLicenseNumber = in.readString();
        this.organizationCertificate = in.readString();
        this.linkman = in.readString();
        this.linkmanPhone = in.readString();
        this.publicAccountName = in.readString();
        this.publicAccount = in.readString();
        this.publicAccountBankId = in.readString();
        this.publicAccountProvinceId = in.readString();
        this.publicAccountCityId = in.readString();
        this.crafts = in.readString();
    }

    public static final Parcelable.Creator<AuthInfo> CREATOR = new Parcelable.Creator<AuthInfo>() {
        public AuthInfo createFromParcel(Parcel source) {
            return new AuthInfo(source);
        }

        public AuthInfo[] newArray(int size) {
            return new AuthInfo[size];
        }
    };
}
