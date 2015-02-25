	//Name______________________________ Date_____________
   import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
   
   public class Othello extends JPanel
   {
      private JButton[][] board;
      private int[][] matrix;
      private int turn = 1;
      public int countb, countw;
      private JLabel label;
      private JButton reset;
      private OthelloScoreboard scoreboard;
      private HashMap<String, Move> moves;
      private ArrayList<Move> moves_list = new ArrayList<Move>();
      private int count = 0;
      boolean handler1_continue = true;
      int player_could_not_move_correction_factor = 0;
      Move ai_move;
      
      public Othello()
      {
      /** 
          Create a 8 by 8 gameboard of buttons that overlays a 8 by 8 matrix.
          Calls Reset to set up the starting arrangement for Othello
          Listener1 will respond to each button when they are pressed. 
          Create a 'Reset' button which will reset the board to the starting arrangement
      */
    	  try { 
    	        UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() ); 
    	}  
    	catch (Exception e) {
    	    // TODO Auto-generated catch block 
    	    e.printStackTrace();
    	}
    	  
    	  setLayout(new BorderLayout());
         JPanel center = new JPanel();
         center.setLayout(new GridLayout(8,8));
         add(center, BorderLayout.CENTER);
         board = new JButton[10][10];
         matrix = new int[10][10];
         for(int r = 1; r < board.length - 1; r++)
            for(int c = 1; c < board[r].length - 1; c++)
            {
               board[r][c] = new JButton();
               board[r][c].setOpaque(true);
               board[r][c].setBackground(Color.green.darker());
               board[r][c].addActionListener( new Handler1(r, c) );
               center.add(board[r][c]);
            }
         board[4][4].setEnabled(false);
         board[4][5].setEnabled(false);
         board[5][4].setEnabled(false);
         board[5][5].setEnabled(false);
      		
         
         for(int x = 0; x < matrix.length; x++)
   		{
   			for(int y = 0; y < matrix[x].length; y++)
   			{
   				matrix[x][y] = 2;
   			}
   		}
            
   		for(int i = 0; i < matrix.length; i++)
   		{
   			matrix[i][0] = 5;
   			matrix[i][9] = 5;
   			matrix[9][i] = 5;
   			matrix[0][i] = 5;
   		}
      		
      	matrix[4][4] = 1;
		   matrix[5][4] = 0;
		   matrix[4][5] = 0;
		   matrix[5][5] = 1;
         
         JPanel north = new JPanel();
         north.setLayout(new FlowLayout());
         add(north, BorderLayout.NORTH);
         scoreboard = new OthelloScoreboard();
         north.add(scoreboard);
         
         JPanel south = new JPanel();
         north.setLayout(new FlowLayout());
         add(south, BorderLayout.SOUTH);
         reset = new JButton();
         reset.addActionListener(new Handler2());
         reset.setText("   Reset   ");
         south.add(reset, BorderLayout.SOUTH);
         print(matrix);
         
         moves = findMoves(matrix, turn%2);
		
   		for(int x = 1; x < matrix.length-1; x++)
   		{
   			for(int y = 1; y < matrix[x].length-1; y++)
   			{
   				if(moves.get(x + "," + y) != null)
               {
   					matrix[x][y] = 3;
               } 
   			}
   		}
         
         update(matrix);
         scoreboard.update(matrix, turn);
//         new Thread(new Runnable() 
//			{
//				public void run() 
//				{
//					//animate_flip(4, 5, 1);
//				}
//			}).start();
      }
      
      private class Handler1 implements ActionListener
      {
         private int myRow, myCol;
         public Handler1(int r, int c)
         {
            myRow = r;
            myCol = c;
         }
         public void actionPerformed(ActionEvent e)
         {
         /**
            a variable 'turn' will keep track of who's turn it is.  If it is even, player 1's turn.
            if it is odd, player 2's turn. Depending on who's turn it is, clicking a button will set
            the underlying int matrix's value to a one or a two.  Depending on the value of the in matrix,
            the button will turn either white or black.
            
            converts appropriate tiles to the other color after a tile is place.
            
            Also calls update on the othello scoreboard
            */
        	 new Thread(new Runnable() 
 			{
 				public void run() 
 				{
 					doAction(myRow, myCol);
 				}
 			}).start();
         	
         }
      }
      
      public void doAction(int myRow, int myCol)
      {
    	  if(turn%2 == 1)//player turn
          {
             //matrix[myRow][myCol] = 1;
             for(int x = 1; x < board.length-1; x++)
             {
            	 for(int y = 1; y < board[x].length-1; y++)
            	 {
            		 board[x][y].setEnabled(false);
            	 }
             }
             System.out.println("PLAYER WENT");
             
          }
          else//computer turn
          {
         	 //if(myRow != -1 && myCol != -1)
         		 //matrix[myRow][myCol] = 0;
         	 System.out.println("AI WENT");
          	handler1_continue = true;
	      	
          }
         if(!moves.isEmpty()) 
         {
        	 int[][] directions = moves.get(myRow + "," + myCol).getDirections();
				for(int i = 0; i < directions.length; i++)
					if(directions[i][0] == 1)
						goDirection(matrix, myRow, myCol, directions[i][1], directions[i][2], turn%2, true);
				
         }
         else
         {
        	 player_could_not_move_correction_factor++;
        	 if(turn%2 == 1)
        		 System.out.println("PLAYER COULD NOT MOVE");
        	 else
        		 System.out.println("AI COULD NOT MOVE");
         }
         
         
         turn++;
         
        for(int x = 1; x < matrix.length-1; x++)//Resets all the moves for the next player
		   {
   			for(int y = 1; y < matrix[x].length-1; y++)
   			{
   				if(matrix[x][y] == 3)
               {
   					matrix[x][y] = 2;
               }
   			}
		   }
         
         moves = findMoves(matrix, turn%2);
		 
         
   		for(int x = 1; x < matrix.length-1; x++)//changes the matrix for the move spots
		   {
   			for(int y = 1; y < matrix[x].length-1; y++)
   			{
   				if(moves.get(x + "," + y) != null)
               {
   					matrix[x][y] = 3;
               }
   			}
		   }
         
         scoreboard.update(matrix, turn);
         update(matrix);
         
         if(turn >= 61 + player_could_not_move_correction_factor || scoreboard.p1 == 0 || scoreboard.p2 == 0)//Case for loser or winner
         {
        	 if(scoreboard.p1 > scoreboard.p2)
        	 {
        		 showWinner(0);
        	 }
        	 else
        		 showWinner(1);
        	 //reset.doClick();
        	 return;
         }
         
         if(turn%2 == 0 || moves.isEmpty())
         {
        	 for(int x = 1; x < board.length-1; x++)
	        {
		       	 for(int y = 1; y < board[x].length-1; y++)
		       	 {
		       		 board[x][y].setEnabled(false);
		       	 }
	        }
        	 
        	Move m = ai_choose(matrix, moves_list, turn%2, 5);
            ai_move = m;
	       
	       new Thread(new Runnable() 
			{
				public void run() 
				{
					doAction(ai_move.mX, ai_move.mY);
				}
			}).start();
         }
         else
         {
        	 for(int x = 1; x < board.length-1; x++)
 	        {
 		       	 for(int y = 1; y < board[x].length-1; y++)
 		       	 {
 		       		 board[x][y].setEnabled(false);
 		       	 }
 	        }
         	 
         	Move m = ai_choose(matrix, moves_list, turn%2, 5);
             ai_move = m;
 	       
           //doAction(ai_move.mX, ai_move.mY);
             
 	       new Thread(new Runnable() 
 			{
 				public void run() 
 				{
 					doAction(ai_move.mX, ai_move.mY);
 				}
 			}).start();
         }
      }
      
      private class Handler2 implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            /**
               Handler for Reset button
               places 2 white and 2 black tiles in the middle of the board as such:
               x = white o = black    ' xo '
                                      ' ox '
               resets the count for white and black tiles
               amount of wins for each player should remain the same
            */
            board[4][4].setEnabled(false);
            board[4][5].setEnabled(false);
            board[5][4].setEnabled(false);
            board[5][5].setEnabled(false);
         		
            for(int x = 0; x < matrix.length; x++)
      		{
      			for(int y = 0; y < matrix[x].length; y++)
      			{
      				matrix[x][y] = 2;
      			}
      		}
               
      		for(int i = 0; i < matrix.length; i++)
      		{
      			matrix[i][0] = 5;
      			matrix[i][9] = 5;
      			matrix[9][i] = 5;
      			matrix[0][i] = 5;
      		}
         		
      		matrix[4][4] = 1;
   		   matrix[5][4] = 0;
   		   matrix[4][5] = 0;
   		   matrix[5][5] = 1;
            moves = findMoves(matrix, turn%2);
		      turn = 1;
      		for(int x = 1; x < matrix.length-1; x++)
      		{
      			for(int y = 1; y < matrix[x].length-1; y++)
      			{
      				if(moves.get(x + "," + y) != null)
      				{
      					matrix[x][y] = 3;
      				}
      				
      			}
      		}
      		scoreboard.update(matrix, turn);
            update(matrix);
            
            
         }
      }
      
      private static boolean canPlay(int[][] matrix, int x, int y, int turn)
      {
         /**
         method that checks for positions that a player can play a tile.
            Requirements for this are:
            1) must be next to/ diagonal to an existing enemy tile
            2) must have a friendly tile on the opposite side (ex. xoooooox) x = white o = black
            if there are no available places for a player to play, their turn is skipped
            if there are no available places for either player, the game is over
            the player with the most tiles of their color wins.
         */
         return true;
      }
      
      public void flip(int x, int y, int turn)
      {
         /**
            Method that flips the global matrix for the given position and for the color based on the turn.
            Must check in each direction where it can be flipped
         */

      }
      
   public void update(int[][] matrix)
      {
         for(int x = 1; x < matrix.length-1; x++)
         {
            for(int y = 1; y < matrix[x].length-1; y++)
            {
               if(matrix[x][y] == 0)
               {
                  board[x][y].setBackground(Color.black);
                  if(board[x][y].isEnabled())//This sets block just clicked by the ai to a dark gray
                  {
                	  board[x][y].setEnabled(false);
                	  board[x][y].setBackground(Color.DARK_GRAY);
                  } 
                  board[x][y].setText("");
               }
               else if(matrix[x][y] == 1)
               {
                  board[x][y].setBackground(Color.white);
                  board[x][y].setText("");
               }
               else if(matrix[x][y] == 3)
               {
                  board[x][y].setBackground(Color.yellow);
                  board[x][y].setFocusable(false);
                  board[x][y].setEnabled(true);
                  board[x][y].setText("");
                  if(moves.get(x + "," + y) != null)
                  {
                	  board[x][y].setText("" + moves.get(x + "," + y).mCount);
                  }
               }
               else if(matrix[x][y] == 2)
               {
                  board[x][y].setBackground(Color.green.darker());
                  board[x][y].setEnabled(false);
                  board[x][y].setText("");
               }
            }
         }
      }
   public static void print(int[][] matrix)
	{
		int count = 0;
		for(int x = 0; x < matrix.length; x++)
		{
			for(int y = 0; y < matrix[x].length; y++)
			{
				System.out.print(matrix[x][y] + " ");
			}
			System.out.println("");
		}
	}
   
   public HashMap<String, Move> findMoves(int[][] matrix, int turn)
	{
		HashMap<String, Move> moves = new HashMap<String, Move>();
		moves_list.clear();
		for(int x = 1; x < matrix.length-1; x++)
		{
			for(int y = 1; y < matrix[x].length-1; y++)
			{
				if(borders(matrix, x, y))
				{
					int[][] directions = new int[8][3];
					
					
					count = 0;
					
					directions[0][0] = goDirection(matrix, x, y, 0, 1, turn, false);
					directions[0][1] = 0;
					directions[0][2] = 1;
					directions[1][0] = goDirection(matrix, x, y, 1, 1, turn, false);
					directions[1][1] = 1;
					directions[1][2] = 1;
					directions[2][0] = goDirection(matrix, x, y, 1, 0, turn, false);
					directions[2][1] = 1;
					directions[2][2] = 0;
					directions[3][0] = goDirection(matrix, x, y, 1, -1, turn, false);
					directions[3][1] = 1;
					directions[3][2] = -1;
					directions[4][0] = goDirection(matrix, x, y, 0, -1, turn, false);
					directions[4][1] = 0;
					directions[4][2] = -1;
					directions[5][0] = goDirection(matrix, x, y, -1, -1, turn, false);
					directions[5][1] = -1;
					directions[5][2] = -1;
					directions[6][0] = goDirection(matrix, x, y, -1, 0, turn, false);
					directions[6][1] = -1;
					directions[6][2] = 0;
					directions[7][0] = goDirection(matrix, x, y, -1, 1, turn, false);
					directions[7][1] = -1;
					directions[7][2] = 1;
					
					Move m = new Move(directions, x, y);
					if(m.isMove())
					{
						m.setCount(count);
						count = 0;
						moves.put(x + "," + y, m);
						moves_list.add(m);
					}
				}
			}
		}
		
		return moves;
	}
   
   public static boolean borders(int[][] matrix, int x, int y)
	{
		if(matrix[x][y] == 1 || matrix[x][y] == 0)//condition if current spot is already occupied
			return false;
		else if(matrix[x+1][y] == 1 || matrix[x+1][y] == 0)
			return true;
		else if(matrix[x-1][y] == 1 || matrix[x-1][y] == 0)
			return true;
		else if(matrix[x][y+1] == 1 || matrix[x][y+1] == 0)
			return true;
		else if(matrix[x+1][y+1] == 1 || matrix[x+1][y+1] == 0)
			return true;
		else if(matrix[x][y-1] == 1 || matrix[x][y-1] == 0)
			return true;
		else if(matrix[x-1][y-1] == 1 || matrix[x-1][y-1] == 0)
			return true;
		else if(matrix[x+1][y-1] == 1 || matrix[x+1][y-1] == 0)
			return true;
		else if(matrix[x-1][y+1] == 1 || matrix[x-1][y+1] == 0)
			return true;
		else 
			return false;
	}
   public int goDirection(int[][] m, int x, int y, int xD, int yD, int turn, boolean flip)
	{
		int coord_x = x;
		int coord_y = y;
		int op_turn;
		int c = 0;
		if(turn == 0)//Set op_turn to the opposite of turn where turn is either 1 or 0
			op_turn = 1;
		else
			op_turn = 0;
		
		
		if(flip && matrix[x][y] != turn)
		{
			matrix[x][y] = turn;//change the matrix for the first case
			animate_flip(x, y, turn);
		}
		
		coord_x += xD;
		coord_y += yD;
			
		while(m[coord_x][coord_y] == op_turn)
		{
			if(flip)
			{
				matrix[coord_x][coord_y] = turn;
				animate_flip(coord_x, coord_y, turn);
			}
			
			coord_x += xD;
			coord_y += yD;
			
			count++;
			c++;
		}
		
		if(m[coord_x][coord_y] == turn && c > 0)
			return 1;
		else
		{
			count -= c;
			return 0;
		}
	}

   public Move ai_choose(int[][] matrix, ArrayList<Move> moves, int turn, int level)
	{
	   
	   //System.out.println("Level: " + level);
	   int op_turn;
		
		if(turn == 0)//Set op_turn to the opposite of turn where turn is either 1 or 0
			op_turn = 1;
		else
			op_turn = 0;
		
		for(int i = 0; i < moves.size(); i++)
		{
			Move m = moves.get(i);
			m.addRank(m.mCount);
			
			if(isCorner(m))
			{
				m.addRank(100000);
			}
			else if(isForceCorner(m, matrix, turn))
			{
				if(level%2 == 0)
					System.out.println("AI: X: " + m.mX + " Y: " + m.mY);
				else
					System.out.println("Possible move: X: " + m.mX + " Y: " + m.mY);
				m.addRank(10000);
			}
			else if(isBorder(m))
				m.addRank(50);
			else if(isCornerVolatile(m))
				m.addRank(-55);
			else if(isBorderVolatile(m, matrix, turn) == 1 || isBorderVolatile(m, matrix, turn) == -1)
			{
				if(isBorderVolatile(m, matrix, turn) == -1)
					return m;
				else
					m.addRank(-10);
			}
			if(level > 0)
			{
				int[][] move_board = new int[10][10];
				
				for(int x = 0; x < move_board.length; x++)
				{
					for(int y = 0; y < move_board[x].length; y++)
					{
						move_board[x][y] = matrix[x][y];
					}
				}
				
				
				int[][] directions = m.getDirections();
				for(int w = 0; w < directions.length; w++)
					if(directions[w][0] == 1)	
						move_board = getMoveBoard(move_board, m.mX, m.mY, directions[w][1], directions[w][2], turn, true);

				HashMap<String, Move> op_turn_moves = findMovesStatic(move_board, op_turn);
				ArrayList<Move> op_turn_moves_list = new ArrayList<Move>();
				for(int x = 1; x < move_board.length-1; x++)
				{
					for(int y = 1; y < move_board[x].length-1; y++)
					{
						if(op_turn_moves.get(x + "," + y) != null)
	      				{
							op_turn_moves_list.add(op_turn_moves.get(x + "," + y));
	      				}
					}
				}
				
				Move op_m = ai_choose(move_board, op_turn_moves_list, op_turn, level-1);
					
				m.addRank((int)(-1*op_m.mRank));
				
			}

		}

		return chooseBiggestRank(moves);
	}
	
	public static boolean isCorner(Move m)
	{
		if((m.mY == 1 || m.mY == 8) && (m.mX == 1 || m.mX == 8))
			return true;
		else
			return false;
	}
	
	public static boolean isBorder(Move m)
	{
		if((m.mY == 1 || m.mY == 8) || (m.mX == 1 || m.mX == 8))
			return true;
		else
			return false;
	}
	
	public static boolean isCornerVolatile(Move m)
	{
		if((m.mY == 2 || m.mY == 7) && (m.mX == 2 || m.mX == 7))
			return true;
		else
			return false;
	}
	
	public static int isBorderVolatile(Move m, int[][] matrix, int turn)
	{	
		if(isBorder(m))
			return 0;
		else if((m.mY == 2 || m.mY == 7) || (m.mX == 2 || m.mX == 7))
		{
			/*if(m.mX >=2  && m.mX <=7)
			{
				/*if(m.mY == 2)
				{
					if(matrix[m.mX][m.mY+6] == turn)
					{
						System.out.println("NO BORDER VOLATILE FOUND");
						return -1;
					}
				}
				else if(m.mY == 8)
					if(matrix[m.mX][m.mY-6] == turn)
					{
						
						System.out.println("NO BORDER VOLATILE FOUND");
						return -1;
					}*/
			}
			else if(m.mY >=2  && m.mY <=7)
			{
				/*if(m.mX == 2)
				{
					if(matrix[m.mX+6][m.mY] == turn)
					{
						System.out.println("NO BORDER VOLATILE FOUND");
						return -1;
					}
				}
				else if(m.mX == 8)
					if(matrix[m.mX-6][m.mY] == turn)
					{
						System.out.println("NO BORDER VOLATILE FOUND");
						return -1;
					}
			}*/
			//else
				return 1;
		}	
		
		return 0;
	}
	
	public static boolean isForceCorner(Move m, int[][] matrix, int turn)
	{
		int op_turn;
		
		if(turn == 0)//Set op_turn to the opposite of turn where turn is either 1 or 0
			op_turn = 1;
		else
			op_turn = 0;
		
		if(m.mX == 4 && m.mY == 6)
		{
			int f = 0;
			f++;
		}
		
		if(((matrix[m.mX + 1][m.mY] == op_turn) && (matrix[m.mX - 1][m.mY] == op_turn)) ||
				((matrix[m.mX][m.mY + 1] == op_turn) && (matrix[m.mX][m.mY - 1] == op_turn)))//has bordering opps
		{
			
			if((((m.mX == 1) || (m.mX == 8)) && ((m.mY == 3) || (m.mY == 6))) ||
					(((m.mY == 1) || (m.mY == 8)) && ((m.mX == 3) || (m.mX == 6))))
			{
				System.out.println("FORCE CORNER FOUND!!!");
				return true;
			}
			
		}
		return false;
	}
	
	public static int[][] getMoveBoard(int[][] matrix, int x, int y, int xD, int yD, int turn, boolean flip)
	{
		int coord_x = x;
		int coord_y = y;
		int op_turn;
		int count = 0;
		
		if(turn == 0)//Set op_turn to the opposite of turn where turn is either 1 or 0
			op_turn = 1;
		else
			op_turn = 0;
		
		
		if(flip)
			matrix[x][y] = turn;//change the matrix for the first case
		
		coord_x += xD;
		coord_y += yD;
			
		while(matrix[coord_x][coord_y] == op_turn)
		{
			if(flip)
				matrix[coord_x][coord_y] = turn;
			
			coord_x += xD;
			coord_y += yD;
			
			count++;
		}
			
		return matrix;
	}
	
	public static Move chooseBiggestRank(ArrayList<Move> moves)
	{
		int max_index = 0;
		if(moves.isEmpty())
		{
			Move m = new Move(new int[8][3], -1, -1);
			m.addRank(-1000000000);
			return m;
		}
		
		for(int i = 0; i < moves.size(); i++)
		{
			if(moves.get(i).getRank() >= moves.get(max_index).getRank())
				max_index = i;
		}
		return moves.get(max_index);
	}
	
	public HashMap<String, Move> findMovesStatic(int[][] matrix, int turn)
	{
		HashMap<String, Move> moves = new HashMap<String, Move>();
		for(int x = 1; x < matrix.length-1; x++)
		{
			for(int y = 1; y < matrix[x].length-1; y++)
			{
				if(borders(matrix, x, y))
				{
					int[][] directions = new int[8][3];
					
					
					count = 0;
					
					directions[0][0] = goDirection(matrix, x, y, 0, 1, turn, false);
					directions[0][1] = 0;
					directions[0][2] = 1;
					directions[1][0] = goDirection(matrix, x, y, 1, 1, turn, false);
					directions[1][1] = 1;
					directions[1][2] = 1;
					directions[2][0] = goDirection(matrix, x, y, 1, 0, turn, false);
					directions[2][1] = 1;
					directions[2][2] = 0;
					directions[3][0] = goDirection(matrix, x, y, 1, -1, turn, false);
					directions[3][1] = 1;
					directions[3][2] = -1;
					directions[4][0] = goDirection(matrix, x, y, 0, -1, turn, false);
					directions[4][1] = 0;
					directions[4][2] = -1;
					directions[5][0] = goDirection(matrix, x, y, -1, -1, turn, false);
					directions[5][1] = -1;
					directions[5][2] = -1;
					directions[6][0] = goDirection(matrix, x, y, -1, 0, turn, false);
					directions[6][1] = -1;
					directions[6][2] = 0;
					directions[7][0] = goDirection(matrix, x, y, -1, 1, turn, false);
					directions[7][1] = -1;
					directions[7][2] = 1;
					
					Move m = new Move(directions, x, y);
					if(m.isMove())
					{
						m.setCount(count);
						count = 0;
						moves.put(x + "," + y, m);
					}
				}
			}
		}
		
		return moves;
	}

	public void showWinner(int turn)
	{
		if(turn == 0)
			JOptionPane.showMessageDialog(this, "Congratulations, you win!!");
		else
			JOptionPane.showMessageDialog(this, "Game Over!\nThere's always next time ;)");
	}
	
	public void animate_flip(int x, int y, int turn)
	{
		int count = 35;
		int total_wait = 600;
		board[x][y].setText("");
		
		
		if(turn == 1)//black to white
		{
			Color c = new Color(0.0f,0.0f, 0.0f);
			for(int i = 0; i < count; i ++)
			{
				c = c.brighter();
				board[x][y].setBackground(c);
				try{
					Thread.sleep(total_wait/count);
				}
				catch(Exception e) {}
			}
			
		}
		else//white to black
		{
			Color c = new Color(1.0f,1.0f, 1.0f);
			board[x][y].setBackground(c);
			for(int i = 0; i < count; i ++)
			{
				c = c.darker();
				board[x][y].setBackground(c);
				try{
					Thread.sleep(total_wait/count);
				}
				catch(Exception e) {}
			}
		}
	}

}