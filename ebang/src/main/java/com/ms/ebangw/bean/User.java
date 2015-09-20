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

	@Override
	public String toString() {
		return "User{" +
			"id='" + id + '\'' +
			", app_token='" + app_token + '\'' +
			", name='" + name + '\'' +
			", email='" + email + '\'' +
			", gender='" + gender + '\'' +
			", password='" + password + '\'' +
			", phone='" + phone + '\'' +
			'}';
	}
}
