package com.wanda3.socket.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;

import com.google.gson.Gson;
import com.wanda3.socket.callback.SendMessageExceptionCallBack;
import com.wanda3.socket.entity.ExceptionCallBackBean;
import com.wanda3.socket.entity.Message;

/**
 * 
 * 链路创建  打开  收发消息   空闲监听  异常捕获   （所有消息的统一入口）
 * 
 * @author song
 *
 */
public class ServerHandler implements IoHandler{
	private static Logger logger = Logger.getLogger(ServerHandler.class);
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("session created");
		 SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();   
	     cfg.setReceiveBufferSize(2 * 1024 * 1024);   
	     cfg.setReadBufferSize(2 * 1024 * 1024);   
	     cfg.setKeepAlive(true);   
	     cfg.setSoLinger(0); 
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("session opened");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.info("session closed");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		logger.info("session idle");
		session.close(true);
		
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)throws Exception {
		logger.info("exception caught");
		String vid = (String)session.getAttribute("vid");
		ExceptionCallBackBean backBean = new ExceptionCallBackBean();
		backBean.setCount(0);
		backBean.setVid(vid);
		SendMessageExceptionCallBack.setAndroid(backBean);
	}

	@Override 
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		logger.info("message received");
		MessageReceived.execute((Message)message, session);
		System.out.println("--------------"+new Gson().toJson(message));
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("message sent");
	}

}
