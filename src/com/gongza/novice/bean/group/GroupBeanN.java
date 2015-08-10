package com.gongza.novice.bean.group;

import java.io.Serializable;

/**
 * 
  * @ClassName: GroupBeanN
  * @Description: DOC #664 荷花亲子 - 推荐列表
  * 用于  GroupRecommIndexBeanN   内部数组中的GeekGroupBeanN  里 和GeekBeanN并列
  * @author gongza
  * @date 2015年6月11日 下午9:29:59
  *
 */
public class GroupBeanN implements Serializable{
	private int group_geek_id; //
	private int uid; //
	private int group_id; //

	private int share_num; //
	private int praise_num; //
	private String add_time; //
	private String update_time;
	private int sold_num;
	private String group_name; //
	private int goods_id; //
	private String goods_name; //
	private double orginal_price; //
	private String start_time; //
	private String end_time; //

	private String group_status;
	private String picture; //
//	private int max_per_user; //
	private int is_shipping; //
	private String group_desc; //
	private double group_price; //
	private String content; //
	
	private int is_like;//是否喜欢    6_15增加
	private long lave_time;//结束时间     6_20增加
	
	private int is_top;//add by zhanhongyi 07/02
	private int status;//add by zhanhongyi 07/02
	
	private long buy_stock;//库存数     8_6增加
	private int sold_out;//0未售空， 1已售空     8_6增加

	
	public long getBuy_stock() {
		return buy_stock;
	}

	public void setBuy_stock(long buy_stock) {
		this.buy_stock = buy_stock;
	}

	public int getSold_out() {
		return sold_out;
	}

	public void setSold_out(int sold_out) {
		this.sold_out = sold_out;
	}

	public int getSold_num() {
		return sold_num;
	}

	public void setSold_num(int sold_num) {
		this.sold_num = sold_num;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus()
	{
		return status;
	}
	
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	
	public void setIs_top(int is_top)
	{
		this.is_top = is_top;
	}
	
	public int getIs_top()
	{
		return is_top;
	}
	
	public long getLave_time() {
		return lave_time;
	}

	public void setLave_time(long lave_time) {
		this.lave_time = lave_time;
	}

	public int getIs_like() {
		return is_like;
	}

	public void setIs_like(int is_like) {
		this.is_like = is_like;
	}

	public int getGroup_geek_id() {
		return group_geek_id;
	}

	public void setGroup_geek_id(int group_geek_id) {
		this.group_geek_id = group_geek_id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public int getShare_num() {
		return share_num;
	}

	public void setShare_num(int share_num) {
		this.share_num = share_num;
	}

	public int getPraise_num() {
		return praise_num;
	}

	public void setPraise_num(int praise_num) {
		this.praise_num = praise_num;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public double getOrginal_price() {
		return orginal_price;
	}

	public void setOrginal_price(double orginal_price) {
		this.orginal_price = orginal_price;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getGroup_status() {
		return group_status;
	}

	public void setGroup_status(String group_status) {
		this.group_status = group_status;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

//	public int getMax_per_user() {
//		return max_per_user;
//	}
//
//	public void setMax_per_user(int max_per_user) {
//		this.max_per_user = max_per_user;
//	}

	public int getIs_shipping() {
		return is_shipping;
	}

	public void setIs_shipping(int is_shipping) {
		this.is_shipping = is_shipping;
	}

	public String getGroup_desc() {
		return group_desc;
	}

	public void setGroup_desc(String group_desc) {
		this.group_desc = group_desc;
	}

	public double getGroup_price() {
		return group_price;
	}

	public void setGroup_price(double group_price) {
		this.group_price = group_price;
	}

}
