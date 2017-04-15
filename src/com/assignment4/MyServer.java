package com.assignment4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.alibaba.fastjson.JSON;
import com.assignment3.object.GameMap;
import com.assignment4.object.UserCount;


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
	private GameMap gameMap;
	private Integer userCount=0;
	private Map<String,String> map=new HashMap<String,String>();
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println("MyServer->exceptionCaught");
		System.out.println(cause.toString());
	}
	
	public void bind(String key,String object){
		map.put(key,object);
	}
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String msg=(String)message;
		System.out.println("msg:"+msg);
		String obj[]=msg.split("[+]");
		System.out.println("length:"+obj.length);
		System.out.println("����˽���:"+obj[0]);
		//�����ʽ��createMap+6+6
		if(obj[0].equals("createMap")&&obj.length==3){
			int row=Integer.parseInt(obj[1]);
			int col=Integer.parseInt(obj[2]);
			gameMap=new GameMap(row,col);
			gameMap.setState(2);
			String gameMapJson = JSON.toJSONString(gameMap);   
			//bind����
			bind("map",gameMapJson);
			//session.write(gameMap.toString());
			System.out.println("server��������һ����ͼ");
		//�����ʽ��changeMap+2+2+6
		}else if(obj[0].equals("changeMap")&&obj.length==4){
			int row=Integer.parseInt(obj[1]);
			int col=Integer.parseInt(obj[2]);
			int value=Integer.parseInt(obj[3]);
			if(gameMap.getArray()==null){
				System.out.println("��ͼ������");
			}else{
				int[][] tmp=gameMap.getArray();
				tmp[row][col]=value;
				gameMap.setArray(tmp);
				//session.write(gameMap.toString());
				System.out.println("server:�޸ĵ�ͼ�ɹ�");
			}
		}else if(obj[0].equals("map")){
			String object=map.get(obj[0]);
			session.write(object);
		}else{
			System.out.println("����������޷�ʶ�����������룡");
		}
	}

	@Override
	public void messageSent(IoSession session, Object message)
			throws Exception {
		System.out.println("MyServer->messageSent");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		this.userCount--;
		System.out.println("MyServer->���û������ˣ���ǰʱ����"+userCount+"���û�����");
		UserCount userCountObject=new UserCount();
		userCountObject.setUserCount(userCount);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(new Date());
		userCountObject.setDateTime(dateString);
		String jsonCount=JSON.toJSONString(userCountObject); 
		System.out.println(jsonCount);
		writeJsonToFile(jsonCount);
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
		this.userCount++;
		System.out.println("MyServer->�����û������ˣ���ǰʱ����"+userCount+"���û�����");
		UserCount userCountObject=new UserCount();
		userCountObject.setUserCount(userCount);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(new Date());
		userCountObject.setDateTime(dateString);
		String jsonCount=JSON.toJSONString(userCountObject); 
		System.out.println(jsonCount);
		writeJsonToFile(jsonCount);
	}
	public static void writeJsonToFile(String userCountJson) throws IOException{
		File file=new File("src/com/assignment4/data/userCount.txt");
        if(!file.exists())
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file,true);        
        StringBuffer sb=new StringBuffer();
        sb.append(userCountJson);
        sb.append("\n");
        out.write(sb.toString().getBytes("utf-8"));
        out.close();
    }
}

