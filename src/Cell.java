import java.awt.Graphics;

import javax.sql.RowSet;


class Cell {

	private int r , c ;
	private boolean[] walls = new boolean[4] ; 
	public boolean visited ; 
	Cell(int r,int c )
	{
		this.r = r ; 
		this.c = c ; 		
		for(int i = 0 ; i < 4 ; ++i ) {
			walls[i] = true ; 
		}
		visited = false ; 
	}
	
	public int getr() {return r ;}
	public int getc() {return c ;}
	public boolean isWall(int index) {
		return walls[index] ; 
	}
	public void setWall(int index,boolean f) {
		walls[index] = f ; 
	}
	public String toString() {
		return "(" + r + "," + c + ")" ;
	}
	
}
