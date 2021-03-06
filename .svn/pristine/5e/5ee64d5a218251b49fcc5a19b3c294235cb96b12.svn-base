package com.wanda3.service.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wanda3.service.pool.PoolManager;
import com.wanda3.socket.entity.PushBean;

public class DaoUtil {

	
	public static Map<String, List<PushBean>> findVid(String sql){
		List<PushBean>  ios= new ArrayList<PushBean>();
		List<PushBean> android = new ArrayList<PushBean>();
		Map<String, List<PushBean>> map = new HashMap<String, List<PushBean>>();
		Connection conn = PoolManager.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		try
		{
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next())
			{
				String userid = rs.getString("vid");
				String dt = rs.getString("dt");
				String token = rs.getString("token");
				PushBean bean = new PushBean();
				if(dt.equals("0")){
					bean.setVid(userid);
					bean.setToken(token);
					ios.add(bean);
				}else if(dt.equals("1")){
					bean.setVid(userid);
					bean.setToken(token);
					android.add(bean);
				}
				
			}
			map.put("ios", ios);
			map.put("android", android);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			PoolManager.freeConnection(conn);
		}
		return map;
	}
}
