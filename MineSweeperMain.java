package minesweeper;
import java.awt.*;        // Use AWT's Layout Manager
import java.awt.event.*;  // Use AWT's Event handlers
import javax.swing.*;     // Use Swing's Containers and Components
/**
 * The Mine Sweeper Game.
 * Left-click to reveal a cell.
 * Right-click to plant/remove a flag for marking a suspected mine.
 * You win if all the cells not containing mines are revealed.
 * You lose if you reveal a cell containing a mine.
 */
@SuppressWarnings("serial")
public class MineSweeperMain extends JFrame {
   // private variables
   static GameBoard board = new GameBoard();
   static MineMap map = new MineMap();
   int gameMode = 1; // difficulty selector (easy == 0, normal == 1, hard == 2, insane == 3)

   JButton btnNewGame = new JButton("New Game");
   JMenuBar menu = new JMenuBar();

   // Constructor to set up all the UI and game components
   public MineSweeperMain() {

      Container cp = this.getContentPane();           // JFrame's content-pane
      cp.setLayout(new BorderLayout()); // in 10x10 GridLayout
      cp.add(board, BorderLayout.CENTER);

      // Add a button to the south to re-start the game
      add(btnNewGame, BorderLayout.SOUTH);
      btnNewGame.setFont(new Font("Arial", Font.BOLD, 26));
      btnNewGame.setBorder(BorderFactory.createEtchedBorder(0));
      btnNewGame.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            if (e.getSource() == btnNewGame) {
               if (gameMode == 1) {
                  board.init();
               }
               else if (gameMode == 0) {
                  board.initEasy();
               }
               else if (gameMode == 2) {
                  board.initHard();
               }
               else {board.initInsane();}
               JOptionPane.showMessageDialog(null, "* New game has been created. Have fun!");
            }
         }
      });
   
      // JMenuBar items
      JMenu file = new JMenu("File");
      JMenu options = new JMenu("Options");
      JMenu difficulty = new JMenu ("Difficulty");
      JMenu help = new JMenu("Help");

      // Reset functions
      JMenuItem NEW_GAME = new JMenuItem("New Game");
      JMenuItem RESET_GAME = new JMenuItem("Reset Game");
      JMenuItem EXIT = new JMenuItem("Exit");

      // cheats and debugging tools
      JMenuItem revealMines = new JMenuItem("Show Mines");
      JMenuItem hideMines = new JMenuItem("Hide Mines");

      // difficulty modes
      JMenuItem easy = new JMenuItem("Easy");
      JMenuItem normal = new JMenuItem("Normal");
      JMenuItem hard = new JMenuItem("Hard");
      JMenuItem insane = new JMenuItem("Insanity");

      // help bar
      JMenuItem hints = new JMenuItem("How to play");
      JMenuItem diffGuide = new JMenuItem("Difficulty guide");

      menu.add(file);
      file.add(NEW_GAME);
      file.add(RESET_GAME);
      file.add(EXIT);
      menu.add(options);
      options.add(revealMines);
      options.add(hideMines);
      menu.add(difficulty);
      difficulty.add(easy);
      difficulty.add(normal);
      difficulty.add(hard);
      difficulty.add(insane);
      menu.add(help);
      help.add(hints);
      help.add(diffGuide);
      setJMenuBar(menu);

      // action listeners for Menu Bar
      NEW_GAME.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            if (e.getSource() == NEW_GAME) {
               if (gameMode == 1) {
                  board.init();
               }
               else if (gameMode == 0) {
                  board.initEasy();
               }
               else if (gameMode == 2) {
                  board.initHard();
               }
               else {board.initInsane();}
               JOptionPane.showMessageDialog(null, "* New game has been created. Have fun!");
            }
         }
      });
      RESET_GAME.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            if (e.getSource() == RESET_GAME) {
               board.initResetOnly();
               JOptionPane.showMessageDialog(null, "* Game has been reset, mine location stays the same. Have fun!");
            }
         }
      });
      EXIT.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            if (e.getSource() == EXIT) {
               System.exit(0);
            }
         }
      });
      hideMines.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            if (e.getSource() == hideMines) {
               board.initHideMines();
               JOptionPane.showMessageDialog(null, "* Mines have been hidden.");
            }
         }
      });
      revealMines.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            if (e.getSource() == revealMines) {
               board.initShowMines();
               JOptionPane.showMessageDialog(null, "* Mines are now shown.");
            }
         }
      });
      easy.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            if (e.getSource() == easy) {
               board.initEasy();
               gameMode = 0;
               JOptionPane.showMessageDialog(null, 
                  "* You are now playing on easy mode." +
                  "\n* There are currently "+ 30 + " Mines."
               );
               setTitle("Minesweeper (Easy Difficulty)");
            }
         }
      });
      normal.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            if (e.getSource() == normal) {
               board.init();
               gameMode = 1;
               JOptionPane.showMessageDialog(null, 
                  "* You are now playing on normal mode." +
                  "\n* There are currently "+ 50 + " Mines."
               );
               setTitle("Minesweeper (Normal Difficulty)");
            }
         }
      });
      hard.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            if (e.getSource() == hard) {
               board.initHard();
               gameMode = 2;
               JOptionPane.showMessageDialog(null, 
                  "* You are now playing on hard mode." +
                  "\n* There are currently "+ 90 + " Mines."
               );
               setTitle("Minesweeper (Hard Difficulty)");
            }
         }
      });
      insane.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            if (e.getSource() == insane) {
               board.initInsane();
               gameMode = 3;
               JOptionPane.showMessageDialog(null, 
                  "* You are now playing on insanity mode." +
                  "\n* There are currently "+ 150 + " Mines."
               );
               setTitle("Minesweeper (Insane Difficulty)");
            }
         }
      });
      hints.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            if (e.getSource() == hints) {
               JOptionPane.showMessageDialog(null, 
                  "* The Mine Sweeper Game." + 
                  "\n* Left-click to reveal a cell." +
                  "\n* Right-click to plant/remove a flag for marking a suspected mine." +
                  "\n* You win if all the cells not containing mines are revealed." +
                  "\n* You lose if you reveal a cell containing a mine. Good Luck!", "How to play", JOptionPane.INFORMATION_MESSAGE
               );
            }  
         }
      });
      diffGuide.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            if (e.getSource() == diffGuide) {
               JOptionPane.showMessageDialog(null, 
                  "* Easy Mode has 30 Mines." + 
                  "\n* Normal Mode is the default and has 50 Mines." +
                  "\n* Hard Mode has 90 Mines." +
                  "\n* Insanity Mode has 150 Mines", "Difficulty guide", JOptionPane.INFORMATION_MESSAGE
               );
            }  
         }
      });

      pack();  // Pack the UI components, instead of setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // handle window-close button
      setTitle("Minesweeper (Normal Difficulty)");
      setSize(720, 720);
      setVisible(true);   // show it
      setResizable(false); // frame stays constant
   }

   // The entry main() method
   public static void main(String[] args) {
      // Recommended to run GUI codes in Event Dispatching thread
      // for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new MineSweeperMain(); // Let the constructor do the job
         }
      });
   }
}