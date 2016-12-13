package com.onlytigi.springStudy.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("user")
public class User {
	private String idNo;
	private String id;
	private String email;
	private String password;
	private String name;
	private String authority;
	private String stat;
	private Date modYmdt;
	private String modr;
	private Date regYmdt;
	private String regr;
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public Date getModYmdt() {
		return modYmdt;
	}
	public void setModYmdt(Date modYmdt) {
		this.modYmdt = modYmdt;
	}
	public String getModr() {
		return modr;
	}
	public void setModr(String modr) {
		this.modr = modr;
	}
	public Date getRegYmdt() {
		return regYmdt;
	}
	public void setRegYmdt(Date regYmdt) {
		this.regYmdt = regYmdt;
	}
	public String getRegr() {
		return regr;
	}
	public void setRegr(String regr) {
		this.regr = regr;
	}
}
