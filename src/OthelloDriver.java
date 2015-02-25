   //Torbert, e-mail: mr@torbert.com, website: www.mr.torbert.com
	//version 7.14.2003

   import javax.swing.JFrame;
   public class OthelloDriver
   {
      public static void main(String[] args)
      {
         JFrame frame = new JFrame("Final Project: OTHELLO");
         frame.setSize(400, 500);
         frame.setLocation(200, 100);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setContentPane(new Othello());
         frame.setVisible(true);
      }
   }