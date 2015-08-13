package com.gongza.novice.bean.group;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * @Description 荷花亲子：基础网络数据结构类
 * @author chenxinghong
 * @date 2015-6-6
 */
public class BaseNetBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ret; // 状态码
	private String msg; // 提示
	private T data; // 泛型：数据部分

	public BaseNetBean(String ret, String msg, T data) {
		this.ret = ret;
		this.msg = msg;
		this.data = data;
	}

	public BaseNetBean(BaseNetBean<JSONObject> jsonObj, T data) {
		this.ret = jsonObj.getRet();
		this.msg = jsonObj.getMsg();
		this.data = data;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
