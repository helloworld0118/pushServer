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
import com.wanda3.socket.entity.Message;

public class OffLineDao {
	public  Map<String, List<Message>> findOffLineMessage(){
		String sql = "select pq.VID as vid, pm.message as message ,pm.id as messageid,pm.url_id as url from pushqueue as  pq left join pushmessage as pm on pq.message_id = pm.id where Date(pm.expiration_time) >now()";
		List<Message>  offlines= new ArrayList<Message>();
		Map<String, List<Message>> map = new HashMap<String, List<Message>>();
		Connection conn = PoolManager.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		try
		{
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			String vid = null;
			while (rs.next())
			{
				String userid = rs.getString("vid");
				String messageid = rs.getString("messageid");
				String message = rs.getString("message");
				String url = rs.getString("url");
				vid = userid;
				Message offmessage = new Message();
				offmessage.setMessageid(messageid);
				offmessage.setMessage(message);
				offmessage.setUrl(url);
				offlines.add(offmessage);
			}
			map.put(vid, offlines);
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
	
	public void delOffline(String vid){
		Connection conn = PoolManager.getConnection();
		String sql="delete form PushQueue where vid = "+vid;
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try
			{
				statement.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			PoolManager.freeConnection(conn);
		}
	}
	
}
