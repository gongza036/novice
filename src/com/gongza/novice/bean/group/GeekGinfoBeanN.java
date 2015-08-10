package com.gongza.novice.bean.group;

import java.io.Serializable;

/**
 * 
  * @ClassName: GeekGinfoBeanN
  * @Description: DOC #664 荷花亲子 - 推荐列表
  * 用于GroupRecommIndexBeanN   内部数组中的GeekGroupBeanN中的 GeekBeanN   里面
  * @author gongza
  * @date 2015年6月11日 下午9:31:22
  *
 */
public class GeekGinfoBeanN implements Serializable {
	private int g_member; // 群成员数
	private String gid; 
	private String uid;
	private int is_join;
	private String g_title;
	
	
	
	public String getG_title() {
		return g_title;
	}
	public void setG_title(String g_title) {
		this.g_title = g_title;
	}
	public int getIs_join() {
		return is_join;
	}
	public void setIs_join(int is_join) {
		this.is_join = is_join;
	}
	public int getG_member() {
		return g_member;
	}
	public void setG_member(int g_member) {
		this.g_member = g_member;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
