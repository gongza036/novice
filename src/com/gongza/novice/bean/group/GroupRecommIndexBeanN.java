package com.gongza.novice.bean.group;

import java.util.ArrayList;

/**
 * 
  * @ClassName: GroupRecommIndexBeanN
  * @Description: DOC #664 荷花亲子 - 推荐列表     实体类内部包含一个GeekGroupBeanN 数组
  * @author gongza
  * @date 2015年6月11日 下午9:25:16
  *
 */
public class GroupRecommIndexBeanN {
	private int count; // 总条数
	private int p_total; // 总页数
	private int rand_id; // 随机ID
	private int uid;
	private ArrayList<GroupOperations> operations;
	private ArrayList<GeekGroupBeanN> list;
	

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public ArrayList<GroupOperations> getOperations() {
		return operations;
	}

	public void setOperations(ArrayList<GroupOperations> operations) {
		this.operations = operations;
	}


	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getP_total() {
		return p_total;
	}

	public void setP_total(int p_total) {
		this.p_total = p_total;
	}

	public int getRand_id() {
		return rand_id;
	}

	public void setRand_id(int rand_id) {
		this.rand_id = rand_id;
	}

	public ArrayList<GeekGroupBeanN> getList() {
		return list;
	}

	public void setList(ArrayList<GeekGroupBeanN> list) {
		this.list = list;
	}

}
