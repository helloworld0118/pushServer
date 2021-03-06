package com.wanda3.socket.callback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wanda3.service.pool.PoolManager;
import com.wanda3.socket.entity.ExceptionCallBackBean;

/**
 * 消息发送异常回调再次发送
 * @author SongXiangYing
 *
 */
public class SendMessageExceptionCallBack {

	private static  List<ExceptionCallBackBean> android  = new ArrayList<ExceptionCallBackBean>();;
	private  List<ExceptionCallBackBean> ios =new ArrayList<ExceptionCallBackBean>();
	public void addAndroidExceptionMessage(ExceptionCallBackBean backBean){
		android.add(backBean);
	}
	public void addIOSExceptionMessage(ExceptionCallBackBean backBean){
		ios.add(backBean);
	}
	public static void removeAndroidExceptionMessage(ExceptionCallBackBean backBean){
		android.remove(backBean);
	}

	public void removeIOSExceptionMessage(ExceptionCallBackBean backBean){
		ios.remove(backBean);
	}
	public static void updateAndroidExceptionMessage(ExceptionCallBackBean backBean){
		android.remove(backBean);
			 String sql = "call add_push_message_state_by_user('"+backBean.getVid()+"',"+backBean.getMessageid()+",',',"+1+",'1')";
			 try {
				PoolManager.getCallableStatement(sql);
			} catch (SQLException e) {
				e.printStackTrace();
		}
	}

	public void updateIOSExceptionMessage(ExceptionCallBackBean backBean){
		ios.remove(backBean);
		int count = backBean.getCount()+1;
		if(count>=4){
			 String sql = "call add_push_message_state_by_user('"+backBean.getVid()+"',"+backBean.getMessageid()+",',',"+1+",'1')";
			 try {
				PoolManager.getCallableStatement(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			backBean.setCount(count);
			addIOSExceptionMessage(backBean);
		}
	}
	public static List<ExceptionCallBackBean> getAndroid() {
		return android;
	}
	public static void setAndroid(ExceptionCallBackBean backBean) {
		android.add(backBean);
	}
	public List<ExceptionCallBackBean> getIos() {
		return ios;
	}
	public  void setIos(ExceptionCallBackBean backBean) {
		ios.add(backBean);
	}
}
