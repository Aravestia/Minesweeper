package minesweeper;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.Random;
import java.awt.*;        // Use AWT's Layout Manager
import java.awt.event.*;  // Use AWT's Event handlers
import javax.swing.*;     // Use Swing's Containers and Components

/**
 * Customize (subclass) the JButton to include
 * row/column numbers and status of each cell.
 */

public class Cell extends JButton {
   // Name-constants for JButton's colors and fonts
   public static final Color BG_NOT_REVEALED = new Color(236, 245, 245);
   public static final Color FG_NOT_REVEALED = new Color(139, 0, 0); // mines
   public static final Color BG_REVEALED = Color.LIGHT_GRAY;
   public static final Color FG_REVEALED = new Color(167, 199, 231); // number of mines

   // All variables have package access
   int row, col;        // The row and column number of the cell
   boolean isRevealed;
   boolean isMined;
   boolean isFlagged;

   // Constructor
   public Cell(int row, int col) {
      super();   // JTextField
      this.row = row;
      this.col = col;
      // Set JButton's default display properties
      super.setBorder(BorderFactory.createEtchedBorder(0));
   }

   public void init(boolean isMined) {
      isRevealed = false;
      isFlagged = false;
      this.isMined = isMined;
      super.setEnabled(true);  // enable button
      super.setForeground(FG_NOT_REVEALED);
      super.setBackground(BG_NOT_REVEALED);
      super.setText("");       // display blank
      // debugger
   }

   // reveal and display all mines
   public void initShowMines(boolean isMined) {

      this.isMined = isMined;

      if (isMined == true) {
         super.setBackground(FG_REVEALED);
      }
   }

   // hide all mines
   public void initHideMines(boolean isMined) {

      this.isMined = isMined;

      if (isMined == true) {
         super.setBackground(BG_NOT_REVEALED);
      }
   }

   // Paint itself based on its status
   public void paint() {
      if (isRevealed == true && isFlagged == false) {
      	if (isMined == false) {
      		super.setBackground(BG_REVEALED);
            super.setForeground(FG_NOT_REVEALED);
      		super.setEnabled(false);
      	}
      	else {
      		super.setBackground(FG_NOT_REVEALED);
      		super.setEnabled(false);
      	}
      }
      else {
      	if (isFlagged == true) {
      		super.setFont(new Font("Serif", Font.BOLD, GameBoard.FONT_SIZE));
      		super.setText("X");
      	}
      }	
   }
}