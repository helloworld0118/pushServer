package com.wanda3.socket;


import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.wanda3.socket.filter.CoderFactory;
import com.wanda3.socket.handler.ServerHandler;

public class WDSocket {

	private static IoAcceptor acceptor;
	public void connection(int port){
		acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());//日志记录
		acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new CoderFactory()));
		//启动线程池
		acceptor.getFilterChain().addLast("executor",new ExecutorFilter(5, 800));
		acceptor.setHandler(new ServerHandler());
		acceptor.setDefaultLocalAddress(new InetSocketAddress(port));
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 12);

		try {
			acceptor.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 启动消息推送应用，指定端口号供APP客户端和APP Server调用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new WDSocket().connection(8888);
	}
}
