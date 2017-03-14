package com.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class MyServer {
	private static final int port=7080;
	public static void main(String[] args) {
		try {
			IoAcceptor acceptor=new NioSocketAcceptor();
			//���ñ��������
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory(
					Charset.forName("UTF-8"),
					LineDelimiter.WINDOWS.getValue(),
					LineDelimiter.WINDOWS.getValue()
				)
			));
			//���ö��������Ĵ�С
			acceptor.getSessionConfig().setReadBufferSize(1024);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			acceptor.setHandler(new MyServerHandler());
			acceptor.bind(new InetSocketAddress(port));
			System.out.println("MyServer start..."+port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
class MyServerHandler extends IoHandlerAdapter {
	private static final int realNumber=100;
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println("MyServer->exceptionCaught");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String msg=(String)message;
		int number=Integer.parseInt(msg);
		System.out.println("����˽���:"+number+"������ʵ���ݽ��бȽ�...");
		if(number<realNumber){
			session.write("С�ˣ�");
		}else if(number>realNumber){
			session.write("����!");
		}else{
			session.write("�¶���!");
			session.closeNow();
		}
	}

	@Override
	public void messageSent(IoSession session, Object message)
			throws Exception {
		System.out.println("MyServer->messageSent");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("MyServer->sessionClosed");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("MyServer->sessionCreated");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("MyServer->sessionIdle");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("MyServer->sessionOpened");
	}
}

