package battleships;

import static battleships.Battleships.BOARD_DIM;
import static battleships.Battleships.NUM_OF_SHIPS;
import java.util.Arrays;


public class InitBoard {
  
    public static void placeShips ( char[][] board){
        

        int count = 0;
        for (int y = 0; y <= BOARD_DIM; y++) {
            Arrays.fill ( board[y], '.' );
        }
        
        for (int i = 1; i <= BOARD_DIM; i++) {
            board[0][i] = (char)( i+'@' );   
            board[i][0] = (char)( i%10 + '0' ); 
        }

        

        
        while ( count< NUM_OF_SHIPS){
            int y = (int)( Math.random()*BOARD_DIM+1 );
            int x = (int)( Math.random()*BOARD_DIM+1 );        
            if ( board[y][x]!='#'){
                board[y][x] = '#';
                ++count;
        }
        
        }
        
        
    }
    
}














