package minesweeper;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class GameBoard extends JPanel {
   // Name-constants for the game properties
   public static final int ROWS = 20;      // number of cells
   public static final int COLS = 20;
   public static final int FONT_SIZE = 26;
   public static final int EASY_MODE = -20, HARD_MODE = 40, INSANE_MODE = 100; // additional mines for difficulty modes

   // Name-constants for UI sizes
   public static final int CELL_SIZE = 100;  // Cell width and height, in pixels
   public static final int CANVAS_WIDTH = CELL_SIZE * COLS; // Game board width/height
   public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;

   Cell cells[][] = new Cell[ROWS][COLS];
   MineMap mines = new MineMap();
   int numberMines = mines.getNumMines(); // default number of mines
   int gameModes = 1; // difficulty selector (easy == 0, normal == 1, hard == 2, insane == 3)
   boolean showMinesTF; // toggle shown Mines on or off
   CellMouseListener mouseListener = new CellMouseListener();

   // to store adjacent cells using revealSurrounding method and reveal them recursively with MouseClicked ActionEvent (left click)
   ArrayList<Cell> storeSurroundingCell = new ArrayList<Cell>();

   // Constructor
   public GameBoard() {
      super.setLayout(new GridLayout(ROWS, COLS, 0, 0));  // JPanel

      // Allocate the 2D array of Cell, and added into content-pane.
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col] = new Cell(row, col);
            cells[row][col].addMouseListener(mouseListener); // add MouseEvent Listener to every cell
            super.add(cells[row][col]); // add each cell as a button to GameBoard Panel
         }
      }
      
      // [TODO 3] Allocate a common listener as the MouseEvent listener for all the
      //  Cells (JButtons)
      // [TODO 4] Every cell adds this common listener
      // ......

      // Initialize for a new game
      init();

      // Set the size of the content-pane and pack all the components
      //  under this container.
      super.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
   }

   // Initialize and re-initialize a new game (normal difficulty is default)
   public void init() {
      // Get a new mine map;
      storeSurroundingCell.clear();
      mines.newMineMap(numberMines);
      System.out.println("init debug");

      // Reset cells, mines, and flags
      for (int row = 0; row < ROWS; row++) {
         for (int col = 0; col < COLS; col++) {
            // Initialize each cell with/without mine
            cells[row][col].init(mines.isMined[row][col]);
         }
      }
      gameModes = 1;
      showMinesTF = false;
   }

   // Initialise with the same mines location
   public void initResetOnly() {
      System.out.println("init debug Reset Only");
      storeSurroundingCell.clear();
      // do not get a new mine map
      // Reset cells, mines, and flags
      for (int row = 0; row < ROWS; row++) {
         for (int col = 0; col < COLS; col++) {
            // Initialize each cell with/without mine
            cells[row][col].init(mines.isMined[row][col]);
         }
      }
      showMinesTF = false;
   }

   // Initialize and show all Mines
   public void initShowMines() {
      System.out.println("init debug show Mines");

      // Do not reset cells, mines, and flags
      for (int row = 0; row < ROWS; row++) {
         for (int col = 0; col < COLS; col++) {
            // Initialize each cell with/without mine
            cells[row][col].initShowMines(mines.isMined[row][col]); // method to show mines from Cell class
            showMinesTF = true;
         }
      }
   }

   // Initialize and hide all Mines
   public void initHideMines() {
      System.out.println("init debug hide Mines");

      // Do not reset cells, mines, and flags
      for (int row = 0; row < ROWS; row++) {
         for (int col = 0; col < COLS; col++) {
            // Initialize each cell with/without mine
            cells[row][col].initHideMines(mines.isMined[row][col]); // method to hide mines from Cell class
            showMinesTF = false;
         }
      }
   }

   // Easy difficulty
   public void initEasy() {
      // Get a new mine map;
      storeSurroundingCell.clear();
      mines.newMineMap(numberMines+EASY_MODE);
      System.out.println("init debug easy");

      // Reset cells, mines, and flags
      for (int row = 0; row < ROWS; row++) {
         for (int col = 0; col < COLS; col++) {
            // Initialize each cell with/without mine
            cells[row][col].init(mines.isMined[row][col]);
         }
      }
      gameModes = 0;
      showMinesTF = false;
   }

   // Hard difficulty
   public void initHard() {
      // Get a new mine map;
      storeSurroundingCell.clear();
      mines.newMineMap(numberMines+HARD_MODE);
      System.out.println("init debug hard");

      // Reset cells, mines, and flags
      for (int row = 0; row < ROWS; row++) {
         for (int col = 0; col < COLS; col++) {
            // Initialize each cell with/without mine
            cells[row][col].init(mines.isMined[row][col]);
         }
      }
      gameModes = 2;
      showMinesTF = false;
   }

   // Insane difficulty
   public void initInsane() {
      // Get a new mine map;
      storeSurroundingCell.clear();
      mines.newMineMap(numberMines+INSANE_MODE);
      System.out.println("init debug insane");

      // Reset cells, mines, and flags
      for (int row = 0; row < ROWS; row++) {
         for (int col = 0; col < COLS; col++) {
            // Initialize each cell with/without mine
            cells[row][col].init(mines.isMined[row][col]);
         }
      }
      gameModes = 3;
      showMinesTF = false;
   }

   // Return the number of mines (0 - 8) in the 8 surrounding cells of the given cell.
   private int getSurroundingMines(Cell cell) {
      int minesFound = 0;
      for (int row = cell.row - 1; row <= cell.row + 1; row++) {
         for (int col = cell.col - 1; col <= cell.col + 1; col++) {
            // Need to ensure valid row and column numbers too
            if (row >= 0 && row < ROWS && col >= 0 && col < COLS && mines.isMined[row][col]) {
               minesFound++;
            }
         }
      }
      return minesFound;
   }

   // This cell has 0 surrounding mine. Reveal the 8 surrounding cells recursively
   private void revealSurrounding(Cell sourceCell) {
      if (sourceCell.row == 0) {
         if (sourceCell.col == 0) { // top left corner, add surrounding 3 cells to ArrayList
            storeSurroundingCell.add(cells[0][1]);
            storeSurroundingCell.add(cells[1][1]);
            storeSurroundingCell.add(cells[1][0]);
         }
         else if (sourceCell.col == 19) { // top right corner, add surrounding 3 cells to ArrayList
            storeSurroundingCell.add(cells[1][19]);
            storeSurroundingCell.add(cells[0][18]);
            storeSurroundingCell.add(cells[1][18]);
         }
         else { // upper row, add surrounding 5 cells to ArrayList
            storeSurroundingCell.add(cells[0][sourceCell.col-1]);
            storeSurroundingCell.add(cells[0][sourceCell.col+1]);
            storeSurroundingCell.add(cells[1][sourceCell.col-1]);
            storeSurroundingCell.add(cells[1][sourceCell.col]);
            storeSurroundingCell.add(cells[1][sourceCell.col+1]);
         }
      }
      else if (sourceCell.row == 19) { // bottom left corner, add surrounding 3 cells to ArrayList
         if (sourceCell.col == 0) {
            storeSurroundingCell.add(cells[18][0]);
            storeSurroundingCell.add(cells[18][1]);
            storeSurroundingCell.add(cells[19][1]);
         }
         else if (sourceCell.col == 19) { // bottom right corner, add surrounding 3 cells to ArrayList
            storeSurroundingCell.add(cells[19][18]);
            storeSurroundingCell.add(cells[18][19]);
            storeSurroundingCell.add(cells[18][18]);
         }
         else { // bottom row, add surrounding 5 cells to ArrayList
            storeSurroundingCell.add(cells[19][sourceCell.col-1]);
            storeSurroundingCell.add(cells[19][sourceCell.col+1]);
            storeSurroundingCell.add(cells[18][sourceCell.col-1]);
            storeSurroundingCell.add(cells[18][sourceCell.col]);
            storeSurroundingCell.add(cells[18][sourceCell.col+1]);
         }
      }
      else {
         if (sourceCell.col == 0) { // leftmost row, add surrounding 5 cells to ArrayList
            storeSurroundingCell.add(cells[sourceCell.row-1][0]);
            storeSurroundingCell.add(cells[sourceCell.row+1][0]);
            storeSurroundingCell.add(cells[sourceCell.row-1][1]);
            storeSurroundingCell.add(cells[sourceCell.row][1]);
            storeSurroundingCell.add(cells[sourceCell.row+1][1]);
         }
         else if (sourceCell.col == 19) { // rightmost row, add surrounding 5 cells to ArrayList
            storeSurroundingCell.add(cells[sourceCell.row-1][19]);
            storeSurroundingCell.add(cells[sourceCell.row+1][19]);
            storeSurroundingCell.add(cells[sourceCell.row-1][18]);
            storeSurroundingCell.add(cells[sourceCell.row][18]);
            storeSurroundingCell.add(cells[sourceCell.row+1][18]);
         }
         else { // anywhere in the middle, add all surrounding 8 cells to ArrayList
            for (int row = sourceCell.row - 1; row <= sourceCell.row + 1; row++) {
               for (int col = sourceCell.col - 1; col <= sourceCell.col + 1; col++) {
                  storeSurroundingCell.add(cells[row][col]);
               }
            }
         } // Afterwards, we will reveal all cells added to storeSurroundingMines ArrayList using MouseClicked ActionEvent (left click)
      }  
   }

   // Return true if the player has won (all cells have been revealed or were mined)
   public void winGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (cells[row][col].isMined == true) { // reveal all mined cells
               cells[row][col].setBackground(Color.GREEN);
               cells[row][col].setForeground(Color.BLACK);
               cells[row][col].setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
               cells[row][col].setText("X");
            }
         }
      }

      // allow player to restart the game
      String[] reply = {"New Game","Try Again","Quit"};
      int reset = JOptionPane.showOptionDialog(null, "Play again?", "You won! Well done!", JOptionPane.DEFAULT_OPTION, 0, null, reply, reply[0]);
      System.out.println("New game(win)? "+reset);
      if (reset == 0) { // New game option
         if (gameModes == 1) {
            init();
         }
         else if (gameModes == 0) {
            initEasy();
         }
         else if (gameModes == 2) {
            initHard();
         }
         else {initInsane();}
      }
      else if (reset == 1) { // reset game option
         initResetOnly();
      }
      else {System.exit(0);} // close game
   }

   // message when game is lost
   public void loseGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (cells[row][col].isMined == true) { // if clicked on mined cell
               if (cells[row][col].isFlagged == false) { // reveal all mined cells for those not flagged
                  cells[row][col].setBackground(new Color(233, 62, 62));
                  cells[row][col].setForeground(Color.BLACK);
                  cells[row][col].setFont(new Font("Serif", Font.BOLD, FONT_SIZE+5));
                  cells[row][col].setText("*");
               } else {
                  cells[row][col].setBackground(new Color(62, 233, 62)); // reveal all mined cells for those flagged
                  cells[row][col].setForeground(Color.BLACK);
                  cells[row][col].setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
                  cells[row][col].setText("X");
               }     
            }
         }
      }

      // allow player to restart the game
      String[] reply = {"New Game","Try Again","Quit"};
      int reset = JOptionPane.showOptionDialog(null, "Play again?", "Game Over! Try again!", JOptionPane.DEFAULT_OPTION, 0, null, reply, reply[0]);
      System.out.println("New Game? "+reset);
      if (reset == 0) {
         if (gameModes == 1) {
            init();
         }
         else if (gameModes == 0) {
            initEasy();
         }
         else if (gameModes == 2) {
            initHard();
         }
         else {initInsane();}
      }
      else if (reset == 1) {
         initResetOnly();
      }
      else {System.exit(0);}
   }

   // check if the win criteria is met by checking total number of revealed squares
   public int checkWin() { 
      int isRevealedCount = 0;
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (cells[row][col].isRevealed == true) {
               isRevealedCount++;
            }
         }
      }
      return isRevealedCount;
   }

   public int checkMines() { // debugging for mines count
      int isMinedCount = 0;
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (cells[row][col].isMined == true) {
               isMinedCount++;
            }
         }
      }
      return isMinedCount;
   }

   public int flagCount() { // debugging for flag count
      int isFlaggedCount = 0;
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (cells[row][col].isFlagged == true) {
               isFlaggedCount++;
            }
         }
      }
      return isFlaggedCount;
   }

   // [TODO 2] Define a Listener Inner Class
   private class CellMouseListener extends MouseAdapter {
      @Override
      public void mouseClicked(MouseEvent e) {         // Get the source object that fired the Event
         Cell sourceCell = (Cell)e.getSource();

         // For debugging
         System.out.println("You clicked on (" + sourceCell.row + "," + sourceCell.col + ")");

         // Left-click to reveal a cell; Right-click to plant/remove the flag.
         if (e.getButton() == MouseEvent.BUTTON1) {  // Left-button clicked
            if (sourceCell.isMined == true) { // when clicking on a mined cell, you lose
               if (sourceCell.isFlagged == false) { // ignore flagged cells
                  loseGame();
               }   
            }
            else {
               if (sourceCell.isFlagged == false) { // if cell is not flagged, it is clickable

                  sourceCell.isRevealed = true; // reveal clicked cell

                  if (getSurroundingMines(sourceCell) < 1) { // do not display 0 surrounding mines
                    
                     sourceCell.paint();
                     revealSurrounding(sourceCell); // use the revealSurrounding method to store surrounding cells

                     for (int i = 0; i < storeSurroundingCell.size(); i++) { // reveal empty cells recursively
                        if (storeSurroundingCell.get(i).isRevealed == false) { // ignore already revealed cells
                           Cell c = storeSurroundingCell.get(i);
                           if (c.isFlagged == false) { // ignore flagged cells
                              if (getSurroundingMines(c) < 1) { // if no surrounding mines
                                 c.isRevealed = true;
                                 c.paint();
                                 revealSurrounding(c); // repeat the loop again by using revealSurrounding method to populate the ArrayList
                              }
                              else { // if there are surrounding mines, do not use revealSurrounding method
                                 c.setFont(new Font("Serif", Font.BOLD, FONT_SIZE)); // display surrounding mines if there are surrounding mines
                                 c.setText(""+getSurroundingMines(c));
                                 c.isRevealed = true;
                                 c.paint();
                              }
                           }             
                        }
                     }

                     storeSurroundingCell.clear(); // clear the reveal array after every left mouse click

                  }
                  else { // if there are surrounding mines, do not use revealSurrounding method
                     sourceCell.setFont(new Font("Serif", Font.BOLD, FONT_SIZE)); // display surrounding mines if there are surrounding mines
                     sourceCell.setText(""+getSurroundingMines(sourceCell));
                     sourceCell.paint();
                  }

                  checkWin(); // check for win condition every time an unrevealed cell is clicked
                  checkMines();

                  // debuggers
                  System.out.println("Revealed Cell Count: "+checkWin());
                  System.out.println("Total mines Count: "+checkMines());

                  if (gameModes == 1) {
                     if (checkWin() == (ROWS*COLS - numberMines)) { // need 350 revealed cells to win normal mode
                        System.out.println("You Win!");
                        winGame();
                     }
                  }
                  else if (gameModes == 0) {
                     if (checkWin() == (ROWS*COLS - numberMines - EASY_MODE)) { // need 370 revealed cells to win easy mode
                        System.out.println("You Win!");
                        winGame();
                     }
                  }
                  else if (gameModes == 2) {
                     if (checkWin() == (ROWS*COLS - numberMines - HARD_MODE)) { // need 310 revealed cells to win hard mode
                        System.out.println("You Win!");
                        winGame();
                     }
                  }        
                  else {
                     if (checkWin() == (ROWS*COLS - numberMines - INSANE_MODE)) { // need 250 revealed cells to win insane mode
                        System.out.println("You Win!");
                        winGame();
                     }
                  }          
               }
            }
         }
         else if (e.getButton() == MouseEvent.BUTTON3) { // right-button clicked
            // [TODO 6] If the location is flagged, remove the flag
            // Otherwise, plant a flag.
            if (sourceCell.isRevealed == false) { // if cell is not revealed it is able to be flagged
               if (sourceCell.isFlagged == false) {
                  sourceCell.isFlagged = true;
                  sourceCell.paint();
                  flagCount();

                  System.out.println("Flag Count: "+flagCount());
               }
               else {
                  sourceCell.isFlagged = false; // toggle a flagged cell off
                  sourceCell.setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
                  sourceCell.setText("");
                  flagCount();

                  System.out.println("Flag Count: "+flagCount());
               }
            }
         }
         // [TODO 7] Check if the player has won, after revealing this cell     
      }

      public void mouseEntered(MouseEvent e) { // display colour when mouse hovering over a cell
         Cell sourceCell = (Cell)e.getSource();

         if (sourceCell.isRevealed == false) {
            sourceCell.setBackground(new Color(200, 230, 230));
         }
      }

      public void mouseExited(MouseEvent e) { // move the display colour when mouse hovers to another cell
         Cell sourceCell = (Cell)e.getSource();

         if (showMinesTF == false) {
            if (sourceCell.isRevealed == false) {
               sourceCell.setBackground(Cell.BG_NOT_REVEALED);
            }
         }
         else {
            if (sourceCell.isRevealed == false) {
               if (sourceCell.isMined == true) {
                  sourceCell.setBackground(Cell.FG_REVEALED);
               }
               else {
                  sourceCell.setBackground(Cell.BG_NOT_REVEALED);
               }
            }
         }   
      }
   }
}