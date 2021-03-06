package com.wanda3.service.dao;

import java.util.List;
import java.util.Map;

import com.wanda3.socket.entity.PushBean;

public class UserInfoDao {

	
	public Map<String, List<PushBean>> findVidByUserid(String userids){
			String sql = "select a.vid as vid,a.device_type as dt,a.device_id as token  from  alluser as a where a.uid in ('"+userids+"')";
			return DaoUtil.findVid(sql);
	}
	public Map<String, List<PushBean>> findVidByOnJob(){
		String sql = "select a.vid as vid,a.device_type as dt,a.device_id as token  from  alluser as a left join  employee as e on e.username = a.uid where  e.employeeStatus NOT IN ('0','3','4','5','6','10') and  e.MDP_OperationType<>'DELETE' and e.status = 1";
		return DaoUtil.findVid(sql);
	}
	public Map<String, List<PushBean>> findVidAll(){
		String sql = "select a.vid as vid,a.device_type as dt,a.device_id as token  from  alluser as a";
		return DaoUtil.findVid(sql);
	}
}
