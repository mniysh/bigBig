package com.ms.ebangw.bean;

import java.io.Serializable;

/**
 * 发布信息的bean文件
 * @author admin
 *
 */
public class PostMeassge implements Serializable {
	private int id;
	private String address;
	private String type;
	private String money;
	private String title;
	private String content;
	private String time;
	private String name;
	private String phone;

	// private int iv_01;
	// private int iv_02;
	// private int iv_03;
	public PostMeassge(int id, String address, String type, String money,
			String title, String content, String time, String name, String phone) {
		super();
		this.id = id;
		this.address = address;
		this.type = type;
		this.money = money;
		this.title = title;
		this.content = content;
		this.time = time;
		this.name = name;
		this.phone = phone;
	}

	public PostMeassge(String address, String type, String money, String title,
			String content, String time, String name, String phone) {
		super();
		this.address = address;
		this.type = type;
		this.money = money;
		this.title = title;
		this.content = content;
		this.time = time;
		this.name = name;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "PostMeassge [id=" + id + ", address=" + address + ", type="
				+ type + ", money=" + money + ", title=" + title + ", content="
				+ content + ", time=" + time + ", name=" + name + ", phone="
				+ phone + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		PostMeassge other = (PostMeassge) obj;
		if (id != other.id)
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public PostMeassge() {
		super();
		// TODO Auto-generated constructor stub
	}

}
