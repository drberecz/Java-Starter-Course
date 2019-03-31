/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bh.jsc04;


import static bh.jsc04.Game.rMoveMarkerYX;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author lborisz
 */
public class Chess {

  static int[][] WB_KING_ORIGINAL_YX ={ { 1, 4 },{ 8, 4 }};
  static int[][] wbKingYX = { { 1, 4 },{ 8, 4 }};
  static boolean showErr   = true;
  static String  errorMsg  = "";  
  
  static final char WHITE_KING = '\u2654';
  static final char BLACK_KING = '\u265A';
  static final char WHITE_QUEEN = '\u2655';
  static final char BLACK_QUEEN = '\u265B';

  static final char WHITE_ROOK = '\u2656';
  static final char BLACK_ROOK = '\u265C';
  static final char WHITE_KNIGHT = '\u2658';
  static final char BLACK_KNIGHT = '\u265E';
  static final char WHITE_BISHOP = '\u2657';
  static final char BLACK_BISHOP = '\u265D';

  static final char WHITE_PAWN = '\u2659';
  static final char BLACK_PAWN = '\u265F';

  static char[][] board = new char[9][9];

  static final char[] WHITE_FIGURES = {WHITE_PAWN, WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING};
  static final char[] BLACK_FIGURES = {BLACK_PAWN, BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING};

  static char[] wfq = {WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN,};
  static char[] bfq = {BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN,};

  static char[] wrq = {' ', WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_KING, WHITE_QUEEN, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK,};
  static char[] brq = {' ', BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_KING, BLACK_QUEEN, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK,};

  static List <Character> figuresRemoved0 = new ArrayList<>();
  static List <Character> figuresRemoved1 = new ArrayList<>();  
  
  static void init() {
    
    for (int i = 0; i < 9; i++) {
        Arrays.fill (board[i], ' ');     
    }
    board[1] = wrq;
    board[2] = wfq;
    board[7] = bfq;
    board[8] = brq;
  }
  
  
  static void enumerateFigures (){
  //  WHITE_FIGURES = {WHITE_PAWN, WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING};
      figuresRemoved0.clear();
      figuresRemoved1.clear();
      
      final int[] FIGURE_NUMBERS = { 8, 2, 2, 2, 1, 1 };
      int[] whiteFigActual = new int[FIGURE_NUMBERS.length];
      int[] blackFigActual = new int[FIGURE_NUMBERS.length];

      for (int y = 1; y <= Game.board_dim; y++) {
        for (int x = 1; x <= Game.board_dim; x++) {
            char boardPiece = board[y][x];
            
            if (boardPiece==WHITE_KING){
                wbKingYX[0][0] = y; wbKingYX[0][1] = x; 
            }
            if (boardPiece==BLACK_KING){
                wbKingYX[1][0] = y; wbKingYX[1][1] = x; 
            }            
            int tmp = isValidFigure(0, boardPiece);
            if ( tmp>=0 ) ++whiteFigActual[tmp];
            tmp = isValidFigure(1, boardPiece);
            if ( tmp>=0 ) ++blackFigActual[tmp];
        }          
      }
//for ( int x: whiteFigActual){ System.out.print(".." + x);}
      
      for (int i = 0; i < FIGURE_NUMBERS.length; i++) {

          while ( whiteFigActual[i]<FIGURE_NUMBERS[i]){
            figuresRemoved0.add(WHITE_FIGURES[i]);  
            ++whiteFigActual[i];
          }
          while ( blackFigActual[i]<FIGURE_NUMBERS[i]){
            figuresRemoved1.add(BLACK_FIGURES[i]);  
            ++blackFigActual[i];
          }
      }

  }
  
  


  static boolean move(int player_flag, int from_row, int from_column, int to_row, int to_column) {

    int [][] castlingYX = {{-1, -1},{-1, -1}};
    char figure = board[from_row][from_column];
    char target = board[to_row][to_column];
    System.out.println("move: " + (player_flag+1) + ". játékos lépés kiértékelés");
    
    if (isValidFigure(player_flag, figure) ==-1){
        errorMsg = "Azon a mezőn nincs saját figurád";
        Game.errorMarkerYX[0] = from_row;
        Game.errorMarkerYX[1] = from_column;
        System.out.println("Azon a mezőn nincs saját figurád ");
      return false;
    }
  
    if ( figure==WHITE_KING && target==WHITE_ROOK && player_flag==0){
        castlingYX = castling(0, from_row, from_column, to_row, to_column);
        if (castlingYX[0][0]==-1){
            System.out.println("Nem sáncolhatsz");
            errorMsg = "Nem sáncolhatsz";
            return false;
        }
    }
    else if ( figure==BLACK_KING && target==BLACK_ROOK && player_flag==1){
        castlingYX = castling(1, from_row, from_column, to_row, to_column);
        if (castlingYX[0][0]==-1){
            System.out.println("Nem sáncolhatsz");
            errorMsg = "Nem sáncolhatsz";
            return false;
        }        
    }   
    else if (!checkRule(figure, from_row, from_column, to_row, to_column)){
            if(showErr) System.out.println( "helytelen koordináta");
                errorMsg = "Helytelen koordináta.";
        Game.errorMarkerYX[0] = to_row;
        Game.errorMarkerYX[1] = to_column;
        return false;
    }else if 
        ( isValidFigure(player_flag, target)>=0 | target==WHITE_KING | target==BLACK_KING){
        System.out.println("Ezt a bábut (" + target + ") nem ütheted");
        Game.errorMarkerYX[0] = to_row;
        Game.errorMarkerYX[1] = to_column;
        errorMsg = "Ezt a bábut (" + target + ") nem ütheted";
      return false;
    } 
        
    boolean isSafeMove = isSafeMove(player_flag, from_row, from_column, to_row, to_column);
    if (!isSafeMove && castlingYX[0][0]==-1){
        System.out.println("Oda nem léphetsz, sakkban lennél/maradnál.");
        Game.errorMarkerYX[0] = to_row;
        Game.errorMarkerYX[1] = to_column;
        return false;
    }
    
    if (castlingYX[0][0] ==-1) {
        
        board[from_row][from_column] = ' ';
        if ( target!=' ' ) {
            if (player_flag==0)
              Chess.figuresRemoved1.add(target);
            else
              Chess.figuresRemoved0.add(target);        
            System.out.println("\u2620\u2620\u2620 ! Leütötted: (" +target+")");
            errorMsg = "Leütötted: (" +target+") \u2620\u2620\u2620"; 
            rMoveMarkerYX[0] = to_row;    
            rMoveMarkerYX[1] = to_column;
        }else{
            System.out.println("Valid a lépés ");
            errorMsg = " Jo lépés \u2713";
            rMoveMarkerYX[0] = to_row;    
            rMoveMarkerYX[1] = to_column;
            board[to_row][to_column] = figure;  
        }
    }
    else{
        board[from_row][from_column] = ' ';
        board[to_row][to_column] = ' ';
        char king = (player_flag==0)? WHITE_KING : BLACK_KING;
        char rook = (player_flag==0)? WHITE_ROOK : BLACK_ROOK;
        board[ castlingYX[0][0] ][ castlingYX[0][1] ] = king;
        board[ castlingYX[1][0] ][ castlingYX[1][1] ] = rook;
        wbKingYX[player_flag][0] = castlingYX[0][0];
        wbKingYX[player_flag][1] = castlingYX[0][1];
        System.out.println("Rosa Sikerult");
        errorMsg = "Rosa Sikerult";
            rMoveMarkerYX[0] = wbKingYX[player_flag][0];    
            rMoveMarkerYX[1] = wbKingYX[player_flag][1];        
    }
    return true;
  }

  static int isValidFigure(int player_flag, char figure) {
    if (figure == ' ') {
      return -1;
    }
    int i = WHITE_FIGURES.length-1;
    if (player_flag == 0) {
      while (i >=0  && figure != WHITE_FIGURES[i]) {
        i--;
      }
    } else {
      while (i >=0  && figure != BLACK_FIGURES[i]) {
        i--;
      }
    }
    return i;
  }

  static boolean checkRule(char figure, int from_row, int from_column, int to_row, int to_column) {

      int vectorY = to_row-from_row;
      int vectorX = to_column-from_column;
      if (ruleOutIdle(vectorY, vectorX)){
          return false;
      }    
      
    if (figure == WHITE_KING || figure == BLACK_KING){
      return checkKingRule(vectorY, vectorX);
    }      
    if (figure == WHITE_PAWN){
      return checkPawnRule( 1,vectorY, vectorX, from_row, to_row, to_column);
    }
    if (figure == BLACK_PAWN){
      return checkPawnRule( -1,vectorY, vectorX, from_row, to_row, to_column);
    }
    if (figure == WHITE_KNIGHT || figure == BLACK_KNIGHT){
      return checkKnightRule(vectorY, vectorX);
    }
    if (figure == BLACK_BISHOP || figure == WHITE_BISHOP){
      return checkBishopRule(vectorY, vectorX, from_row, from_column, to_row, to_column);
    }
    if (figure == BLACK_ROOK || figure == WHITE_ROOK){
      return checkRookRule(vectorY, vectorX, from_row, from_column, to_row, to_column);
    }
    if (figure == BLACK_QUEEN || figure == WHITE_QUEEN){

        boolean isValidQueenMove = ( vectorY==0 | vectorX==0 ) ?
                checkRookRule(vectorY, vectorX, from_row, from_column, to_row, to_column) :
                checkBishopRule(vectorY, vectorX, from_row, from_column, to_row, to_column);
                
        return isValidQueenMove;
    }    
    
    return true;
  }

  
  
  
  private static boolean checkBishopRule(int vectorY, int vectorX, int y1, int x1, int y2, int x2) {
             
      int signY   = (vectorY>0) ? 1:-1;     // a teszteléshez kellenek
      int signX   = (vectorX>0) ? 1:-1;     // a while ciklusban léptet majd
      
      // átlós lépésnél a két vektor abszolút értéke egyezik, ha nem - false
      if ( Math.abs(vectorY) != Math.abs(vectorX)){
          return false;
      }
      // ha csak 1-et lép nem kell vizsgálni, van e blokkoló bábu- ?vectorY>1
      if (Math.abs(vectorY) ==1) return true;
      
      return isRouteFree ( y1, x1, y2, x2, signY, signX);
      
  }


  private static boolean checkRookRule(int vectorY,int vectorX,int y1, int x1, int y2, int x2) {
  
      if ( vectorY!=0 & vectorX!=0 ){
          return false;
      }
      if (Math.abs(vectorY)==1 | Math.abs(vectorX)==1) return true;
      
      int signY = (vectorY==0) ? 0 : vectorY/Math.abs(vectorY);
      int signX = (vectorX==0) ? 0 : vectorX/Math.abs(vectorX);
           
      return isRouteFree ( y1, x1, y2, x2, signY, signX);
  }

  
  private static boolean checkPawnRule(int dirY,int vectorY,int vectorX, int y1, int y2, int x2) {
       
      int vectorYabs = Math.abs(vectorY);   
      int signY = (vectorY ==0) ? 0 : vectorY/Math.abs(vectorY);    
  
      //kill switch for chechk4check mode
      if (!showErr & vectorYabs>1) return false; 
      
      if ( y1!=2  & y1!=7 & vectorYabs > 1){
          if(showErr) System.out.println("a Gyalog csak 1x léphet duplát"); return false;
      }    
      if ( signY!=dirY | vectorYabs>2 | Math.abs(vectorX)>1) {
          return false;
      }
      char target = board[y2][x2];      
      if (vectorX==0 &  target!=' '){
          if(showErr) System.out.println("Foglalt mező: ("+target+")");
          return false;
      }
      if( vectorX != 0 & target == ' '){
          if(showErr) System.out.println("A Gyalog üres mezőt nem üthet");
          return false;
      }  
      return true;
  } 

  
    private static boolean checkKnightRule(int vectorY,int vectorX){
        int[][] predefVectors = {
            { -1,-2 }, { -2,-1 }, { -2,1 },{ -1,2 },
            { 1,-2 }, { 2,-1 }, { 2,1 }, { 1,2 } 
        };

        int      ptr = vectorY<0 ? 0 : predefVectors.length>>1;
        int endIndex = vectorY<0 ? predefVectors.length>>1 : predefVectors.length;
        endIndex     = vectorX<0 ? endIndex-2  : endIndex;
        
        for (; ptr < endIndex; ptr++) {
            if ( vectorY==predefVectors[ptr][0] && vectorX==predefVectors[ptr][1] ){
                return true;
            }
        }

    return false;
    }
  
  
    private static boolean checkKingRule(int vectorY,int vectorX){

        if(Math.abs(vectorY)>1 | Math.abs(vectorX)>1){
            return false;
        }
        
        return true;
    }    
  
    private static boolean ruleOutIdle ( int vectorY, int vectorX){
      if ( vectorY ==0 & vectorX ==0) {     
          if(showErr) System.out.println("Debug: Hiba, helyben maradna a bábu"); return true;
      }

    return false;    
    }

    
    static boolean check4Check ( int plyr_flag, boolean showMsg){
        showErr = false;
        int opponent = (plyr_flag==0) ? 1 : 0;
        int kingY = wbKingYX[plyr_flag][0];
        int kingX = wbKingYX[plyr_flag][1];
        
      for (int y = 1; y <= Game.board_dim; y++) {
        for (int x = 1; x <= Game.board_dim; x++) {
            char boardPiece = board[y][x];  
            if ( boardPiece!=' ' && isValidFigure(opponent,boardPiece)!=-1 ){
                if ( checkRule(boardPiece, y, x, kingY, kingX)){
              System.out.println("CSAK sakk" +boardPiece + ".." +y + "--" +x +
                      "king:" + kingY +".." + kingX);
                  if (showMsg)System.out.println("SAKKBAN: "+ (plyr_flag+1) +". játékos" );
                  showErr = true;      
                  return true;
                }
            }
        
        }
      }
        showErr = true;      
        return false;
    }

    
static boolean isSafeMove ( int player_flag, int from_row, int from_column, int to_row, int to_column){
        
    char figure = board[from_row][from_column];
    char target = board[to_row][to_column];
    int kingYstored = wbKingYX[player_flag][0];
    int kingXstored = wbKingYX[player_flag][1];
    board[to_row][to_column] = figure;
    board[from_row][from_column] = ' ';
    if ( figure==WHITE_KING | figure==BLACK_KING){
        wbKingYX[player_flag][0] = to_row;
        wbKingYX[player_flag][1] = to_column;        
    }
    if (check4Check(player_flag, false)){
        board[to_row][to_column] = target;
        board[from_row][from_column] = figure;     
        wbKingYX[player_flag][0] = kingYstored;
        wbKingYX[player_flag][1] = kingXstored;   
        return false;
    }

return true;    
}
    

static boolean isRouteFree (int y1, int x1, int y2, int x2, int signY, int signX){
    
      while ( y1!=y2 | x1!=x2  ){
          y1+= signY; 
          x1+= signX;
          //System.out.println( y1 + "..RFCOORD." + x1);
          char piece = board[y1][x1];
          if (  piece!= ' ' &  (y1!=y2 | x1!=x2)) {
              if(showErr) System.out.println("debug: Útban van egy bábu: "+ piece);
              return false;
          }
      } 
  return true;
}

static int[][] castling (int plyr_flag, int y1, int x1, int y2, int x2){


    int[][] result = {{-1,-1 },{-1,-1 }};
    int vectorY = y2-y1;
    int vectorX = x2-x1;     
    int signY = (vectorY==0) ? 0 : vectorY/Math.abs(vectorY);
    int signX = (vectorX==0) ? 0 : vectorX/Math.abs(vectorX);
    int rookPos_X = (signX<0) ? 1 : 8; 
    int rookPos_Y = (plyr_flag==0) ? 1 : 8; 
    
    if (vectorY!=0) {
    //    System.out.println("vectorY nem nulla");
        return result;
    }
    if ( rookPos_Y !=y2 | rookPos_X != x2 ){
      //  System.out.println("a bastya mar lepett a kezdeti pozi-bol");
        return result;
    }
                
    if ( wbKingYX[plyr_flag][0]!= WB_KING_ORIGINAL_YX[plyr_flag][0] &&
         wbKingYX[plyr_flag][1]!= WB_KING_ORIGINAL_YX[plyr_flag][1]   ){
        return result;
    }

  //  System.out.println("YX1:" + y1 + ".." + x1 + "YX2:" + y2 + ".." + x2);
    if (!isRouteFree(y1, x1, y2, x2, signY, signX)){
        return result;
    }
    
    int targetKingX = x1+signX*3;
    int kingXptr = x1;
    int kingXstored = wbKingYX[plyr_flag][1];
    
    do{
        wbKingYX[plyr_flag][1] = kingXptr;
        if ( check4Check(plyr_flag, false) ){
            wbKingYX[plyr_flag][1] = kingXstored;
            System.out.println("debug: King sakkban az utvonalon");
            return result;
        }     
        kingXptr+=signX;
    }while ( kingXptr!= targetKingX );
    
    
    
    result[0][0] = y1;
    result[0][1] = (signX>0) ? x1+2 : x1-2;    
    result[1][0] = y2;
    result[1][1] = (signX>0) ? result[0][1]-1 : result[0][1]+1;
    
    return result;
}





}











