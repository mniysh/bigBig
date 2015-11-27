package com.ms.ebangw.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "user")
public class User implements Serializable {
	/**
	 * 用户Id
	 */
	@DatabaseField(id = true, unique = true, canBeNull = false)
	private String  id;
	/**
	 * token
	 */
	@DatabaseField(columnName = "app_token", canBeNull = false)
	private String app_token;
	@DatabaseField(columnName = "name")
	private String name;
	@DatabaseField(columnName = "email")
	private String email;

	/**
	 * 头像Url
	 */
	@DatabaseField(columnName = "head_img")
	private String head_img;
	/**
	 * 性别
	 */
	@DatabaseField(columnName = "gender")
	private String gender;
	@DatabaseField(columnName = "password")
	private String password;
	@DatabaseField(columnName = "phone")
	private String phone;
	@DatabaseField(columnName = "message")
	private String message;
	/**个人 investor，农民工 worker，工长 headman，开发商 developers*/
	@DatabaseField(columnName = "category")
	private String category;

	@DatabaseField(columnName = "nick_name")
	private String nick_name;

	@DatabaseField(columnName = "rank")
	private String rank;
	/**
	 * 真实姓名
	 */
	@DatabaseField(columnName = "real_name")
	private String real_name;

	/**
	 * 籍贯
	 */
	@DatabaseField(columnName = "area")
	private String area;

	/**
	 * 工种描述(只工人有)
	 */
	@DatabaseField(columnName = "craft")
	private String craft;

	/**
	 * 0或1 工头特有标示 1表示工头第二步认证过了 0表示没有
	 */
	@DatabaseField(columnName = "recommend")
	private String recommend;



	/**
	 * 认证状态
	 * //状态游客guest/
	 * 认证中auth_developers(认证开发者中)/
	 * auth_worker(认证工人中)/
	 * auth_headman(认证工头中)/
	 * auth_investor(认证个人中)/
	 * complete（完成认证）
	 */
	@DatabaseField(columnName = "status")
	private String status;

	/**
	 * 身份证号
	 */
	@DatabaseField(columnName = "identity_card")
	private String identity_card;

	/**
	 * 身份证前面照片
	 */
	@DatabaseField(columnName = "card_image_front")
	private String card_image_front;


	/**
	 * 身份证后面照片
	 */
	@DatabaseField(columnName = "card_image_back")
	private String card_image_back;

	/**
	 * 总积分
	 */
	private String total_score;

	/**
	 * 邀请码
	 */
	private String invite_code;

	/**
	 *
	 */
	private String is_have_headman;



	public User() {
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApp_token() {
		return app_token;
	}

	public void setApp_token(String app_token) {
		this.app_token = app_token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCraft() {
		return craft;
	}

	public void setCraft(String craft) {
		this.craft = craft;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIdentity_card() {
		return identity_card;
	}

	public void setIdentity_card(String identity_card) {
		this.identity_card = identity_card;
	}

	public String getCard_image_front() {
		return card_image_front;
	}

	public void setCard_image_front(String card_image_front) {
		this.card_image_front = card_image_front;
	}

	public String getCard_image_back() {
		return card_image_back;
	}

	public void setCard_image_back(String card_image_back) {
		this.card_image_back = card_image_back;
	}

	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getTotal_score() {
		return total_score;
	}

	public void setTotal_score(String total_score) {
		this.total_score = total_score;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public String getIs_have_headman() {
		return is_have_headman;
	}

	public void setIs_have_headman(String is_have_headman) {
		this.is_have_headman = is_have_headman;
	}

	@Override
	public String toString() {
		return "User{" +
			"id='" + id + '\'' +
			", app_token='" + app_token + '\'' +
			", name='" + name + '\'' +
			", email='" + email + '\'' +
			", head_img='" + head_img + '\'' +
			", gender='" + gender + '\'' +
			", password='" + password + '\'' +
			", phone='" + phone + '\'' +
			", message='" + message + '\'' +
			", category='" + category + '\'' +
			", nick_name='" + nick_name + '\'' +
			", rank='" + rank + '\'' +
			", real_name='" + real_name + '\'' +
			", area='" + area + '\'' +
			", craft='" + craft + '\'' +
			", recommend='" + recommend + '\'' +
			", status='" + status + '\'' +
			", identity_card='" + identity_card + '\'' +
			", card_image_front='" + card_image_front + '\'' +
			", card_image_back='" + card_image_back + '\'' +
			", total_score='" + total_score + '\'' +
			", invite_code='" + invite_code + '\'' +
			", is_have_headman='" + is_have_headman + '\'' +
			'}';
	}
}
