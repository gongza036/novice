package com.gongza.novice.bean.group;

public class BannerAdBean {
	private String advert_id;
	private int position_id;
	private String advert_name;
	private int typeid;
	private String advert_pic;
	private String advert_link;

	public int getPosition_id() {
		return position_id;
	}

	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public String getAdvert_id() {
		return advert_id;
	}

	public void setAdvert_id(String advert_id) {
		this.advert_id = advert_id;
	}

	public String getAdvert_name() {
		return advert_name;
	}

	public void setAdvert_name(String advert_name) {
		this.advert_name = advert_name;
	}

	public String getAdvert_pic() {
		return advert_pic;
	}

	public void setAdvert_pic(String advert_pic) {
		this.advert_pic = advert_pic;
	}

	public String getAdvert_link() {
		return advert_link;
	}

	public void setAdvert_link(String advert_link) {
		this.advert_link = advert_link;
	}

}
