package com.mycloud.pojo;

import java.util.Date;

public class Note {
	private Integer id;
	private Integer folderid;
	private Integer userid;
	private String note;
	private String content;
	private Date createtime;
	private Date latestupdatetime;
	private Integer share;
	private Integer zan;
	private Integer status;
    private String shareby;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFolderid() {
		return folderid;
	}

	public void setFolderid(Integer folderid) {
		this.folderid = folderid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getLatestupdatetime() {
		return latestupdatetime;
	}

	public void setLatestupdatetime(Date latestupdatetime) {
		this.latestupdatetime = latestupdatetime;
	}

	public Integer getShare() {
		return share;
	}

	public void setShare(Integer share) {
		this.share = share;
	}

	public Integer getZan() {
		return zan;
	}

	public void setZan(Integer zan) {
		this.zan = zan;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getShareby() {
		return shareby;
	}

	public void setShareby(String shareby) {
		this.shareby = shareby;
	}
}
