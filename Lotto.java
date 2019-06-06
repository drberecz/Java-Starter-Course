import java.util.Scanner;

public class Lotto {

    static Scanner scanner = new Scanner(System.in);
    static final String ANSI_GREEN_BG = "\u001B[42m";
    static final String ANSI_RED_BG = "\u001B[41m";
    static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {


        
        
        final int NUMS_SET_SIZE = inputHandler("Which GAME would you like play?\n"
                + "(5) --- 5 out of 90\n"
                + "(6) --- 6 out of 45\n"
                + "(7) --- 7 out of 35\n"
                + "\nPlease enter value 5,6 or 7.\n ", 5, 7);
        final int NUMS_RANGE_UPPER = (NUMS_SET_SIZE == 5) ? 90 : ((NUMS_SET_SIZE == 6) ? 45 : 35);
        final int DISPLAY_COLS = (NUMS_SET_SIZE == 5) ? 10 : ((NUMS_SET_SIZE == 6) ? 7 : 5);
        final int TOTAL_OF_SHEETS = inputHandler("How many sheets would you like to fill out?\n"
                + "Please enter a value between 1...10", 1, 10);
        boolean[][] bitSet = new boolean[TOTAL_OF_SHEETS][NUMS_RANGE_UPPER + 1];

        
        for (int sheetIndex = 0; sheetIndex < TOTAL_OF_SHEETS; sheetIndex++) {

            int count = 0;
            int errorMarker = -1;
            while (count < NUMS_SET_SIZE) {

                printSheet(bitSet, sheetIndex, NUMS_RANGE_UPPER, DISPLAY_COLS, errorMarker);
                errorMarker = -1;
                String message = (sheetIndex + 1) + ". szelveny " + (count + 1) + ". szám: ";
                int inputNumber = inputHandler(message, 1, NUMS_RANGE_UPPER);

                if (bitSet[sheetIndex][inputNumber]) {
                    System.out.println("Sheet already contains number: " + inputNumber + " \n");
                    errorMarker = inputNumber;
                } else {
                    bitSet[sheetIndex][inputNumber] = true;
                    count++;
                }

            }

            printSheet(bitSet, sheetIndex, NUMS_RANGE_UPPER, DISPLAY_COLS, errorMarker);

        }

       

        for (int j = 0; j < TOTAL_OF_SHEETS; j++) {
            System.out.print("\n" + " Lotto sheet no."+(j+1) + ": ");
            int dotCount = 0;
            for (int i = 1; i <= NUMS_RANGE_UPPER; i++) {
                if (bitSet[j][i]) {
                    System.out.print(i + ((++dotCount < NUMS_SET_SIZE) ? ", " : ".\n"));

                }
            }
        }

    }

    
    static void printSheet(boolean[][] bitSet, int sheetNum, int NUMS_RANGE_UPPER, int DISPLAY_COLS, int errorMarker) {
 
        System.out.println("*****SHEET n. " + (sheetNum+1) + "*****");
        for (int i = 1; i <= NUMS_RANGE_UPPER; i++) {

                if ((i-1)%DISPLAY_COLS==0) System.out.println();
                String highlighter = (i==errorMarker) ? ANSI_RED_BG : ANSI_GREEN_BG;
                if (bitSet[sheetNum][i]) {
                    System.out.print(highlighter);
                    System.out.print((i < 10) ? " " + i : i);
                    System.out.print(ANSI_RESET + " ");
                } else {
                    System.out.print((i < 10) ? " " + i : i);
                    System.out.print(" ");
                }

        }
            System.out.println();
    }

    
    static int inputHandler(String msg, final int LOWER_LIMIT, final int UPPER_LIMIT) {

        boolean isValidInput = false;
        int inputNumber= Integer.MIN_VALUE; 
        
        while (!isValidInput) {
            System.out.println(msg);
           
            String inputLine = scanner.nextLine();           
            try{
            inputNumber = Integer.parseInt(inputLine);
            }catch(NumberFormatException e){
                System.out.println("error: Invalid input.\n");
                inputNumber= Integer.MIN_VALUE; 
            }


                if (inputNumber >= LOWER_LIMIT && inputNumber <= UPPER_LIMIT) {
                    isValidInput = true;
                } else if (inputNumber != Integer.MIN_VALUE) {
                    System.out.println("error: Entry out of range.");
                }

        }
        
        return inputNumber;
    }

}
