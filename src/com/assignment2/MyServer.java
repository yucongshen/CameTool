package com.assignment2;

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

import com.assignment2.object.GameMap;


public class MyServer {
	private static final int port=7080;
	public static void main(String[] args) {
		try {
			IoAcceptor acceptor=new NioSocketAcceptor();
			//设置编码过滤器
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory(
					Charset.forName("UTF-8"),
					LineDelimiter.WINDOWS.getValue(),
					LineDelimiter.WINDOWS.getValue()
				)
			));
			//设置读缓冲区的大小
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
	private GameMap gameMap;
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println("MyServer->exceptionCaught");
		System.out.println(cause.toString());
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		
		String msg=(String)message;
		System.out.println("msg:"+msg);
		String obj[]=msg.split("[+]");
		System.out.println("length:"+obj.length);
		System.out.println("服务端接收:"+obj[0]);
		//命令格式：createMap+6+6
		if(obj[0].equals("createMap")&&obj.length==3){
			int row=Integer.parseInt(obj[1]);
			int col=Integer.parseInt(obj[2]);
			gameMap=new GameMap(row,col);
			session.write(gameMap.toString());
			System.out.println("server：创建了一个地图");
		//命令格式：changeMap+2+2+6
		}else if(obj[0].equals("changeMap")&&obj.length==4){
			int row=Integer.parseInt(obj[1]);
			int col=Integer.parseInt(obj[2]);
			int value=Integer.parseInt(obj[3]);
			if(gameMap.getArray()==null){
				System.out.println("地图不存在");
			}else{
				int[][] tmp=gameMap.getArray();
				tmp[row][col]=value;
				gameMap.setArray(tmp);
				session.write(gameMap.toString());
				System.out.println("server:修改地图成功");
			}
		}else{
			System.out.println("输入的命令无法识别，请重新输入！");
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

