package com.wanda3.socket.filter;


import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class CoderFactory implements ProtocolCodecFactory {

	private final Encoder encoder;
	private final Decoder decoder;

	public CoderFactory() {
		this(Charset.defaultCharset());
	}

	public CoderFactory(Charset charSet) {
		this.encoder = new Encoder(charSet);
		this.decoder = new Decoder(charSet);
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}
}
