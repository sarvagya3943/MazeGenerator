import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.security.auth.x500.X500Principal;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	// JFrame width and height 
	private static int width = 400 , height = 400 ;  
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Maze Generator") ;
		Panel panel = new Panel(); 
		frame.setSize(width , height) ; 
		// dont allow user to resize the window 
		frame.setResizable(false) ; 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ; 
		frame.setVisible(true) ; 
		frame.add(panel) ; 
	}
}
class Panel extends JPanel implements ActionListener {
	private int width = 400 , height = 400 ;
	public int rows , cols , w = 10 ; // number of rows,cols in a grid and width of a grid cell 
	private Cell Current , Next ; 
	private Timer timer ; // needed for the animation 
	private Stack<Cell> stack ;
	float red , green , blue ; 
	private ArrayList<Cell> grid = new ArrayList<Cell>() ; // grid represented as an Arraylist  
	// JPanel constructor 
	public Panel() 
	{
		cols = (int)(width/w) ; 
		rows = (int)(height/w) ;
		for(int j = 0 ; j < cols ; ++j) {
			for(int i = 0 ; i < rows ; ++i) {
				grid.add(new Cell(i, j)) ; 
			}
		}
		Random random = new Random() ; 
		red = random.nextFloat() ; 
		blue = random.nextFloat() ; 
		green = random.nextFloat() ; 
		stack = new Stack<Cell>() ; 
		timer = new Timer(0,this); 
		timer.start(); 
		Current = grid.get(0) ; 
		Current.visited = true ; 
		setBackground(Color.DARK_GRAY) ; 
	}
	// returns the index of cell (i,j) in arraylist 
	public int index(int i,int j) {
		if(i < 0 || j < 0 || i >= rows || j >= cols) return -1 ; 
		return i + j * cols ; 
	}
	// return a random unvisited neighbor of the current cell 
	public Cell checkNeighbors(Cell a) {
		int index = -1 ; 
		Cell[] arr = new Cell[4] ; 
		int r = a.getr() , c = a.getc() ; 
		int top = (index(r,c-1) == -1) ? -1 : index(r,c-1) ; 
		int right = (index(r + 1,c) == -1) ? -1 : index(r + 1,c) ; 
		int bottom = (index(r,c+1) == -1) ? -1 : index(r,c+1) ; 
		int left = (index(r-1,c) == -1) ? -1 : index(r-1,c) ;
		if(top != -1 && grid.get(top).visited==false) {
			arr[++index] = grid.get(top) ; 
		}
		if(right != -1 && grid.get(right).visited==false) {
			arr[++index] = grid.get(right) ; 
		}
		if(bottom != -1 && grid.get(bottom).visited==false) {
			arr[++index] = grid.get(bottom) ; 
		}
		if(left != -1 && grid.get(left).visited==false) {
			arr[++index] = grid.get(left) ; 
		}
		if(index >= 0) {
			Random random = new Random();
			int t = random.nextInt(index + 1) ; 
			return arr[t] ;  
		}
		else return new Cell(-1, -1) ; 
	}
	@Override 
	public void paint(Graphics g)
	{
		super.paint(g) ;
		g.setColor(Color.white) ;
		for(Cell cell : grid) {
			int x = cell.getr() * w , y = cell.getc() * w ;
			// draw the walls of the cell 
			if(cell.isWall(0)) {
				g.drawLine(x, y, x + w, y ) ; 
			}
			if(cell.isWall(1)) {
				g.drawLine(x + w, y , x + w , y + w) ; 
			}
			if(cell.isWall(2)) {
				g.drawLine(x + w,	 y + w, x , y + w) ; 
			}
			if(cell.isWall(3)) {
				g.drawLine(x , y + w, x, y) ; 
			}
			// visited and unvisited cells have different colors 
			if(cell.visited) {
				g.setColor(new Color((int)(red * 255) , (int)(green * 255) , (int)(blue * 255) , 100)) ;
				g.fillRect(x, y, w, w) ;
				g.setColor(Color.white) ; 
			}
			// distinguish the current cell from the rest 
			if(cell.equals(Current)) {
				g.setColor(new Color(0,255,0)) ; 
				g.fillRect(x, y, w, w) ; 
				g.setColor(Color.white) ; 
			}
		}		
	}
	// remove the wall between the current and next cell 
	public void removeWalls(Cell a,Cell b)
	{
		int dx = a.getr() - b.getr() ;
		int dy = a.getc() - b.getc() ;
		if(dx == 1) {
			a.setWall(3, false) ; 
			b.setWall(1, false) ; 
		}
		else if(dx == -1) {
			a.setWall(1, false) ; 
			b.setWall(3, false) ; 
		}
		else if(dy == -1) {
			a.setWall(2, false) ; 
			b.setWall(0, false) ; 
		}
		else if(dy == 1) {
			a.setWall(0, false) ; 
			b.setWall(2, false) ; 
		}
		
	}
	// Timer calls this method every frame 
	@Override
	public void actionPerformed(ActionEvent e) {
		// Standard DFS approach 
		Cell next = checkNeighbors(Current) ; 
		// found a valid neighbor 
		if(next.getr() != -1) {
			next.visited = true ;
			stack.add(Current) ; 
			removeWalls(Current,next) ; 
			Current = next ; 
		}
		// couldn't find anything , backtrack! 
		else if(stack.empty()==false) {
			Current = stack.pop() ; 
		}
		// repaint the panel every frame 
		repaint() ; 
	}
}

