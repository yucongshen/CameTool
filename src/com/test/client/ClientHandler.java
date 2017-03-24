package com.test.client;

import java.util.Scanner;

import org.apache.mina.core.service.IoHandlerAdapter;  
import org.apache.mina.core.session.IoSession;  

import com.test.object.GameMap;
  
public class ClientHandler extends IoHandlerAdapter {  
    private static ClientHandler samplMinaClientHandler = null;  
    public static ClientHandler getInstances() {  
        if (null == samplMinaClientHandler) {  
            samplMinaClientHandler = new ClientHandler();  
        }  
        return samplMinaClientHandler;  
    }  
  
    private ClientHandler() {  
  
    }  
  
    public void sessionOpened(IoSession session) throws Exception {  
        session.write("�ͻ�����������ĻỰ���ˡ���");  
        @SuppressWarnings("resource")
		Scanner input=new Scanner(System.in); 
		//while(!session.isClosing()){
			System.out.print("���������");
			System.out.println();
			String command=input.next();
			session.write(command);
		//}
        /*UserInfo text=new UserInfo();  
        text.setName("÷����");  
        text.setQQNum("972341215");  
        session.write(text);  */
    }  
  
    public void sessionClosed(IoSession session) {  
    }  
  
    public void messageReceived(IoSession session, Object message)  
            throws Exception {  
    	System.out.println("�ͻ�����Ⱦ");
    	GameMap map=(GameMap) message;
		System.out.println("length:"+map.getArray().length);
    }  
  
    public void messageSent(IoSession session, Object message) throws Exception {  
        System.out.println("�ͻ����Ѿ�������������ˣ�"+(String)message);  
    }  
}
