package com.assignment2;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MyClient {
	private static final String host="127.0.0.1";
	private static final int port=7080;
	public static void main(String[] args) {
		IoSession session=null;
		IoConnector connector=new NioSocketConnector();
		connector.setConnectTimeoutMillis(3000);
		//…Ë÷√π˝¬À∆˜
		connector.getFilterChain().addLast("coderc", new ProtocolCodecFilter(
				new TextLineCodecFactory(Charset.forName("UTF-8"),
					LineDelimiter.WINDOWS.getValue(),
					LineDelimiter.WINDOWS.getValue()
				))
		);
		connector.getSessionConfig().setReadBufferSize(1024);
		connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		connector.setHandler(new MyClientHandler());
		ConnectFuture connneFutrure=connector.connect(new InetSocketAddress(host,port));
		connneFutrure.awaitUninterruptibly();
		session=connneFutrure.getSession();
		sendData(session);
		//session.write("ƒ„∫√£°watchmen");
		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}
	public static void sendData(IoSession session){
		@SuppressWarnings("resource")
		Scanner input=new Scanner(System.in); 
		while(!session.isClosing()){
			System.out.print("«Î ‰»Î√¸¡Ó£∫(createMap+6+6 or changeMap+2+2+6)");
			System.out.println();
			String command=input.next();
			session.write(command);
		}
	}
}
class MyClientHandler extends IoHandlerAdapter {

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		
		String gameMap=(String) message;
		System.out.println(gameMap);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		if(status==IdleStatus.READER_IDLE){
			session.closeNow();
		}
	}
}
