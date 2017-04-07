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
		System.out.println("����˽���:"+obj[0]);
		//�����ʽ��createMap+6+6
		if(obj[0].equals("createMap")&&obj.length==3){
			int row=Integer.parseInt(obj[1]);
			int col=Integer.parseInt(obj[2]);
			gameMap=new GameMap(row,col);
			session.write(gameMap);
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
				session.write(gameMap);
				System.out.println("server:�޸ĵ�ͼ�ɹ�");
			}
		}else{
			System.out.println("����������޷�ʶ�����������룡");
		}
    	/*if (message instanceof UserInfo) {     
            UserInfo text = (UserInfo) message;     
            System.out.println("���������յ��ӿͻ��˵�������"+text.getName());     
            System.out.println("���������յ��ӿͻ��˵�QQ��"+text.getQQNum());     
        }      */
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