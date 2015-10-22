package com.ms.ebangw.bean;

import java.io.Serializable;

/*
 * 发现的bean
 */
public class FoundBean implements Serializable {
	private int id;
	private String url;
	private String title;
	private String area;
	private String content;
	private String money;
	private String qiangdan;
	@Override
	public String toString() {
		return "FoundBean [id=" + id + ", url=" + url + ", title=" + title
				+ ", area=" + area + ", content=" + content + ", money="
				+ money + ", qiangdan=" + qiangdan + "]";
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
