package com.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.assignment2.object.GameMap;

public class Test {
	public static void main(String[] args) throws IOException {
//		System.out.println("shenyucong\ncongcong\nshenshen");
//		String c="1 3 5";
//		String cc[]=c.split(" ");
//		System.out.println(Integer.parseInt(cc[0])+" "+Integer.parseInt(cc[1])+" "+Integer.parseInt(cc[2]));
//		GameMap m=new GameMap(6,6);
//		System.out.println(m);
		File file=new File("src/com/assignment4/data/userCount.txt");
        if(!file.exists())
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file,true);        
        StringBuffer sb=new StringBuffer();
        sb.append("cc");
        out.write(sb.toString().getBytes("utf-8"));
        out.close();

	}
}
