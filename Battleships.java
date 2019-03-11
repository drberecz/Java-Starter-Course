package battleships;


public class Battleships{

public static final int BOARD_DIM = 5;
public static final int PLYR_ONE = 1;
public static final int PLYR_TWO = 2;
public static final int GAME_IS_ON = 0;
public static final int NUM_OF_SHIPS = (int)(BOARD_DIM*BOARD_DIM*0.4); 
public static final int MAX_DIFF_LEVEL = 3;
public static final String[] NAMES_AI = { "notUsed", "MÁMA LÁMA",
                                         "KISGONOSZ", "TIBI BÁCSI"};
public static char[][] boardPlyr1 = new char[BOARD_DIM+1][BOARD_DIM+1];
public static char[][] boardPlyr2 = new char[BOARD_DIM+1][BOARD_DIM+1];    
public static String[] names      = new String[3];
public static int missFactorAI;
public static int misscountAI;
public static boolean godMode = false;



public static  boolean missOrHit (char[][] table,int y, int x, boolean haveToMiss){

        if (haveToMiss & table[y][x] == '.' ) {
            return true;
        }
        if (!haveToMiss & table[y][x] == '#' ) {
            return true;
        }
        return false;

}

public static int[] moveAI (char[][] board,int currPlayer) {
        int[] move = new int[2];
        boolean validTarget;

        misscountAI -= ((int)(Math.random()*3)>0) ? 1:0;        
        boolean haveToMiss = (misscountAI >0);
        if ( misscountAI <= 0) misscountAI = missFactorAI;

        
        System.out.print("\n" + names[currPlayer] );
        Display.printDelayed(" lövés\n");
        do{
            move[0] = (int)( Math.random()*BOARD_DIM+1 );
            move[1] = (int)( Math.random()*BOARD_DIM+1 );    
            validTarget = (haveToMiss)? missOrHit(board,move[0],move[1],true):
                                           missOrHit(board,move[0],move[1],false);
        }while ( !validTarget);        
            
            return move;
}


    public static void updateBoard (char[][] board,int y, int x) {
            
            switch (board[y][x]){
                
                case '#':
                    board[y][x] = 'X';
                    Display.printDelayed(" TALÁLT!\n");
                    break;
                case '.':
                    board[y][x] = '°';
                    Display.printDelayed("MELLÉ.\n");
                    break;
            }
    }

       public static void displayWinner(int result) {
           System.out.println("\nVége a játéknak");
           switch (result) {
            case PLYR_ONE:
                System.out.println( names[PLYR_ONE] + " győzött.");
                break;
            case PLYR_TWO:
                System.out.println( names[PLYR_TWO] + " győzött.");
                break;
        }
    }
    
    
    public static boolean allShipsLost ( char[][] board){
        
        for (int i = 0; i <= BOARD_DIM; i++) {
            for (int j = 0; j <= BOARD_DIM; j++) {
                if ( board[i][j] == '#') return false;
            }
        }   
    return true;
    }
       
       
    

    public static void main(String[] args) {
        int gameState = 0;
        int[] move;
        boolean isSimulation;
        
        Display.startScreen();
        InitBoard.placeShips(boardPlyr1);
        InitBoard.placeShips(boardPlyr2);
        Input.getPlyrName();
        isSimulation = Input.isSimulation();
        missFactorAI = 2+MAX_DIFF_LEVEL - Input.getDiffLevel(MAX_DIFF_LEVEL, NAMES_AI);
        do{
            Display.showBoard(boardPlyr1, boardPlyr2);
            
            if ( !isSimulation) move = Input.getValidMove(boardPlyr2, names[PLYR_ONE]);
            else move = moveAI(boardPlyr2, PLYR_ONE);
            updateBoard(boardPlyr2, move[0], move[1]);
            gameState = (allShipsLost (boardPlyr2))? PLYR_ONE : 0;
            
            if ( gameState == GAME_IS_ON){
                move = moveAI(boardPlyr1, PLYR_TWO);
                updateBoard(boardPlyr1, move[0], move[1]);
                gameState = (allShipsLost (boardPlyr1))? PLYR_TWO : 0;
            }            
        }while ( gameState == GAME_IS_ON);
        displayWinner(gameState);
        godMode = true;
        Display.showBoard(boardPlyr1, boardPlyr2);        
    }

    
}
