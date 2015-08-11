package com.gongza.novice.bean.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gongza.novice.bean.HehuaResultBean;
import com.gongza.novice.bean.group.GeekBeanN;
import com.gongza.novice.bean.group.GeekGinfoBeanN;
import com.gongza.novice.bean.group.GeekGroupBeanN;
import com.gongza.novice.bean.group.GroupBeanN;
import com.gongza.novice.bean.group.GroupOperations;
import com.gongza.novice.bean.group.GroupRecommIndexBeanN;


/**
 * 
 * @ClassName: GroupRecommParser
 * @Description: DOC #664 荷花亲子 - 推荐列表 首页推荐主列表的解析类 采取手动解析的方式
 * @author gongza
 * @date 2015年6月11日 下午5:02:37
 *
 */
public class GroupRecommParser {
	private HehuaResultBean<GroupRecommIndexBeanN> mHttpResultBean;

	public HehuaResultBean<GroupRecommIndexBeanN> getGroupRecomm(String json) {
		mHttpResultBean = new HehuaResultBean<GroupRecommIndexBeanN>();
		try {
			JSONObject jsonOb = new JSONObject(json);
			mHttpResultBean.setRet(jsonOb.getInt("ret"));
			mHttpResultBean.setMsg(jsonOb.getString("msg"));
			mHttpResultBean.setData(jsonOb.getString("data"));
			mHttpResultBean.setTimestamp(jsonOb.getLong("timestamp"));
			mHttpResultBean.setDataBean(getRecommIndexBean(mHttpResultBean
					.getData()));

		} catch (JSONException e) {
			e.printStackTrace();
			mHttpResultBean.setRet(800);
			mHttpResultBean.setMsg("json解析失败");
		}
		return mHttpResultBean;
	}

	private GroupRecommIndexBeanN getRecommIndexBean(String data)
			throws JSONException {
		GroupRecommIndexBeanN ri = new GroupRecommIndexBeanN();
		JSONObject jsonOb = new JSONObject(data);
		try {ri.setCount(jsonOb.getInt("count"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--count出错");}
		try {ri.setP_total(jsonOb.getInt("p_total"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--p_total出错");}
		try {ri.setRand_id(jsonOb.getInt("rand_id"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--rand_id出错");}
		try {ri.setUid(jsonOb.getInt("uid"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--uid出错");}
		ArrayList<GroupOperations> listGroupOperations = null;
		if (jsonOb.has("operations")) {
			listGroupOperations=getOperations(jsonOb.getJSONArray("operations"));
			try {ri.setOperations(listGroupOperations);} catch (Exception e) {e.printStackTrace();System.out.println("json解析--Operations出错");}
		}
		ri.setList(getlist(jsonOb.getJSONArray("list"),listGroupOperations));
//		ri.setList(getlist(jsonOb.getJSONArray("list"),null));
		return ri;
	}

	private ArrayList<GeekGroupBeanN> getlist(JSONArray Jsa,ArrayList<GroupOperations> oList){
		ArrayList<GeekGroupBeanN> list = new ArrayList<GeekGroupBeanN>();
		
		if (oList!=null) {
			for (int i = 0; i < oList.size(); i++) {
				GeekGroupBeanN oGeekGroupBeanN=new GeekGroupBeanN();
				GroupOperations oGroupOperations=oList.get(i);
				oGeekGroupBeanN.setType(1);
				oGeekGroupBeanN.setOperation(oGroupOperations);
				list.add(oGeekGroupBeanN);
			}
		}
		
		// GeekGroupBean gg=new GeekGroupBean();
		// gg.setGeek(null);
		for (int i = 0; i < Jsa.length(); i++) {
			GeekGroupBeanN mGeekGroupBean = new GeekGroupBeanN();
			GeekBeanN mGeekBeanN=new GeekBeanN();
			GroupBeanN mGroupBeanN=new GroupBeanN();
			try {
				JSONObject tJsono = Jsa.getJSONObject(i);
				JSONObject geekJo=tJsono.getJSONObject("geek");
				try {mGeekBeanN.setUid(geekJo.getString("uid"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--uid出错");}
				try {mGeekBeanN.setGeek_name(geekJo.getString("geek_name"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--geek_name出错");}
				try {mGeekBeanN.setGeek_logo(geekJo.getString("geek_logo"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--geek_logo出错");}
				try {mGeekBeanN.setAddress(geekJo.getString("address"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--address出错");}
				try {mGeekBeanN.setRegion_name(geekJo.getString("region_name"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--region_name出错");}
				try {mGeekBeanN.setDescription(geekJo.getString("description"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--description出错");}
				try {mGeekBeanN.setIs_follow(geekJo.getInt("is_follow"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--is_follow出错");}
//				getG_info(geekJo.getString("g_info"));
				mGeekBeanN.setGeekInfoList(getG_info(geekJo.getJSONArray("g_info")));;
				mGeekGroupBean.setGeek(mGeekBeanN);
				
				JSONObject groupJo=tJsono.getJSONObject("group");
				try {mGroupBeanN.setGroup_geek_id(groupJo.getInt("group_geek_id"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--group_geek_id出错");}
				try {mGroupBeanN.setUid(groupJo.getInt("uid"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--uid出错");}
				try {mGroupBeanN.setGroup_id(groupJo.getInt("group_id"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--group_id出错");}
				try {mGroupBeanN.setShare_num(groupJo.getInt("share_num"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--share_num出错");}
				try {mGroupBeanN.setPraise_num(groupJo.getInt("praise_num"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--praise_num出错");}
				try {mGroupBeanN.setAdd_time(groupJo.getString("add_time"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--add_time出错");}
				try {mGroupBeanN.setUpdate_time(groupJo.getString("update_time"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--update_time出错");}
				try {mGroupBeanN.setSold_num(groupJo.getInt("sold_num"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--sold_num出错");}
				try {mGroupBeanN.setGroup_name(groupJo.getString("group_name"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--group_name出错");}
				try {mGroupBeanN.setGoods_id(groupJo.getInt("goods_id"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--goods_id出错");}
				try {mGroupBeanN.setGoods_name(groupJo.getString("goods_name"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--goods_name出错");}
				try {mGroupBeanN.setOrginal_price(groupJo.getDouble("orginal_price"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--orginal_price出错");}
				try {mGroupBeanN.setStart_time(groupJo.getString("start_time"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--start_time出错");}
				try {mGroupBeanN.setEnd_time(groupJo.getString("end_time"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--end_time出错");}
				try {mGroupBeanN.setGroup_status(groupJo.getString("group_status"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--group_status出错");}
				try {mGroupBeanN.setPicture(groupJo.getString("picture"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--picture出错");}
				try {mGroupBeanN.setContent(groupJo.getString("content"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--content出错");}
//				try {mGroupBeanN.setMax_per_user(groupJo.getInt("max_per_user"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--max_per_user出错");}
				try {mGroupBeanN.setIs_shipping(groupJo.getInt("is_shipping"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--is_shipping出错");}
				try {mGroupBeanN.setGroup_desc(groupJo.getString("group_desc"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--group_desc出错");}
				try {mGroupBeanN.setGroup_price(groupJo.getDouble("group_price"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--group_price出错");}
				try {mGroupBeanN.setIs_like(groupJo.getInt("is_like"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--is_like出错");}
				try {mGroupBeanN.setLave_time(groupJo.getLong("lave_time"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--lave_time出错");}
				try {mGroupBeanN.setBuy_stock(groupJo.getLong("buy_stock"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--buy_stock出错");}
				try {mGroupBeanN.setSold_out(groupJo.getInt("sold_out"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--sold_out出错");}
				mGeekGroupBean.setGroup(mGroupBeanN);
				list.add(mGeekGroupBean);
			
			} catch (Exception e) {
				
			}
			
			
		}
		return list;
	}
	
	private ArrayList<GeekGinfoBeanN> getG_info(JSONArray jaInfo) {
		ArrayList<GeekGinfoBeanN> geekInfoList =new ArrayList<GeekGinfoBeanN>();
		
		
		for (int i = 0; i < jaInfo.length(); i++) {
			GeekGinfoBeanN mGeekGinfoBeanN=new GeekGinfoBeanN();
			try {
				JSONObject infoJo=jaInfo.getJSONObject(i);
				try {mGeekGinfoBeanN.setGid(infoJo.getString("gid"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--gid出错");}
				try {mGeekGinfoBeanN.setUid(infoJo.getString("uid"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--uid出错");}
				try {mGeekGinfoBeanN.setG_member(infoJo.getInt("g_member"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--g_member出错");}
				try {mGeekGinfoBeanN.setIs_join(infoJo.getInt("is_join"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--is_join出错");}
				try {mGeekGinfoBeanN.setG_title(infoJo.getString("g_title"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--g_title出错");}
				geekInfoList.add(mGeekGinfoBeanN);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return geekInfoList;
	}
	
	private ArrayList<GroupOperations> getOperations(JSONArray operations) {
		ArrayList<GroupOperations> listGroupOperations =new ArrayList<GroupOperations>();
		
		
		for (int i = 0; i < operations.length(); i++) {
			GroupOperations mGroupOperations=new GroupOperations();
			try {
				JSONObject infoJo=operations.getJSONObject(i);
				try {mGroupOperations.setId(infoJo.getInt("id"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--id出错");}
				try {mGroupOperations.setTitle(infoJo.getString("title"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--title出错");}
				try {mGroupOperations.setThumb(infoJo.getString("thumb"));} catch (JSONException e) {e.printStackTrace();System.out.println("json解析--thumb出错");}
				listGroupOperations.add(mGroupOperations);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return listGroupOperations;
	}
	
	

}
