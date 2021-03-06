
package com.wanda3.socket.utils;



import java.util.HashMap;
import java.util.Map;

//import org.apache.log4j.Logger;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.wanda3.socket.entity.UserInfo;

public  class SessionMap
{
	private final static Logger			logger		= Logger.getLogger(SessionMap.class);
	public volatile static Map<String, IoSession>	SESSION_MAP	= new HashMap<String, IoSession>();
	
	public static void registerSession(UserInfo userinfo ,IoSession IoSession)
	{
		synchronized (SESSION_MAP)
		{
			IoSession.setAttribute(userinfo.getVid(), userinfo);
			IoSession.setAttribute("vid",userinfo.getVid());
			SessionMap.SESSION_MAP.put(getSessionid(userinfo.getVid()), IoSession);
			logger.info("session 注册成功");
		}
	}
	
	public static IoSession getSession(String vid)
	{
		
		synchronized (SESSION_MAP)
		{
			return SessionMap.SESSION_MAP.get(getSessionid(vid));
		}
	}
	
	
	public static void unregisterSession(String vid)
	{
		synchronized (SESSION_MAP)
		{
			SessionMap.SESSION_MAP.remove(getSessionid(vid));
			logger.info("用户下线");
		}
	}
	public static String getSessionid(String vid)
	{
		return vid.toUpperCase();
	}
}
