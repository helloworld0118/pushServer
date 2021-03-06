package com.wanda3.socket.handler;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.wanda3.ThreadPool.AlarmMessageQueue;
import com.wanda3.ThreadPool.ThreadPool;
import com.wanda3.service.dao.APPDao;
import com.wanda3.service.dao.OffLineDao;
import com.wanda3.service.dao.OrgDao;
import com.wanda3.service.dao.UserInfoDao;
import com.wanda3.service.pool.PoolManager;
import com.wanda3.socket.entity.Message;
import com.wanda3.socket.entity.PushBean;
import com.wanda3.socket.utils.IOSPushUtil;
import com.wanda3.socket.utils.SessionMap;

/**
 * 
 * 发送消息
 * 
 * @author song
 *
 */
public class MessageSend {
	private static Logger logger = Logger.getLogger(MessageSend.class);
	private OrgDao orgDao = new OrgDao();
	private APPDao appDao = new APPDao();
	private OffLineDao offLineDao = new OffLineDao();
	private UserInfoDao userInfoDao = new UserInfoDao();
	
	/**
	 * 一组人推送
	 * @param message
	 */
	public void departmentMessage(Message message) {
		 List<PushBean> ios = new ArrayList<PushBean>();
		 List<PushBean> android = new ArrayList<PushBean>();
		String[] orgs = message.getTo().split(",");
		for (String org : orgs) {
			Map<String, List<PushBean>> map =	orgDao.findVid(org);
			ios.addAll(map.get("ios"));
			android.addAll(map.get("android"));
		}
		forSendByAndriod(message, android);
		forSendByIOS(message,ios);
	}

	

	/**
	 * 单个人推送
	 * @param message
	 */
	public void groupMessage(Message message){
		 List<PushBean> ios = new ArrayList<PushBean>();
		 List<PushBean> android = new ArrayList<PushBean>();
		 if(null!=message.getFromsys()&&message.getFromsys().equalsIgnoreCase("mkh0013")){
			 message.setUrl("wdappoa://officehome?url="+message.getUrl());
		 }else if(null!=message.getFromsys()&&message.getFromsys().equalsIgnoreCase("oa"))
		 {
			  message.setUrl("wdappoa://oaaprove?url="+message.getUrl());
		 }else{
			 message.setUrl(message.getUrl());
		 }
		 message.setType("msg");
		 Map<String, List<PushBean>> map = userInfoDao.findVidByUserid(message.getTo());
		 ios.addAll(map.get("ios"));
		 android.addAll(map.get("android"));
		 forSendByAndriod(message, android);
		 forSendByIOS(message,ios);
	}
	
	/**
	 * app权限推送
	 * @param message
	 */
	public void APPMessage(Message message){
		 List<PushBean> ios = new ArrayList<PushBean>();
		 List<PushBean> android = new ArrayList<PushBean>();
		 message.setType("msg");
		String[] appids = message.getTo().split(",");
		for (String appid : appids) {
			Map<String, List<PushBean>> map =	appDao.findVid(appid);
			ios.addAll(map.get("ios"));
			android.addAll(map.get("android"));
		}
		 if(null!=message.getFromsys()&&message.getFromsys().equalsIgnoreCase("mkh0013")){
			 message.setUrl("wdappoa://officehome?url="+message.getUrl());
		 }else if(null!=message.getFromsys()&&message.getFromsys().equalsIgnoreCase("oa"))
		 {
			  message.setUrl("wdappoa://oaaprove?url="+message.getUrl());
		 }else{
			 message.setUrl(message.getUrl());
		 }
		forSendByAndriod(message, android);
		forSendByIOS(message,ios);
	}
	
	/**
	 * 在职员工推送
	 * @param message
	 */
	public void onJobAll(Message message){
		 List<PushBean> ios = new ArrayList<PushBean>();
		 List<PushBean> android = new ArrayList<PushBean>();
		 message.setType("msg");
		Map<String, List<PushBean>> map =	userInfoDao.findVidByOnJob();
		ios.addAll(map.get("ios"));
		android.addAll(map.get("android"));
		 if(null!=message.getFromsys()&&message.getFromsys().equalsIgnoreCase("mkh0013")){
			 message.setUrl("wdappoa://officehome?url="+message.getUrl());
		 }else if(null!=message.getFromsys()&&message.getFromsys().equalsIgnoreCase("oa"))
		 {
			  message.setUrl("wdappoa://oaaprove?url="+message.getUrl());
		 }else{
			 message.setUrl(message.getUrl());
		 }
		forSendByAndriod(message, android);
		forSendByIOS(message,ios);
	}

	/**
	 * 所有用户推送
	 * @param message
	 */
	public void allUser(Message message){
		 List<PushBean> ios = new ArrayList<PushBean>();
		 List<PushBean> android = new ArrayList<PushBean>();
		 message.setType("msg");
		 Map<String, List<PushBean>> map =	userInfoDao.findVidAll();
		 ios.addAll(map.get("ios"));
		 android.addAll(map.get("android"));
		 if(null!=message.getFromsys()&&message.getFromsys().equalsIgnoreCase("mkh0013")){
			 message.setUrl("wdappoa://officehome?url="+message.getUrl());
		 }else if(null!=message.getFromsys()&&message.getFromsys().equalsIgnoreCase("oa"))
		 {
			  message.setUrl("wdappoa://oaaprove?url="+message.getUrl());
		 }else{
			 message.setUrl(message.getUrl());
		 }
		 forSendByAndriod(message, android);
		 forSendByIOS(message,ios);
	}
	
	/**
	 * 获取离线消息并且发送
	 * @param header
	 */
	public void offlineMessage(Message header){
		Map<String, List<Message>> map =offLineDao.findOffLineMessage();
		 List<Message> listheafder = map.get(header.getVid());
		 Boolean del = true;
		if(header.getDevicetype().equals("0")){
			 for (Message h : listheafder) {
				 try {
					 IOSPushUtil.push(header.getDevice(), h.getMessage(),h.getUrl(), 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else if(header.getDevicetype().equals("1")){
			 for (Message h : listheafder) {
				 IoSession session = SessionMap.getSession(header.getVid());
				 if(null!=session){
					 session.write(h);
				 }else{
					 del = false;
				 }
			}
		}else{
			logger.info("没有次设备类型---"+header.getDevicetype());
		}
		if(del){
			offLineDao.delOffline(header.getVid());
		}
	}
	/**
	 * 安卓设备的推送
	 * @param message
	 * @param android
	 */
	private void forSendByAndriod(Message message, List<PushBean> android) {
		//记录以发用户
		List<String> alreadyList = new ArrayList<String>();
		//记录未发用户
		List<String> notList = new ArrayList<String>();
		for (PushBean pushBean : android) {
			IoSession session = SessionMap.getSession(pushBean.getVid());
			if(null!=session){
				message.setVid(pushBean.getVid());
				session.write(message);
				alreadyList.add(pushBean.getVid());
			}else{
				message.setVid(pushBean.getVid());
				notList.add(pushBean.getVid());
			}
		}
		  try {
			 //持久化发送状态
			  if(notList.size()>0){
				  String users = StringUtils.join(notList, ",");
				  String sql = "call add_push_message_state_by_user('"+users+"',"+message.getMessageid()+",',',"+notList.size()+",'1')";
				  PoolManager.getCallableStatement(sql);
			  }else if(alreadyList.size()>0){
//				  String users = StringUtils.join(alreadyList, ",");
//				  String sql = "call add_push_message_state_by_user('"+users+"',"+message.getMessageid()+",',',"+alreadyList.size()+",'0')";
//				  PoolManager.getCallableStatement(sql);
			  }else{
				  logger.info("请求用户不存在");
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		if (SendMessageExceptionCallBack.getAndroid().size() > 0) {
//			ExceptionCallBackBean backBean = null;
//			for (int i = 0; i < 3; i++) {
//				for (ExceptionCallBackBean ba : SendMessageExceptionCallBack.getAndroid()) {
//					backBean = ba;
//					IoSession session = SessionMap.getSession(ba.getVid());
//					if(null!=session){
//						session.write(message);
//						SendMessageExceptionCallBack.removeAndroidExceptionMessage(ba);
//					}
//				}
//			}
//			if(SendMessageExceptionCallBack.getAndroid().size() > 0){
//				SendMessageExceptionCallBack.updateAndroidExceptionMessage(backBean);
//			}
//			
//		}
	}
	
	/**
	 * iOS 设备的推送
	 * @param message
	 * @param ios
	 */
	public void forSendByIOS(Message message, List<PushBean> ios){
		int size = ios.size();
		int lengle = 100;
		int i=0;
		if(size%lengle>0){
			i = size/lengle;
			i +=1;
		}else{
			i = size/lengle;
		}
		List<PushBean> addUser = new ArrayList<PushBean>();
		List<PushBean> all = new ArrayList<PushBean>();
		all.addAll(ios);
		boolean first = false;
		List<AlarmMessageQueue> queuelist = new ArrayList<AlarmMessageQueue>();
		for (int j = 0; j < i; j++) {
			AlarmMessageQueue queue = new AlarmMessageQueue();
			if(first){
				all.removeAll(addUser);
			}
			first = true;
			for (PushBean pushBean : all) {
				message.setVid(pushBean.getVid());
				message.setDevice(pushBean.getToken());
				addUser.add(pushBean);
				queue.push(message);
			}
			queuelist.add(queue);
		}
		ThreadPool threadPool = new ThreadPool(20);
		for (AlarmMessageQueue alarmMessageQueue : queuelist) {
			threadPool.poll(alarmMessageQueue);
		}
	}
	
	
}
