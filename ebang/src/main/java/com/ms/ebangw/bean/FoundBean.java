package com.ms.ebangw.bean;

import java.io.Serializable;

/*
 * ���ֵ�
 */
public class FoundBean implements Serializable {
	private int id;
	private String url;
	private String title;
	private String area;
	private String content;
	private String money;
	private String qiangdan;
	public FoundBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FoundBean(int id, String url, String title, String area,
			String content, String money, String qiangdan) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.area = area;
		this.content = content;
		this.money = money;
		this.qiangdan = qiangdan;
	}
	public FoundBean(String url, String title, String area, String content,
			String money, String qiangdan) {
		super();
		this.url = url;
		this.title = title;
		this.area = area;
		this.content = content;
		this.money = money;
		this.qiangdan = qiangdan;
	}
	@Override
	public String toString() {
		return "FoundBean [id=" + id + ", url=" + url + ", title=" + title
				+ ", area=" + area + ", content=" + content + ", money="
				+ money + ", qiangdan=" + qiangdan + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + id;
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result
				+ ((qiangdan == null) ? 0 : qiangdan.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		FoundBean other = (FoundBean) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id != other.id)
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (qiangdan == null) {
			if (other.qiangdan != null)
				return false;
		} else if (!qiangdan.equals(other.qiangdan))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getQiangdan() {
		return qiangdan;
	}
	public void setQiangdan(String qiangdan) {
		this.qiangdan = qiangdan;
	}
	
}
