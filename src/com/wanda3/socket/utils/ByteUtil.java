package com.wanda3.socket.utils;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ByteUtil {

	public static String read(InputStream in) throws IOException
	{
		@SuppressWarnings("unused")
		int len =0;
		String json = null;
		byte[] bflen = new byte[4];
		while (null != in && (len = in.read(bflen)) > 0) {
			int blen = bytes2Int(bflen);
			bflen = null;
				byte[]  tmpBuffer = new byte[blen];
				int c = in.read(tmpBuffer, 0, blen);
				if(c > 0){
				return new String(tmpBuffer,"UTF-8");
			}
		}
		return json;
	}
	
	public static int bytes2Int(byte[] b)
	{
		if (b.length != 4)
		{
			return 0;
		}
		int value = 0;
		for (int i = 0; i < 4; i++)
		{
			value += (b[i] & 0xFF) << 8 * i;
		}
		return value;
	}
	
	public static byte[] int2Bytes(int intValue)
	{
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++)
		{
			b[i] = (byte) (intValue >> 8 * i & 0xFF);
		}
		return b;
	}
	public static void string2bytebuffer(String json, OutputStream out) throws IOException
	{
		byte[] h = json.getBytes("UTF-8");
		int len = h.length;
		byte[] bl = ByteUtil.int2Bytes(len);
		ByteBuffer buffer = ByteBuffer.allocate(4 + len);
		buffer.put(bl, 0, 4);
		buffer.put(h, 0, len);
		byte[] b = buffer.array();
		int nRead = 0;
		InputStream in = new ByteArrayInputStream(b); 
		byte[] nb = new byte[1024*8];
		while ((nRead = in.read(nb)) != -1)
		{
			out.write(nb, 0, nRead);
			if(b.length>1024){
				out.flush();
			}
		}
		if(b.length>1024){
			out.flush();
		}
	}
}
