
package com.wanda3.socket.utils;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Configer
{
	
	private static final Log	log			= LogFactory.getLog(Configer.class);
	private static Properties	properties	= new Properties();
	
	static
	{
		try
		{
			properties.load(Thread.currentThread().getContextClassLoader().getResource("config.properties").openStream());
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}
	
	public static void reLoad()
	{
		try
		{
			properties.clear();
			properties.load(Thread.currentThread().getContextClassLoader().getResource("config.properties").openStream());
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}
	
	public static String get(String key)
	{
		return properties.getProperty(key);
	}
	
	public static String get(int key)
	{
		return properties.getProperty(String.valueOf(key));
	}
	
	public static int getInt(String key)
	{
		return Integer.parseInt(properties.getProperty(key));
	}
	
	public static boolean getBool(String key)
	{
		return Boolean.parseBoolean(properties.getProperty(key));
	}
}
