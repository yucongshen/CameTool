package com.assignment1.client;

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

public class MyClient1 {
	private static final String host="127.0.0.1";
	private static final int port=7080;
	public static void main(String[] args) {
		IoSession session=null;
		IoConnector connector=new NioSocketConnector();
		connector.setConnectTimeoutMillis(3000);
		//���ù�����
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
		//session.write("��ã�watchmen");
		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}
	public static void sendData(IoSession session){
		@SuppressWarnings("resource")
		Scanner input=new Scanner(System.in); 
		while(!session.isClosing()){
			System.out.print("������һ��������");
			System.out.println();
			int number=input.nextInt();
			session.write(number+"");
		}
	}
}
class MyClientHandler extends IoHandlerAdapter {

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String msg=(String) message;
		System.out.println("������˵��"+msg);
		if(msg.equals("�¶���!")){
			session.closeNow();
		}
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		if(status==IdleStatus.READER_IDLE){
			session.closeNow();
		}
	}
}
