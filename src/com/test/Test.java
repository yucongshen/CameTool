package com.test;

import com.assignment2.object.GameMap;

public class Test {
	public static void main(String[] args) {
		System.out.println("shenyucong\ncongcong\nshenshen");
		String c="1 3 5";
		String cc[]=c.split(" ");
		System.out.println(Integer.parseInt(cc[0])+" "+Integer.parseInt(cc[1])+" "+Integer.parseInt(cc[2]));
		GameMap m=new GameMap(6,6);
		System.out.println(m);
		
	}
}
