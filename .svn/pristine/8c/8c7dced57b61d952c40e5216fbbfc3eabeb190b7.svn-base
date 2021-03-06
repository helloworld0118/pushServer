package com.wanda3.socket.filter;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.google.gson.Gson;
import com.wanda3.socket.utils.ByteUtil;

public class Encoder extends ProtocolEncoderAdapter{

	private final Charset charset;
	private final Gson gson = new Gson();
    public Encoder(Charset charset) {
        this.charset = charset;
 
    }
 
    @Override
    public void encode(IoSession session, Object msg, ProtocolEncoderOutput arg2)
            throws Exception {
 
        charset.newEncoder();
        String json = gson.toJson(msg);
        byte[] h = json.getBytes("UTF-8");
        int len = h.length;
        byte[] bl = ByteUtil.int2Bytes(len);
        IoBuffer buffer = IoBuffer.allocate(len+4).setAutoExpand(true);
        buffer.put(bl, 0, 4);
		buffer.put(h, 0, len);
        buffer.flip();
        arg2.write(buffer);
 
    }


}
