package com.wanda3.socket.filter;


import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.google.gson.Gson;
import com.wanda3.socket.entity.Message;
import com.wanda3.socket.utils.ByteUtil;

public class Decoder extends CumulativeProtocolDecoder{

	private final Charset charset;  
	private final Gson gson = new Gson();
   
    public Decoder(Charset charset) {  
        this.charset = charset;  
   
    }  
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,ProtocolDecoderOutput out) throws Exception {
		@SuppressWarnings("unused")
		CharsetDecoder cd = charset.newDecoder();  
		if(in.remaining() > 0){ 
            byte [] sizeBytes = new byte[4];   
            in.mark();  
            in.get(sizeBytes);  
            int size = ByteUtil.bytes2Int(sizeBytes);
            if(size > in.remaining()){
                in.reset();   
                return false; 
            } else{   
                byte[] bytes = new byte[size];    
                in.get(bytes, 0, size);   
                String json = new String(bytes,"UTF-8");   
                if(null != json && json.length() > 0){   
                    Message header = gson.fromJson(json, Message.class);
                    if(header != null){   
                        out.write(header);   
                    }   
                }   
                if(in.remaining() > 0){
                    return true;   
                }   
            }   
        }   
        return false;

	}


}
