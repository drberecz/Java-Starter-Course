/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bh.jsc04;

import java.util.List;

/**
 *
 * @author lborisz
 */
public class ChessBoardPrinter {
  
  private static void printABC(){
    System.out.println("    A   B   C   D   E   F   G   H");
  }
  
  
  static void printRemovedFig(int row, int num, List list){  
        for (int i =(row-num)*4; i < list.size(); i++) {
            if (i == ((row-num)*4)+4) break;
            System.out.print(list.get(i));
        }
  }
  
  
  static void printBoard(){

     // StringBuilder sbstr = new StringBuilder();
    System.out.println("  ---------------------------------      Levett Bábuk: ");
    for (int row = 1; row <= 8; row++) {
      System.out.print(row+" ");
      for (int column = 1; column <= 8; column++) {
        
      //  sbstr.append(Chess.board[row][column]);
          
        System.out.print(Chess.board[row][column] == ' ' ?  "| " + square(row+column) + " " :
                                     "| " + Chess.board[row][column] + " ");
        //\u2B1B, \u2588, \u2591
      }
      System.out.print("|       \u001B[47m");
      if ( row<=4) printRemovedFig(row, 1, Chess.figuresRemoved0);
      else         printRemovedFig(row, 5, Chess.figuresRemoved1);
      System.out.println("\u001B[0m");
      System.out.println("  ---------------------------------");
    }
    printABC();
    //  System.out.println(sbstr);
  }

  static char square(int i) {
    return i%2!=0? '\u2588' : '\u2591'; 
  }
  
}
