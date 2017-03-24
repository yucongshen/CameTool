package com.assignment2.object;

public class GameMap {
	int[][] array=null;
	public GameMap(int row,int col){
		array=new int[row][col];
	}
	public GameMap(){
		
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
				result+=array[i][j];
			}
			result+="\n";
		}
		return result;
	}
}
