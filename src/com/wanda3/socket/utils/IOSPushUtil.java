
package com.wanda3.socket.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;

public class IOSPushUtil
{
	
	private static final int				port				= Integer.parseInt(Configer.get("iosPushPort"));
	private static final String				host				= Configer.get("iosPushHost");
	private static String					certificatePath		= Configer.get("certificatePath");					// 导出的证书
	private static String					certificatePassword	= Configer.get("iosPushPassword");					// 此处注意导出的证书密码不能为空因为空密码会报错
	private static PushNotificationManager	pushManager			= PushNotificationManager.getInstance();
	static
	{
		try
		{
			pushManager.initializeConnection(host, port, certificatePath, certificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
		}
		catch (UnrecoverableKeyException e)
		{
			e.printStackTrace();
		}
		catch (KeyManagementException e)
		{
			e.printStackTrace();
		}
		catch (KeyStoreException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (CertificateException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchProviderException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static synchronized void push(String token, String content,String url, int num)
	{
		
		try
		{
			
			PayLoad payLoad = new PayLoad();
			payLoad.addAlert(content);// push的内容
			payLoad.addBadge(num);// 图标小红圈的数值
			payLoad.addCustomDictionary("url", url);
			payLoad.addSound("default");// 铃音
			
			// if(pushManager.getDevice("iPhone")!=null){
			// pushManager.removeDevice("iPhone");
			// }
			pushManager.addDevice("iPhone", token);
			
			// Connect to APNs
			/************************************************
			 * 测试的服务器地址：gateway.sandbox.push.apple.com /端口2195
			 * 产品推送服务器地址：gateway.push.apple.com / 2195
			 ***************************************************/
			
			// Send Push
			Device client = pushManager.getDevice("iPhone");
			
			pushManager.sendNotification(client, payLoad);
			pushManager.stopConnection();
			pushManager.removeDevice("iPhone");
			
			// System.out.println(token);
			// System.out.println(content);
		}
		catch (Exception e)
		{
			// System.out.println(e.toString());
			// e.printStackTrace();
		}
		
	}
	public static void main(String[] args)
	{
		 for (int i = 1; i < 10; i++)
		 {
			 IOSPushUtil.push("9ad294ea6d8f6c6a831f114611e353c9029bddbd0269310175a777b76777ed02", "测试推送","", 1);
			 System.out.println("-----------------"+i);
		 }
		
	}
	
}
