package com.ms.ebangw.bean;

import java.io.Serializable;

public class User implements Serializable {
	private int id;
	private String name;
	private String password;
	private String sex;
	private String age;
	private String identity_card;//���֤
	private String native_place;//����
	private String iv_head;
	private String iv_identity_card_zhengmian;
	private String iv_identity_card_fanmian;
	private String phone;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int id, String name, String password, String sex,
			String age, String identity_card, String native_place,
			String iv_head, String iv_identity_card_zhengmian,
			String iv_identity_card_fanmian, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.identity_card = identity_card;
		this.native_place = native_place;
		this.iv_head = iv_head;
		this.iv_identity_card_zhengmian = iv_identity_card_zhengmian;
		this.iv_identity_card_fanmian = iv_identity_card_fanmian;
		this.phone = phone;
	}
	public User(String name, String password, String sex, String age,
			String identity_card, String native_place, String iv_head,
			String iv_identity_card_zhengmian, String iv_identity_card_fanmian,
			String phone) {
		super();
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.identity_card = identity_card;
		this.native_place = native_place;
		this.iv_head = iv_head;
		this.iv_identity_card_zhengmian = iv_identity_card_zhengmian;
		this.iv_identity_card_fanmian = iv_identity_card_fanmian;
		this.phone = phone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getIdentity_card() {
		return identity_card;
	}
	public void setIdentity_card(String identity_card) {
		this.identity_card = identity_card;
	}
	public String getNative_place() {
		return native_place;
	}
	public void setNative_place(String native_place) {
		this.native_place = native_place;
	}
	public String getIv_head() {
		return iv_head;
	}
	public void setIv_head(String iv_head) {
		this.iv_head = iv_head;
	}
	public String getIv_identity_card_zhengmian() {
		return iv_identity_card_zhengmian;
	}
	public void setIv_identity_card_zhengmian(String iv_identity_card_zhengmian) {
		this.iv_identity_card_zhengmian = iv_identity_card_zhengmian;
	}
	public String getIv_identity_card_fanmian() {
		return iv_identity_card_fanmian;
	}
	public void setIv_identity_card_fanmian(String iv_identity_card_fanmian) {
		this.iv_identity_card_fanmian = iv_identity_card_fanmian;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password="
				+ password + ", sex=" + sex + ", age=" + age
				+ ", identity_card=" + identity_card + ", native_place="
				+ native_place + ", iv_head=" + iv_head
				+ ", iv_identity_card_zhengmian=" + iv_identity_card_zhengmian
				+ ", iv_identity_card_fanmian=" + iv_identity_card_fanmian
				+ ", phone=" + phone + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((identity_card == null) ? 0 : identity_card.hashCode());
		result = prime * result + ((iv_head == null) ? 0 : iv_head.hashCode());
		result = prime
				* result
				+ ((iv_identity_card_fanmian == null) ? 0
						: iv_identity_card_fanmian.hashCode());
		result = prime
				* result
				+ ((iv_identity_card_zhengmian == null) ? 0
						: iv_identity_card_zhengmian.hashCode());
		result = prime * result
				+ ((native_place == null) ? 0 : native_place.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result
				+ ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (id != other.id)
			return false;
		if (identity_card == null) {
			if (other.identity_card != null)
				return false;
		} else if (!identity_card.equals(other.identity_card))
			return false;
		if (iv_head == null) {
			if (other.iv_head != null)
				return false;
		} else if (!iv_head.equals(other.iv_head))
			return false;
		if (iv_identity_card_fanmian == null) {
			if (other.iv_identity_card_fanmian != null)
				return false;
		} else if (!iv_identity_card_fanmian
				.equals(other.iv_identity_card_fanmian))
			return false;
		if (iv_identity_card_zhengmian == null) {
			if (other.iv_identity_card_zhengmian != null)
				return false;
		} else if (!iv_identity_card_zhengmian
				.equals(other.iv_identity_card_zhengmian))
			return false;
		if (native_place == null) {
			if (other.native_place != null)
				return false;
		} else if (!native_place.equals(other.native_place))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	

	
	
}
