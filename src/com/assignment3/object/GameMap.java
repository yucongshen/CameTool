package com.assignment3.object;

public class GameMap {
	private int[][] array=null;
	private int state;
	public GameMap(int row,int col){
		array=new int[row][col];
	}
	public GameMap(){
		
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int[][] getArray() {
		return array;
	}

	public void setArray(int[][] array) {
		this.array = array;
	}
	public void printMap(){
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array[0].length;j++){
				System.out.print(array[i][j]);
			}
			System.out.println();
		}
	}
	public String toString(){
		String result="";
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array[0].length;j++){
				array[i][j]=this.getState();
			}
		}
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array[0].length;j++){
				result+=array[i][j]+" ";
			}
			result+="\n";
		}
		return result;
	}
}
