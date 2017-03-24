package com.test.object;

public class GameMap implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int[][] array=null;
	public GameMap(int row,int col){
		array=new int[row][col];
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
}
