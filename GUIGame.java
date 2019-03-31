/*
koordinata geometria wikipedia
* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bh.jsc04;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;


public class Game extends JFrame {
  
    static boolean doneSetup = false;
    static String prevBoard = "";
    static JFrame frame; 
    static JTextArea jTextArea;
    static JTextArea inputField;
    static JLabel jErrorDispl;
    static JButton doneEditing;
    static boolean doneEdit = true;
    static int[] errorMarkerYX = {-1,-1};
    static int[] rMoveMarkerYX = {-1,-1};
    
    Game(){

    }
    
    
    
    
    
   static Scanner scanner = new Scanner(System.in);
    
  static int player_flag = 0, // 0 white, 1 black
                    board_dim   = 8,
                    from_row = 0, // A, B, C... (1-8)
                    to_row = 0, // A, B, C... (1-8)
                    from_column = 0, // 1 - 8
                    to_column = 0; // 1 - 8
  
  //'\u2591'
 
  
  
  
  static void showRemovedFigs (){
      System.out.println("benne a removedwho ban");
  jTextArea.setText("NANANANANAN");
      
      StringBuilder sb = new StringBuilder();
    sb.append( "Levett bábuk:\n" );
    Chess.figuresRemoved0.forEach((ch) -> {
        sb.append(ch);
        });
    sb.append("\n");
    Chess.figuresRemoved1.forEach((ch) -> {
        sb.append(ch);
        });
  
  jTextArea.setText(sb.toString());
  }
  
  
  
  static void harvestTextField (){
      String str = jTextArea.getText();
      String[] strArr = str.split("\n");
      for (int y = 1; y <=8; y++) {
          int ptr = 1;
          for (int x= 1; x<=8; x++) {
              char piece = strArr[y-1].charAt(ptr);
              Chess.board[y][x] = (piece>=9812) ? piece : ' '; 
              ptr+=2;
          }
      }
      
      
  }
  
  
  static void editBoardInit () throws BadLocationException, InterruptedException{

          doneEdit = false;
          setTextField("Pályaszerkesztő");
          editBoard();
          doneEditing.setText("<html>K<br>É<br>S<br>Z<br>?<br></html>");
          do{
          System.out.println("Fut a szerkesztő . . .    Ha kész, nyomj ENTER-t");
          System.out.println("Ellenőrzéshez kattints a (KÉSZ?) gombra");
          scanner.nextLine();
          }while (!doneEdit);
          System.out.println("KILÉPETTT a szerkesztőből");
          doneEditing.setText("<html>|<br>|<br>|<br>|<br>|<br></html>");
  }      
  
  
  static boolean editBoard () throws BadLocationException, InterruptedException{
 
      
        boolean hasMarks = false;
        StringBuilder sb = new StringBuilder();  
        String str = jTextArea.getText();
        str = str.replaceAll("\u2588", "\u2592");
        String[] strArr = str.split("\n");
        String line;
        for (int i = 0; i < strArr.length; i++) {
            line = strArr[i];
            int lineLen = line.length();

            if ( i<8 &&  lineLen!=18 ) hasMarks=true;
            
            if ( i<8 && lineLen == 20 && line.contains("<="))
                line = line.substring(0, lineLen-2) + "\n";
            else if ( i<8 &&  lineLen!=18  && !line.contains("<=")){
                line += "<=\n";
            }
            else
                line += "\n";

            sb.append(line);
        }

      
      String res = sb.toString();
      int caretPos = jTextArea.getCaretPosition();
//      int prevLength = jTextArea.getText().length();
//      int newLength  = res.length();
      //if (hasMarks) caretPos += 2;
//      if ( caretPos<0) caretPos = 0;
      jTextArea.setText(res);
      //TimeUnit.MILLISECONDS.sleep(300);
      highLighter(res);
      jTextArea.setCaretPosition(caretPos);

     if (!hasMarks) hasMarks = (strArr.length < 8); 
     return hasMarks; 
  }
  
  
  
  
  
  static void highLighter (String str) throws BadLocationException{
      
      for (int i = 0; i < str.length(); i++) {
          if ( str.charAt(i) =='X')
              jTextArea.getHighlighter().addHighlight(i, i+1,
              new DefaultHighlighter.DefaultHighlightPainter(Color.RED));          
          else if ( str.charAt(i) =='\u2713')
              jTextArea.getHighlighter().addHighlight(i, i+1,
              new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN));
          else if ( str.charAt(i) =='<')
              jTextArea.getHighlighter().addHighlight(i, i+2,
              new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE));              
      }

      errorMarkerYX[0] = -1; errorMarkerYX[1] = -1;
      rMoveMarkerYX[0] = -1; rMoveMarkerYX[1] = -1;
      }
  
  //u2580
  static void setTextField (String msg) throws BadLocationException{
      StringBuilder sb = new StringBuilder();
      
      
      for (int y = 1; y <= 8; y++) {

          sb.append ((char)(y+48));
          for (int x = 1; x <= 8; x++) {
            char piece = Chess.board[y][x];
            char block = ((x+y)%2 ==0) ? ' ' : '\u2588';
            char filler = ((x+y)%2 ==0)? ' ' : '\u2592';
            if (y==errorMarkerYX[0] & x==errorMarkerYX[1]){
                block ='X';   filler = 'X';
            }
            else if(y==rMoveMarkerYX[0] & x==rMoveMarkerYX[1]) {filler = '\u2713';}
            
            if ( piece == ' '){
                sb.append(block); sb.append(block);
            }
            else {sb.append(piece);  sb.append(filler);
            }
      }
        sb.append("|\n");

      }
      sb.append(" A B C D E F G H \n");
      
      if (!doneEdit){
      sb.append("Bábu:");
      for ( char piece : Chess.WHITE_FIGURES){
          sb.append(piece);
      }
      for ( char piece : Chess.BLACK_FIGURES){
          sb.append(piece);
      }
      }
      String str = sb.toString();
//      String[] strArr = str.split("\n");
//      for ( String s : strArr){
//      System.out.println(s);
//      }
      jTextArea.setText(str);
      highLighter(sb.toString());
      jErrorDispl.setText( msg + Chess.errorMsg);
      
  }
  
  
  
  static void play() throws BadLocationException, InterruptedException{
    boolean exit = false;

    do {
      ChessBoardPrinter.printBoard();
      String msg = (Chess.check4Check(player_flag, true) ) ? "SAKK!" : "";
      setTextField(msg);
      
      String currPlyr = (player_flag==0) ? "Világos" : "Sötét"; 
        System.out.println("Mit lépsz - " + currPlyr+ " ?: ");
      String inputLine = scanner.nextLine().trim();

      if ("edit".equals(inputLine)){
          System.out.println("EDITOR STARTED");
        editBoardInit();          
      }    
      else if ("save".equals(inputLine)){
          saveBoard(player_flag);
      }
      else if ("exit".equals(inputLine)){
        exit = true;
      }
      else {
        boolean validInput = checkInputLine(inputLine, 5);
        if (!validInput) setTextField("");
        if (validInput){
          if (Chess.move(player_flag, from_row, from_column, to_row, to_column)){
            player_flag = player_flag == 0 ? 1 : 0;
          } 
        }
         
      }
    } while (!exit);
  }
  
  static boolean checkInputLine(String inputLine, int correctLength ){
    if (null == inputLine || "".equals(inputLine)){
      System.out.println("Nem megfelelő adatbevitel.");
      Chess.errorMsg = "Nem megfelelő adatbevitel."; 
      return false;
    } 
    if (inputLine.length() != correctLength ){
      System.out.println("Nem megfelelő adatbevitel.");
      Chess.errorMsg = "Nem megfelelő adatbevitel."; 
      return false;
    }
    char[] inputChars = inputLine.toCharArray();


if (    !((inputChars[0] >= 'a' && inputChars[0] <= 'h') && 
        (inputChars[1] >= '1' && inputChars[1] <= '8') &&
         inputChars[2] == ' ' && 
        (inputChars[3] >= 'a' && inputChars[3] <= 'h') && 
        (inputChars[4] >= '1' && inputChars[4] <= '8'))
   ){
      System.out.println("Nem megfelelő adatbevitel.");
      Chess.errorMsg = "Nem megfelelő adatbevitel."; 
      return false;
    } else {
      from_column = (int)inputChars[0] - 96;
      from_row = inputChars[1] - 48;
      
      to_column = (int)inputChars[3] - 96;
      to_row = inputChars[4] - 48;
    }
    return true;
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
                for (int y = 1; y <=board_dim; y++) {
                    for (int x = 1; x <= board_dim; x++) {
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
                Chess.enumerateFigures();
            }catch( Exception e){
                System.out.println("Nem sikerült betölteni a file-t");
            }
        }
        return (ptr>0);
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
//        switch (k){
//            case ' ': sb.append("%20"); break;
//            case Chess.WHITE_KING: sb.append( "\u2654" ); break;
//            case Chess.BLACK_KING :sb.append( "\u265A"); break;
//            case Chess.WHITE_QUEEN :sb.append( "\u2655"); break;
//            case Chess.BLACK_QUEEN :sb.append( "\u265B"); break;
//            case Chess.WHITE_ROOK :sb.append( "\u2656"); break;
//            case Chess.BLACK_ROOK :sb.append( "\u265C"); break;
//            case Chess.WHITE_KNIGHT :sb.append( "\u2658"); break;
//            case Chess.BLACK_KNIGHT :sb.append( "\u265E"); break;
//            case Chess.WHITE_BISHOP :sb.append( "\u2657"); break;
//            case Chess.BLACK_BISHOP :sb.append( "\u265D"); break;
//            case Chess.WHITE_PAWN :sb.append( "\u2659"); break;
//            case Chess.BLACK_PAWN :sb.append( "\u265F"); break;
//        
//            }
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
    
    
    
    
    
    

  public static void main(String[] args) throws Exception{
  
    frame = new JFrame("BRH CHESS"); 
        //Game textOut = new Game();
  
        
    GraphicsEnvironment.getLocalGraphicsEnvironment();

    Font font = new Font("DejaVu Sans Mono", Font.PLAIN, 40);
    Font font2 = new Font("DejaVu Sans Mono", Font.PLAIN, 18);        
        jTextArea = new JTextArea(32, 30);   
        jTextArea.setFont(font);

        jErrorDispl = new JLabel("Hibauzenetek");
        jErrorDispl.setFont(font2);
        doneEditing = new JButton("<html>|<br>|<br>|<br>|<br>|<br></html>");
        doneEditing.setFont(font2);
        JPanel p = new JPanel(); 


     
              
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {System.exit(0);}
    });
        //p.setLayout(new GridBagLayout());
       //p.setLayout(new FlowLayout());
        p.setLayout(new BorderLayout());
        p.add(jTextArea, BorderLayout.CENTER); 
        p.add(doneEditing, BorderLayout.EAST); 
        p.add(jErrorDispl, BorderLayout.SOUTH);


jTextArea.addKeyListener(new KeyListener(){
    
    @Override
    public void keyPressed(KeyEvent e){

            String str = jTextArea.getText();
            if ( !doneEdit & !str.equals(prevBoard)){
                prevBoard = str;
                try {
                    editBoard();
                } catch (BadLocationException | InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    } 
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    });
        

        doneEditing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("btn pressed");
                boolean hasMarks = true;
                
                if ( !doneEdit){
                try {
                    hasMarks = editBoard(); hasMarks = editBoard();
                } catch (BadLocationException | InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
                if ( !hasMarks) {
                    doneEdit = true;
                    harvestTextField();
                    Chess.enumerateFigures();
                    jTextArea.setText("KÉSZ.\nVisszatérhetsz\na konzolhoz");
                }
                else
                    Chess.errorMsg = "PROBLEM";
            
                }
                else if ( doneSetup ){
                    try {
                        showRemovedFigs();
                        try {
                            TimeUnit.MILLISECONDS.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        setTextField("");
                    } catch (BadLocationException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                    
                
            }
        });
        
        
        frame.add(p); 
        frame.setSize(570, 530); 
  
        frame.show(); 
        //frame.setVisible(true);   
        jTextArea.setText("PARANCSOK:\nEDIT\nSAVE\nEXIT");

        
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

      if ( !menuLoadBoard() ) { Chess.init(); }
 
      String plyrToStart = ( player_flag==0) ? "Világos" : "Sötét";
      System.out.println(" - ha ki akarsz majd lépni, írj be \"exit\" parancsot");
      System.out.println(" - ha ki akarod menteni a játékot, írj be \"save\" parancsot");
      System.out.println(" - a pályaszerkesztőhöz, írj be \"edit\" parancsot");
      System.out.println( plyrToStart + " KEZD ... NYOMJ Betu +ENTER-T A FOLYTATASHOZ\n\n");
      scanner.nextLine();
    
      doneSetup = true;
    play();
  }
  
}


