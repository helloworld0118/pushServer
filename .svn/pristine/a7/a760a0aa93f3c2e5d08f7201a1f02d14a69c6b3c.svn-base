package com.wanda3.service.dao;

import java.util.List;
import java.util.Map;

import com.wanda3.socket.entity.PushBean;

public class OrgDao{

	
	
	/**
	 * 获取用户的vid 并且分类
	 * @param orgid
	 * @return
	 */
	public Map<String, List<PushBean>> findVid(String orgid){
		String sql = "select a.vid as vid,a.device_type as dt,a.device_id as token  from  alluser as a left join  employee as e on e.employeeID = a.uid where e.orgcode = "+orgid+" and e.employeeStatus NOT IN ('0','3','4','5','6','10') and  e.MDP_OperationType<>'DELETE' and e.status = 1";
		return DaoUtil.findVid(sql);
}
}
