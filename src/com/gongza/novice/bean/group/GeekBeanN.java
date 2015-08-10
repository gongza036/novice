package com.gongza.novice.bean.group;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
  * @ClassName: GeekBeanN
  * @Description: DOC #664 荷花亲子 - 推荐列表    
  * 用于  GroupRecommIndexBeanN   内部数组中的GeekGroupBeanN  里  和GroupBeanN并列
  * @author gongza
  * @date 2015年6月11日 下午9:27:44
  *
 */
public class GeekBeanN implements Serializable{
	private String uid; // 团长uid
	private String geek_name; // 团长名称
	private String geek_logo; // 团长图像
	private String address; // 详细地址
	private String region_name; // 省市区
	private String description; // 团长描述 description
	private int is_follow; // 是否关注 （1-已关注，0-未关注） is_follow
	private ArrayList<GeekGinfoBeanN> geekInfoList;

	public ArrayList<GeekGinfoBeanN> getGeekInfoList() {
		return geekInfoList;
	}

	public void setGeekInfoList(ArrayList<GeekGinfoBeanN> geekInfoList) {
		this.geekInfoList = geekInfoList;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGeek_name() {
		return geek_name;
	}

	public void setGeek_name(String geek_name) {
		this.geek_name = geek_name;
	}

	public String getGeek_logo() {
		return geek_logo;
	}

	public void setGeek_logo(String geek_logo) {
		this.geek_logo = geek_logo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIs_follow() {
		return is_follow;
	}

	public void setIs_follow(int is_follow) {
		this.is_follow = is_follow;
	}

}
