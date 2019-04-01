/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author mantis
 */
public class Game {

   static Scanner scanner = new Scanner(System.in);
    
  public static int player_flag = 0, // 0 white, 1 black
                    from_row = 0, // A, B, C... (1-8)
                    to_row = 0, // A, B, C... (1-8)
                    from_column = 0, // 1 - 8
                    to_column = 0; // 1 - 8


  
  
  
    static boolean menuYesNo (String message){
        String input = "";
        boolean validInput = false;
        do{
            System.out.println(message);
            input = scanner.nextLine();
            if (input != null){
                input = input.toUpperCase();
                validInput = (input.equals("I") | input.equals("N"));
            }
        }while (!validInput);
        if ( input.equals("I")) return true;
        return false;
    }  

  
  
  
  
static ArrayList<String> getFileList (){
    ArrayList<String> fileList = new ArrayList<>();
    try{
    ChessHttpConn DataConn = new ChessHttpConn();
    ArrayList list = DataConn.sendGet("", "?q=dirlist");
          for (int i = 0; i < list.size(); i++) {
              String  str = (String)list.get(i);
              System.out.println(str);
              str = str.replaceAll("\\s+","");
              String[] strParts = str.split(">");
              fileList.add (strParts[1]);

          }
      }catch( Exception e){
        System.out.println("Internet connection failed");
      }
    return fileList;
}
  
  
  
  
static void saveBoard (int plyr){

    System.out.println("Játékállás mentése.  MIN 6 - MAX 48 KARAKTER. Mi legyen a neve:");
    String fajl;
    boolean validInput = false;
    do{
        fajl = scanner.nextLine();
        if ( fajl!=null && fajl.length()<=48 && fajl.length()>=6 ){
            validInput = true;
        }
        if ( !validInput) System.out.println("HIBAS FAJLNEV, UJRA KEREM");
    }while(!validInput);
    fajl = fajl.replaceAll("[^-_.A-Za-z0-9]", "_");
    System.out.println(  "EZ lett a filename: " +fajl);
    try{
        StringBuilder sb = new StringBuilder();
        
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                char k = Chess.board[i][j];
                
                if (k==32) sb.append("%20");
                else sb.append ( (char)(k+'a'-9812));

            }
        }
        if (plyr==0) sb.append("0");
        else sb.append("1");
        
        
       String figu = "?q=writefile&fajlnev=" +fajl + "&content=" + sb.toString();
    ChessHttpConn DataConn = new ChessHttpConn();
    String msg = DataConn.sendGetFile(figu, true);
        System.out.println(msg);
        System.out.println(" JATEK: MENTVE!");
      }catch( Exception e){
        System.out.println("Internet connection failed");
      }
    
}
  
  
  

    static boolean menuLoadBoard (){
         
        int ptr =0;
        int num=-1;
 
        boolean answeredYes = menuYesNo("Akarsz játekállást betölteni? (I)gen  / (N)em");
        if (answeredYes){
            ArrayList<String> list = getFileList();
            if (list == null || list.size()<1) return false;
            System.out.println("\nVálassz számot . . . pl. [0] ");
            while( num==-1 ){
                
                String input2 =scanner.next();
                try{
                    int tmp =Integer.parseInt(input2);
                    if ( tmp>=0 && tmp<list.size() ) num = tmp;
                }catch(Exception e){}
                if ( num==-1)System.out.println("nem jo a szám");
            }

            try{
            ChessHttpConn DataConn = new ChessHttpConn();            
            String boardData = DataConn.sendGetFile(list.get(num), false);
                for (int y = 1; y <=8; y++) {
                    for (int x = 1; x <= 8; x++) {
                        //Chess.board[y][x] = boardData.charAt(ptr++);
                        char piece = boardData.charAt(ptr++);
                        if (piece>32 & piece<128){
                            piece =(char)( piece + 9812 - 'a' );
                            System.out.print( ((int)piece) + "..");
                        }
                        Chess.board[y][x] = piece;
                    }
                }
                System.out.println(boardData);
                player_flag = boardData.charAt(64) - '0';
              //  Chess.enumerateFigures(0);
            }catch( Exception e){
                System.out.println("Nem sikerült betölteni a file-t");
            }
        }
        return (ptr>0);
    }
  




  
  public static void play(){
    boolean exit = false;
    Scanner scanner = new Scanner(System.in);
    do {
      ChessBoardPrinter.printBoard();
        System.out.println("A "+(player_flag == 0 ? "világos" : "sötét")+" jön!");
        
      String inputLine = scanner.nextLine().trim();
      
      if ("save".equals(inputLine)){
          saveBoard(player_flag);
      }
      else if ("exit".equals(inputLine)){
        exit = true;
      } else {
        if (checkInputLine(inputLine)){
          System.out.println("FLAG: "+player_flag);
          if (Chess.move(player_flag, from_row, from_column, to_row, to_column)){
            player_flag = player_flag == 0 ? 1 : 0;
          } else {
            System.out.println("Hibás lépés, lépjen újra!!");
          }
        }
        System.out.println(from_row + " " + from_column + " " + to_row + " " + to_column);        
      }
    } while (!exit);
  }
  
  public static boolean checkInputLine(String inputLine){
    if (null == inputLine || "".equals(inputLine)){
      System.out.println("Nem megfelelő adatbevitel.");
      return false;
    }
    if (inputLine.length() != 5){
      System.out.println("Nem megfelelő adatbevitel.");
      return false;
    }
    char[] inputChars = inputLine.toCharArray();
//    if ((inputChars[0] < 'a' || inputChars[0] > 'f') || 
//        (inputChars[1] < '1' || inputChars[1] > '8') ||
//         inputChars[2] != ' ' ||
//        (inputChars[3] < 'a' || inputChars[3] > 'f') || 
//        (inputChars[4] < '1' || inputChars[4] > '8')){

if (    !((inputChars[0] >= 'a' && inputChars[0] <= 'h') && 
        (inputChars[1] >= '1' && inputChars[1] <= '8') &&
         inputChars[2] == ' ' && 
        (inputChars[3] >= 'a' && inputChars[3] <= 'h') && 
        (inputChars[4] >= '1' && inputChars[4] <= '8'))
   ){
      System.out.println("Nem megfelelő adatbevitel.");
      return false;
    } else {
      from_column = (int)inputChars[0] - 96;//b == 98. karakter az ascii táblában --> 98-96 == 2; AlphabeticAsciiPositionShiftToTable
      from_row = inputChars[1] - 48;//2 == 50. karakter az ascii táblában --> 50-48 == 2; NumericAsciiPositionShiftToTable
      
      to_column = (int)inputChars[3] - 96;
      to_row = inputChars[4] - 48;
    }
    return true;
  }
  
  public static void main(String[] args) {

      
      try{
    ChessHttpConn DataConn = new ChessHttpConn();
         List data = DataConn.sendGet("startscreenBRHchess.txt", "?q=startscreen");
          for (int i = 0; i < data.size(); i++) {
              System.out.println(data.get(i));
              TimeUnit.MILLISECONDS.sleep(100);
          } 
      }catch( Exception e){
        System.out.println("Internet connection failed");
      }

           System.out.println("BRH CHESS - ZOLI");
      
      if ( !menuLoadBoard() ) { Chess.init(); }
      

    play();
  }
  
}
