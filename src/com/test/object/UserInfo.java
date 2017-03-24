package com.test.object;

public class UserInfo implements java.io.Serializable{  
    private String name;  
    private String QQNum;  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public String getQQNum() {  
        return QQNum;  
    }  
    public void setQQNum(String qQNum) {  
        QQNum = qQNum;  
    }  
}
