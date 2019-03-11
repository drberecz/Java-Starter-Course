package battleships;

import static battleships.Battleships.BOARD_DIM;
import static battleships.Battleships.godMode;
import static battleships.Battleships.names;


public class Display{
    
    public static int mainCou = 0;    
    public static final String[] INFO = {
        "    JELMAGYARÁZAT:",
        "    # - saját hajó",
        "    X - találat",
        "    ° - mellé"
    //   "    ~ - süllyedt"

    };

    public static  String[] scores = {
    "TIBI BÁCSI  : 999999",
    "Dr. EVIL    : 016000",
    "KISGONOSZ   : 008790",
    "ZALALÖVŐ    : 002399",
    "MÁMA LÁMA   : 000001"
    };
    
public static final String ANSI_RESET = "\u001B[0m";
public static final String ANSI_BLACK = "\u001B[30m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE = "\u001B[35m";
public static final String ANSI_CYAN = "\u001B[36m";
public static final String ANSI_WHITE = "\u001B[37m";



public static final String BACKGROUND_PURPLE    = "\u001B[45m";
public static final String BACKGROUND_CYAN      = "\u001B[46m";
public static final String BACKGROUND_WHITE     = "\u001B[47m";
public static final String BACKGROUND_BLACK	= "\u001B[40m";
public static final String BACKGROUND_RED	= "\u001B[41m";
public static final String BACKGROUND_BLUE	= "\u001B[44m";   
 

    public static void drawLineHalf(char[][] board, int y, boolean hideShips) {
        String str = "";
        
        char first = (y >= 10) ? '1' : ' ';
        System.out.print(first);
        for (int x = 0; x <= BOARD_DIM; x++) {
            char tmp = board[y][x];
            tmp = (!godMode & hideShips && tmp == '#') ?  '.' : tmp;
            switch (tmp) {
                case '#' : str = BACKGROUND_PURPLE + "# "; break;
                case '.' : str = BACKGROUND_BLUE + ". "; break;
                case 'X' : str = BACKGROUND_RED + "X "; break;
                case '°' : str = ANSI_WHITE +BACKGROUND_CYAN + "° "; break;
                default: str = String.valueOf(tmp) + " ";
            }
            System.out.print(str);
            System.out.print(ANSI_RESET);
        }

    }

    public static void showBoard(char[][] board, char[][] boardOther) {


        int padding = (BOARD_DIM >= 5) ? BOARD_DIM: 0;
        if (BOARD_DIM>8) padding +=3;

        System.out.print("\n " + names[1]);
        for (int i = 0; i < (13 - names[1].length()) + padding; i++) {
            System.out.print(" ");
        }
        System.out.println(names[2] + "\n");

        for (int y = 0; y <= BOARD_DIM; y++) {

            drawLineHalf(board, y, false);
            System.out.print("  |  ");
            drawLineHalf(boardOther, y, true);

            if (y < INFO.length) {
                System.out.print(INFO[y]);
            }
            if (y==BOARD_DIM){
                mainCou++;
                System.out.print("    " + mainCou + ". KÖR");
            }
            System.out.println();

        }
    }

    
    public static void printDelayed(String str) {

        for (int i = 0; i < str.length(); i++) {
            System.out.print(str.charAt(i));
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

    }

public static void startScreen(){
    
    String[] s = new String[16];
    
s[0]= " **********   *******   *******    *******  ******** *******     *******  ";
s[1]= " ////**///   **/////** /**////**  /**////**/**///// /**////**   **/////** ";
s[2]= "    /**     **     //**/**   /**  /**   /**/**      /**    /** **     //**";    
s[3]= "    /**    /**      /**/*******   /******* /******* /**    /**/**      /**";
s[4]= "    /**    /**      /**/**///**   /**////  /**////  /**    /**/**      /**";
s[5]= "    /**    //**     ** /**  //**  /**      /**      /**    ** //**     ** ";
s[6]= "    /**     //*******  /**   //** /**      /********/*******   //*******  ";
s[7]= "    //       ///////   //     //  //       //////// ///////     ///////   ";
s[8]= "                                                                          ";
s[9]= "__________________________________________________________________________";
s[10]="                                                                          ";
s[11]="    *   *  ***    ***   ***   ***   ****   ***** | 1. ";
s[12]="    *   *   *    *     *     *   *  *   *  *     | 2. ";
s[13]="    *****   *  -- ***  *     *   *  ****   ***** | 3. ";
s[14]="    *   *   *        * *     *   *  *   *  *     | 4. ";
s[15]="    *   *  ***    ***   ***   ***   *   *  ***** | 5. ";


    for (int y = 11, i=0; y < s.length; y++, i++) {
        s[y] +=(scores[i]);
    }


    System.out.println(ANSI_WHITE);
    for (int i = 0; i < s.length; i++) {
        for (int j = 0; j < s[i].length(); j++) {
            if ( i==9){ System.out.print( BACKGROUND_RED);}
            else if ( j <= 32 & i<=9){    
            System.out.print( BACKGROUND_CYAN);
            }
            else
            System.out.print(BACKGROUND_BLUE);
            char tmp = (j<s[i].length()) ? s[i].charAt(j) : ' ';
            System.out.print(tmp);
        }
        System.out.print("\n");
    }
    System.out.println(ANSI_RESET);

}

 







    
    
}
