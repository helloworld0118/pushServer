package com.wanda3.socket.handler;

import org.apache.mina.core.session.IoSession;

import com.wanda3.socket.entity.Message;
import com.wanda3.socket.entity.UserInfo;
import com.wanda3.socket.utils.SessionMap;

/**
 * 
 * 确定用户身份 保证用户登陆的唯一
 * 
 * @author song
 *
 */
public class RegisterHandler {

	public static void register(Message header,IoSession session){
		UserInfo info = new UserInfo();
		String vid = header.getVid();
		info.setVid(vid);
		info.setDevice(header.getDevice());
		IoSession oldSession = SessionMap.getSession(vid);
		if(null!=oldSession){
		UserInfo userInfo = (UserInfo)oldSession.getAttribute(vid);
		if(userInfo.getDevice().equals(header.getDevice())){
			SessionMap.unregisterSession(vid);
			SessionMap.registerSession(info, session);
			session.write(header);
			}else{
				oldSession.close(true);
				SessionMap.unregisterSession(vid);
				SessionMap.registerSession(info, session);
				session.write(header);
			}
		}else{
			SessionMap.registerSession(info, session);
			session.write(header);
		}
	}
}
