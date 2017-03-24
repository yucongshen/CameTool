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
        session.write("客户端与服务器的会话打开了……");  
        @SuppressWarnings("resource")
		Scanner input=new Scanner(System.in); 
		//while(!session.isClosing()){
			System.out.print("请输入命令：");
			System.out.println();
			String command=input.next();
			session.write(command);
		//}
        /*UserInfo text=new UserInfo();  
        text.setName("梅竹寒香");  
        text.setQQNum("972341215");  
        session.write(text);  */
    }  
  
    public void sessionClosed(IoSession session) {  
    }  
  
    public void messageReceived(IoSession session, Object message)  
            throws Exception {  
    	System.out.println("客户端渲染");
    	GameMap map=(GameMap) message;
		System.out.println("length:"+map.getArray().length);
    }  
  
    public void messageSent(IoSession session, Object message) throws Exception {  
        System.out.println("客户端已经向服务器发送了："+(String)message);  
    }  
}
