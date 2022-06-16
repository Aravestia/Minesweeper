package minesweeper;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.Random;
import java.awt.*;        // Use AWT's Layout Manager
import java.awt.event.*;  // Use AWT's Event handlers
import javax.swing.*;     // Use Swing's Containers and Components
/**
 * Locations of Mines
 */
public class MineMap {
   // package access
   Random random = new Random();
   Random random2 = new Random();
   int numMines = 50;
   int duplicateCount = 0;
   boolean[][] isMined;
   int[][] duplicateTracker = new int[GameBoard.ROWS][GameBoard.COLS]; // duplicate prevention

   public int getNumMines() {
      return this.numMines;
   }

   public void setNumMines(int n) {
      this.numMines = n;
   }

   // Constructor
   public MineMap() {
      isMined = new boolean[GameBoard.ROWS][GameBoard.COLS];
   }

   // randomly generated mines
   public void newMineMap(int numMines) {
      // this.numMines = numMines;

      //resetter
      for (int i = 0; i < GameBoard.ROWS; i++) {
         for (int j = 0; j < GameBoard.COLS; j++) {
            isMined[i][j] = false;
         }
      }

      //random mine debugger
      /* for (int i = 0; i < numMines; i++) {
         int rnd1 = random.nextInt(GameBoard.ROWS), rnd2 = random2.nextInt(GameBoard.COLS);
         isMined[rnd1][rnd2] = true;
         duplicateTracker[rnd1][rnd2]++; // find duplicates
         // System.out.println("[" + rnd1 +"]["+ rnd2 +"]["+ i +"]["+duplicateTracker[rnd1][rnd2]+"]");
      } */

      int mineCount = mineCounter();
      System.out.println("There are currently "+mineCount+ " Mines."); // initial no. of mines should be 0

      //in case of duplicates, this will add additional mines up to numMines
      while (mineCount < numMines) {
         int rnd1 = random.nextInt(GameBoard.ROWS), rnd2 = random2.nextInt(GameBoard.COLS); // generate random x and y positions
         isMined[rnd1][rnd2] = true;
         mineCount = mineCounter(); // count no. of mines, if less than numMines it will continue adding, ignoring duplicates
      }
      System.out.println("There are currently "+mineCount+ " Mines."); // check no. of mines after preventing duplicates, should be equal to numMines
   }

   public int mineCounter() { // count total number of mines for debugging
      int isMinedCount = 0;
      for (int i = 0; i < GameBoard.ROWS; ++i) {
         for (int j = 0; j < GameBoard.COLS; ++j) {
            if (isMined[i][j] == true) {
               isMinedCount++;
            }
         }
      }
      return isMinedCount;
   }
}