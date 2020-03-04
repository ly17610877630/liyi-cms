package com.liyi.cms.pojo;

public class Picture {
	private Integer id;
	private String picture;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	@Override
	public String toString() {
		return "Picture [id=" + id + ", picture=" + picture + "]";
	}
	
}
