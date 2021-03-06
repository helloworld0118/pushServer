package com.wanda3.ThreadPool;



import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.wanda3.socket.entity.Message;

	/**
	 * 报警阻塞队列
	 * 
	 * @author sxy
	 */
	public class AlarmMessageQueue {

	    private Logger                        logger            =  Logger.getLogger(AlarmMessageQueue.class);

	    //队列大小
	    public static final int               QUEUE_MAX_SIZE    = 100;

	    private static AlarmMessageQueue      alarmMessageQueue = new AlarmMessageQueue();
	   
	    //阻塞队列
	    private BlockingQueue<Message> blockingQueue     = new LinkedBlockingQueue<Message>(QUEUE_MAX_SIZE);
	    public static AlarmMessageQueue getInstance() {
	        return alarmMessageQueue;
	    }

	    /**
	     * 消息入队
	     * @param alarmMessageVO
	     * @return
	     */
	    public  boolean push(Message Header) {
	        return  this.blockingQueue.offer(Header);
	    }

	    /**
	     * 消息出队
	     * @return
	     */
	    public Message poll() {
	    	Message result = null;
	        try {
	            result = this.blockingQueue.take();
	        } catch (InterruptedException e) {
	            logger.error("消息出队失败", e);
	        }
	        return result;
	    }

	    /**
	     * 获取队列大小
	     * @return
	     */
	    public int size() {
	        return this.blockingQueue.size();
	    }
}
