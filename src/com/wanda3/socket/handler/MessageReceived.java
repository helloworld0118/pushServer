package com.wanda3.socket.handler;


import org.apache.mina.core.session.IoSession;

import com.wanda3.socket.entity.Message;

/**
 * 
 * 根据不同的请求进行分支处理
 * 
 * @author song
 *
 */
public class MessageReceived {

	public static void execute(Message header,IoSession session){
		String type = header.getType();
		//心跳
		if(type.equals("heart")){
		   session.write(header);
		//用户验证
		}else if(type.equals("register")){
			RegisterHandler.register(header, session);
		}else if(type.equals("group")){
			new MessageSend().groupMessage(header);
		}else if(type.equals("department")){
			new MessageSend().departmentMessage(header);
		}else if(type.equals("%")){
			new MessageSend().onJobAll(header);
		}else if(type.equals("@")){
			new MessageSend().allUser(header);
		}else if(type.equals("app")){
			new MessageSend().APPMessage(header);
		}else if(type.equals("offline")){
			new MessageSend().offlineMessage(header);
		}else{}
	}
	
	//一组人 type = 'department' to = 用户数组  数组
	// type ='group'     to =  部门id 数组
	//所有人 type ='%'		  to ＝ null  
	//所有在职人员 type ='@'	  to ＝ null  
	//拥有某个APP权限的人  type= 'app'  from =  appid
}
