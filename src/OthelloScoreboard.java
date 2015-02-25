   //Name______________________________ Date_____________
import javax.swing.*;
import java.awt.*;
public class OthelloScoreboard extends JPanel
{
   private JLabel label1, label2, label3;
   int p1, p2;
   private int turn, win1/*white*/, win2/*black*/, maxturn;
      //labels for player turn and tile counting
      //ints to keep track of player turn and wins
   public OthelloScoreboard()
   {
      label1 = new JLabel("Computer: 0", SwingConstants.CENTER);
      label1.setHorizontalAlignment(SwingConstants.CENTER);
      label1.setBackground(Color.YELLOW);
      label1.setOpaque(true);
      add(label1);
      label2 = new JLabel("Othello");
      label2.setHorizontalAlignment(SwingConstants.CENTER);
      label2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
      add(label2);
      label3 = new JLabel("Comp: 0", SwingConstants.CENTER);
      label3.setHorizontalAlignment(SwingConstants.CENTER);
      label3.setBackground(Color.GRAY.brighter());
      label3.setOpaque(true);
      add(label3);
   
   }
   public void update(int[][]matrix, int turn)
   {
      if(turn % 2 == 0)
      {
    	 label3.setOpaque(true);
    	 label3.setBackground(Color.YELLOW);
         label1.setOpaque(false);
      }
      else
      {
    	 label1.setOpaque(true);
    	 label1.setBackground(Color.YELLOW);
         label3.setOpaque(false);
      }  
      p1 = 0;
      p2 = 0;
      
		for(int x = 1; x < matrix.length-1; x++)
		{
			for(int y = 1; y < matrix[x].length-1; y++)
			{
				if(matrix[x][y] == 1)
					p1++;
				else if(matrix[x][y] == 0)
					p2++;
			}
		}
      
		label1.setText("Player: " + p1);
		label3.setText("Computer: " + p2);
      /*if(turn == maxturn)
      {
         if(1 > 1)
         {
            win1++;
            label1.setText("Player One: " + win1);
         }
         else if (1 > 1)
         {
            win2++;
            label3.setText("Player Two: " + win2);
         }
      }*/
      /**
      add one to the variable for turns
      if(variable%2 is even)
         -highlight background of player 1's label
         
      if(variable%2 is odd)
         -highlight background of player 2's label
         
      because only one tile can be played at a time, the maximum value for turn will always
      remain the same.
      
      if(turn = maximum value for turn)
         if(player one has more tiles)
            -add 1 to player 1's wins
         else
            -add 1 to player 2's wins
      */
   }
}