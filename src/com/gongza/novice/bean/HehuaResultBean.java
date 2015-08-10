package com.gongza.novice.bean;

/**
 * 
 * @ClassName: HttpResultBean
 * @Description: http请求返回的结果
 * @author gongza
 * @date 2015年5月29日 下午2:33:37
 *
 */
public class HehuaResultBean<D> {
	private int ret = -1; // 状态码
	private String msg = ""; // 状态信息
	private long timestamp; // 时间戳
	private String data; // 返回内容
	private D dataBean;

	public D getDataBean() {
		return dataBean;
	}

	public void setDataBean(D dataBean) {
		this.dataBean = dataBean;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "HttpResultBean [ret=" + ret + ", msg=" + msg + ", data=" + data
				+ ", timestamp=" + timestamp + "]";
	}

}
