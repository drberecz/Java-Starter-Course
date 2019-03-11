package battleships;

import static battleships.Battleships.BOARD_DIM;
import static battleships.Battleships.godMode;
import static battleships.Battleships.names;
import java.util.Scanner;

public class Input {

    public static Scanner sc = new Scanner(System.in);

    
    public static void getPlyrName (){
        String str;
        boolean isValid;
        
        System.out.println("Mi a neved? (Max. 12 karakter)");
        do{
        str = sc.next();
        if ( str.length()>12 ){
            isValid = false;
            System.out.println("Hibás adatbevitel!");
        }
        else isValid = true;
        } while ( !isValid);
        
        names[1] = str;
    }

    
    
    public static int getDiffLevel(int MAX_DIFF_LEVEL, String[] namesAI) {
        int input;
        boolean isValid;
            
        System.out.println("Válassz nehézségi fokozatot (1-" + MAX_DIFF_LEVEL + "):");
        do {

            String tmp = (sc.next().toUpperCase());
            input = (tmp.matches(".*\\d+.*"))? Integer.parseInt(tmp): 0;
            if (tmp.equals("GODMODE")){
                godMode = true;
                System.out.println("GOD MODE AKTIVÁLVA !");
                input= 1;
            }

            if (!(input >= 1 && input <= MAX_DIFF_LEVEL)) {
                isValid = false;
                System.out.println("Hibás adatbevitel! Újra kérem (1-"+MAX_DIFF_LEVEL+"):");
            }
            else isValid = true;
        } while (!isValid);

        System.out.println("\n Az ellenfeled: " + input + ". szint: " + namesAI[input] );
        names[2] = namesAI[input];
        return input;
    }

    
    public static boolean validate(char[][] table, int y, int x) {

        if (!(y >= 1 & y <= BOARD_DIM) | !(x >= 1 & x <= BOARD_DIM)) {
            return false;
        }
        if (table[y][x] == 'X' | table[y][x] =='°') {
            return false;
        }
        return true;
    }

    
    public static boolean isSimulation (){
            
        System.out.println("\nÍrj be I betűt, ha csak szimulációt kérsz,");
        System.out.println("Ha nem (I) az választásod, egy játékos mód lesz");
        String input = sc.next().toUpperCase();
        if ( input.charAt(0) == 'I'){
            System.out.println("\nAkkor szimuláció lesz");
            return true;
        }              
        
        System.out.println("\nAkkor, egy játékos mód");
        return false;
    }
 
    
    public static int[] getValidMove(char[][] table, String name) {
        int[] move = new int[2];
        boolean isValid;
        String tmp;
        
        System.out.println(name + " következik.");

        do {
            System.out.println("Melyik oszlop (A-" + (char) ('@' + BOARD_DIM) + ") ?");
            tmp = sc.next().toUpperCase();
            move[1] = (tmp.charAt(0) - '@');
            System.out.println("Melyik sor?");
            tmp = sc.next();
            move[0] = (tmp.matches(".*\\d+.*"))? Integer.parseInt(tmp): 0;

            isValid = validate(table, move[0], move[1]);
            if (!isValid) {
                System.out.println("Hibás mezőszám vagy már lőttél oda: " + tmp+","+ move[1]);
            }
        } while (!isValid);

        return move;
    }

}
