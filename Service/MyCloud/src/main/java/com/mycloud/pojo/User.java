package com.mycloud.pojo;

import java.util.Date;

public class User {
	private Integer id;
	private String username;
	private String password;
	private String nickname;
	private String area;
	private String phone;
	private String oauthkey;
	private String oauthtype;
	private String score;
	private String permission;
	private Integer age;
	private Integer sex;
	private Date createtime;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOauthkey() {
		return oauthkey;
	}

	public void setOauthkey(String oauthkey) {
		this.oauthkey = oauthkey;
	}

	public String getOauthtype() {
		return oauthtype;
	}

	public void setOauthtype(String oauthtype) {
		this.oauthtype = oauthtype;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}