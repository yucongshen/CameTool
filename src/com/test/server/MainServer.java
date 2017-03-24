package com.test.server;

import java.io.IOException;  
import java.net.InetSocketAddress;  
  
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;  
import org.apache.mina.filter.codec.ProtocolCodecFilter;  
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;  
import org.apache.mina.transport.socket.SocketAcceptor;  
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;  
  
public class MainServer {  
    private static MainServer mainServer = null;  
    private SocketAcceptor acceptor = new NioSocketAcceptor();  
    private DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();  
    private int bindPort = 8888;  
  
    public static MainServer getInstances() {  
        if (null == mainServer) {  
            mainServer = new MainServer();  
        }  
        return mainServer;  
    }  
  
    private MainServer() {  
        chain.addLast("myChin", new ProtocolCodecFilter(  
                new ObjectSerializationCodecFactory()));  
        acceptor.setHandler(ServerHandler.getInstances());  
        try {  
            acceptor.bind(new InetSocketAddress(bindPort));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static void main(String[] args) throws Exception {  
        MainServer.getInstances();  
    }  
}  