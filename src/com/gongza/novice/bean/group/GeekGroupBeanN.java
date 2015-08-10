package com.gongza.novice.bean.group;

/**
 * 
  * @ClassName: GeekGroupBeanN
  * @Description: DOC #664 荷花亲子 - 推荐列表     
  * 用于GroupRecommIndexBeanN   内部数组
  * @author gongza
  * @date 2015年6月11日 下午9:26:35
  *
 */
public class GeekGroupBeanN {
	private int type;
	private GeekBeanN geek;
	private GroupBeanN group;
	
	//gz
	private GroupOperations operation;
	
	
	public GroupOperations getOperation() {
		return operation;
	}

	public void setOperation(GroupOperations operation) {
		this.operation = operation;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public GeekBeanN getGeek() {
		return geek;
	}

	public void setGeek(GeekBeanN geek) {
		this.geek = geek;
	}

	public GroupBeanN getGroup() {
		return group;
	}

	public void setGroup(GroupBeanN group) {
		this.group = group;
	}
}
