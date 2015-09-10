package com.ms.ebangw.bean;

import java.io.Serializable;

/**
 * ������bean
 * @author admin
 *
 */
public class Comment implements Serializable {
	private int id;
	private String username;
	private String url;
	private String content;
	private String click_zan;
	private String number;
	public Comment(int id, String username, String url, String content,
			String click_zan, String number) {
		super();
		this.id = id;
		this.username = username;
		this.url = url;
		this.content = content;
		this.click_zan = click_zan;
		this.number = number;
	}
	public Comment(String username, String url, String content,
			String click_zan, String number) {
		super();
		this.username = username;
		this.url = url;
		this.content = content;
		this.click_zan = click_zan;
		this.number = number;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", username=" + username + ", url=" + url
				+ ", content=" + content + ", click_zan=" + click_zan
				+ ", number=" + number + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getClick_zan() {
		return click_zan;
	}
	public void setClick_zan(String click_zan) {
		this.click_zan = click_zan;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((click_zan == null) ? 0 : click_zan.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + id;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		Comment other = (Comment) obj;
		if (click_zan == null) {
			if (other.click_zan != null)
				return false;
		} else if (!click_zan.equals(other.click_zan))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id != other.id)
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}
