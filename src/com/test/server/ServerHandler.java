package com.test.server;

import org.apache.mina.core.filterchain.IoFilterAdapter;  
import org.apache.mina.core.service.IoHandler;  
import org.apache.mina.core.session.IdleStatus;  
import org.apache.mina.core.session.IoSession;  

import com.assignment2.object.GameMap;
  
public class ServerHandler extends IoFilterAdapter implements IoHandler {  
    private static ServerHandler samplMinaServerHandler = null;  
  
    public static ServerHandler getInstances() {  
        if (null == samplMinaServerHandler) {  
            samplMinaServerHandler = new ServerHandler();  
        }  
        return samplMinaServerHandler;  
    }  
  
    private ServerHandler() {  
  
    }  
  
    // �����Ӻ��ʱ�����˷�����һ��˷����� sessionCreated �ᱻͬʱ����  
    public void sessionOpened(IoSession session) throws Exception {  
    }  
    public void sessionClosed(IoSession session) {  
    }  
    public void messageReceived(IoSession session, Object message)  
            throws Exception {    
    	String msg=(String)message;
		System.out.println("msg:"+msg);
		String obj[]=msg.split("[+]");
		System.out.println("length:"+obj.length);
		if(obj[0].equals("setMap")){
			System.out.println("����˽���:"+obj[0]);
			System.out.println("length:"+obj.length);
			int row=Integer.parseInt(obj[1]);
			int col=Integer.parseInt(obj[2]);
			System.out.println(row+""+col);
			GameMap gameMap=new GameMap(row,col);
			session.write("asdf");
			System.out.println("server��������һ����ͼ");
		}
    }  
  
    public void exceptionCaught(IoSession arg0, Throwable arg1)  
            throws Exception {  
  
    }  
  
    // ����Ϣ���͵��ͻ��˺󴥷�  
    public void messageSent(IoSession arg0, Object arg1) throws Exception {  
          
    }  
  
    // ��һ���¿ͻ������Ӻ󴥷��˷���.  
    public void sessionCreated(IoSession arg0) throws Exception {  
          
    }  
  
    // �����ӿ���ʱ�����˷���.  
    public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {  
          
    }

	@Override
	public void inputClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}  
  
}  