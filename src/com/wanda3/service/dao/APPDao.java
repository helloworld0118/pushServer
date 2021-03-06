package com.wanda3.service.dao;

import java.util.List;
import java.util.Map;

import com.wanda3.socket.entity.PushBean;

public class APPDao {

	
	public  Map<String, List<PushBean>> findVid(String appid){
		String sql = "select a.vid as vid,a.device_type as dt,a.device_id as token from alluser as a   left join  authuser as au where au.rtxId = a.uid and au.module_code =" + appid;
		return DaoUtil.findVid(sql);
	}
}
