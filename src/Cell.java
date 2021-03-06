import java.awt.Graphics;

import javax.sql.RowSet;


class Cell {
	// Coordinates of the top-left corner of the grid cell 
	private int r , c ;
	/* Store which all cells are present in the grid
	* walls[0] = top wall 
	* walls[1] = right wall
	* walls[2] = bottom wall
	* walls[3] = left wall
	*/
	private boolean[] walls = new boolean[4] ; 
	public boolean visited , isOnStack ; 
	Cell(int r,int c )
	{
		this.r = r ; 
		this.c = c ; 		
		// initially which all walls are present 
		for(int i = 0 ; i < 4 ; ++i ) {
			walls[i] = true ; 
		}
		visited = false ; 
		isOnStack = false ; 
	}
	
	public int getr() {return r ;}
	public int getc() {return c ;}
	// return true if the wall is present 
	public boolean isWall(int index) {
		return walls[index] ; 
	}
	// set the wall's visibility 
	public void setWall(int index,boolean f) {
		walls[index] = f ; 
	}
	public String toString() {
		return "(" + r + "," + c + ")" ;
	}
	
}
