package com.test.server;

import org.apache.mina.core.filterchain.IoFilterAdapter;  
import org.apache.mina.core.service.IoHandler;  
import org.apache.mina.core.session.IdleStatus;  
import org.apache.mina.core.session.IoSession;  

import com.assignment2.object.GameMap;
import com.test.object.UserInfo;
  
public class ServerHandler extends IoFilterAdapter implements IoHandler {  
    private static ServerHandler samplMinaServerHandler = null;  
    private GameMap gameMap;
    public static ServerHandler getInstances() {  
        if (null == samplMinaServerHandler) {  
            samplMinaServerHandler = new ServerHandler();  
        }  
        return samplMinaServerHandler;  
    }  
  
    private ServerHandler() {  
  
    }  
  
    // 当连接后打开时触发此方法，一般此方法与 sessionCreated 会被同时触发  
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
		System.out.println("服务端接收:"+obj[0]);
		//命令格式：createMap+6+6
		if(obj[0].equals("createMap")&&obj.length==3){
			int row=Integer.parseInt(obj[1]);
			int col=Integer.parseInt(obj[2]);
			gameMap=new GameMap(row,col);
			session.write(gameMap);
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
				session.write(gameMap);
				System.out.println("server:修改地图成功");
			}
		}else{
			System.out.println("输入的命令无法识别，请重新输入！");
		}
    	/*if (message instanceof UserInfo) {     
            UserInfo text = (UserInfo) message;     
            System.out.println("服务器接收到从客户端的姓名："+text.getName());     
            System.out.println("服务器接收到从客户端的QQ："+text.getQQNum());     
        }      */
    }  
  
    public void exceptionCaught(IoSession arg0, Throwable arg1)  
            throws Exception {  
  
    }  
  
    // 当消息传送到客户端后触发  
    public void messageSent(IoSession arg0, Object arg1) throws Exception {  
          
    }  
  
    // 当一个新客户端连接后触发此方法.  
    public void sessionCreated(IoSession arg0) throws Exception {  
          
    }  
  
    // 当连接空闲时触发此方法.  
    public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {  
          
    }

	@Override
	public void inputClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}  
  
}  