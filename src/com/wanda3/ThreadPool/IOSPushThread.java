package com.wanda3.ThreadPool;

import com.wanda3.socket.callback.SendMessageExceptionCallBack;
import com.wanda3.socket.entity.ExceptionCallBackBean;
import com.wanda3.socket.entity.Message;
import com.wanda3.socket.utils.IOSPushUtil;

public class IOSPushThread extends Thread {

	private AlarmMessageQueue alarmMessageQueue;

	public IOSPushThread(AlarmMessageQueue alarmMessageQueue) {
		this.alarmMessageQueue = alarmMessageQueue;
	}

	@Override
	public void run() {
		SendMessageExceptionCallBack back = new SendMessageExceptionCallBack();
		Message message = null;
			for (int i = 0; i < alarmMessageQueue.size(); i++) {
				try {
					message = alarmMessageQueue.poll();
					IOSPushUtil.push(message.getDevice(), message.getMessage(),message.getUrl(), Integer.parseInt(message.getTotal()));
				} catch (Exception e) {
					ExceptionCallBackBean backBean = new ExceptionCallBackBean();
					backBean.setCount(0);
					backBean.setVid(message.getVid());
					back.setIos(backBean);
				}
			}
		if (back.getIos().size() > 0) {
			ExceptionCallBackBean backBean = null;
			for (int i = 0; i < 3; i++) {
				for (ExceptionCallBackBean ba : back.getIos()) {
					backBean = ba;
					try {
						IOSPushUtil.push(message.getDevice(),message.getMessage(), message.getUrl(), 1);
						back.removeIOSExceptionMessage(ba);
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (back.getIos().size() > 0) {
				back.updateIOSExceptionMessage(backBean);
			}
		}
	}
}
