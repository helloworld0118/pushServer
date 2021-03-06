package com.wanda3.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	private ExecutorService pool;
	public ThreadPool(int poolsize){
		pool = Executors.newFixedThreadPool(poolsize);
	}
	public void poll(AlarmMessageQueue alarmMessageQueue){
	   pool.execute(new IOSPushThread(alarmMessageQueue));
	}
	
}
