package bh10zh01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Runners {

    static Scanner scanner = new Scanner(System.in);

    static final int OPTION_LOWEST_VAL = 1;
    static final int OPTION_HIGHEST_VAL = 3;
    static final String FULL_DISTANCE = "teljes táv: 21km";
    static final String HALF_DISTANCE = "fél táv: 10.5km";
    static final String ONE_THIRD_DISTANCE = "harmad táv: 7km";

    static List<String[]> participants = new ArrayList<>();
    static List<String> waitingListHalf = new ArrayList<>();
    static List<String> waitingListThird = new ArrayList<>();
    static Set<String> containerOfNames = new TreeSet<>();
    static int maxParticipants;

    public static void main(String[] args) {

        final int maxPossibleparticipants = 3000;
        maxParticipants = inputHandler("Mennyi a max. resztvevo szam?\n"
                + "Irj be  1 es " + maxPossibleparticipants + " kozotti erteket", 1, maxPossibleparticipants);

        int currentNumOfParticipants = 1;


        while (currentNumOfParticipants <= maxParticipants) {

            String currentName = inputName(currentNumOfParticipants + ". nevező . . .Add meg a neved:");
            int optionChosen = inputHandler(currentName + "! melyik távra jelentkeznél?\n"
                    + " (1) teljes táv 21 km\n"
                    + " (2) fél táv    10.5 km\n"
                    + " (3) harmad táv  7 km\n", OPTION_LOWEST_VAL, OPTION_HIGHEST_VAL);

            switch (optionChosen) {
                case 1:
                    String[] tmpStr = {currentName,  FULL_DISTANCE};
                    participants.add(tmpStr);
                    System.out.println("résztvevő vagy teljes távon!");
                    break;
                case 2:

                    if (!waitingListHalf.isEmpty()) {
                        String firstPersonOnList = waitingListHalf.remove(0);
                        String[] tmpStrHalfP2 = {currentName, HALF_DISTANCE};
                        String[] tmpStrHalfP1 = {firstPersonOnList,  HALF_DISTANCE};

                        participants.add(tmpStrHalfP1);
                        participants.add(tmpStrHalfP2);
                        System.out.println("résztvevő vagy FÉL távon! a párod :" + tmpStrHalfP1[0]);
                    } else {
                        System.out.println("Kis türelmed kérjük, Jelezzük, ha teljes a csapatod");
                        waitingListHalf.add(currentName);
                    }

                    break;

                case 3:
//&& participants.size() < maxParticipants - 2
                    if (waitingListThird.size() >= 2) {
                        String firstPersonOnList = waitingListThird.remove(0);
                        String secondPersonOnList = waitingListThird.remove(0);
                        String[] tmpStrThirdP3
                                = {currentName,  ONE_THIRD_DISTANCE};
                        String[] tmpStrThirdP2
                                = {secondPersonOnList, ONE_THIRD_DISTANCE};
                        String[] tmpStrThirdP1
                                = {firstPersonOnList,  ONE_THIRD_DISTANCE};

                        participants.add(tmpStrThirdP1);
                        participants.add(tmpStrThirdP2);
                        participants.add(tmpStrThirdP3);
                        System.out.println("résztvevő vagy HARMAD távon! a csapat : ");
                        System.out.println(firstPersonOnList + "-" + secondPersonOnList + "-" + currentName);

                    } else {
                        System.out.println("Kis türelmed kérjük, Jelezzük, ha teljes a csapatod");
                        waitingListThird.add(currentName);
                    }

                    break;

                default:
                    break;
            }

            currentNumOfParticipants++;

        }

        System.out.println();
        printChartOfPersons();
    }

    
    
    static String inputName(String message) {

        boolean isValidinput = false;
        String name = "";
        
        System.out.println(message);
        while (!isValidinput) {
            name = scanner.nextLine().trim();
            if (!name.isEmpty() && name.length()<25 && !checkIfNameOccupied(name)) {
                isValidinput = true;
                addNametoContainer(name);
            } else {
                if (checkIfNameOccupied(name)) System.out.println("A név már foglalt!");
                System.out.println("Hiba, újra kérem a nevet:");
            }

        }

        return name;
    }

    
    
    static int inputHandler(String msg, final int LOWER_LIMIT, final int UPPER_LIMIT) {

        boolean isValidInput = false;
        int inputNumber = Integer.MIN_VALUE;

        while (!isValidInput) {
            System.out.println(msg);

            String inputLine = scanner.nextLine();
            try {
                inputNumber = Integer.parseInt(inputLine);
            } catch (NumberFormatException e) {
                System.out.println("error: Invalid input.\n");
                inputNumber = Integer.MIN_VALUE;
            }

            if (inputNumber >= LOWER_LIMIT && inputNumber <= UPPER_LIMIT) {
                isValidInput = true;
            } else if (inputNumber != Integer.MIN_VALUE) {
                System.out.println("error: Entry out of range.");
            }

        }

        return inputNumber;
    }

    static void printChartOfPersons() {

        System.out.println(containerOfNames);
        
        System.out.println("EZEK AZ EMBEREK FUTNAK:");
        if (participants.isEmpty()) {
            System.out.println("SENKI!");
        } else {
            int numPlate = 1;
            for (String[] nameAndDistance : participants) {
                System.out.print("rajtszám: " + convertNumtoString(numPlate++) + " Név: " + nameAndDistance[0] + " --- " + nameAndDistance[1] + "\n");
            }
        }
        System.out.println();

        System.out.println("FELTAV VAROLISTA:");
        if (waitingListHalf.isEmpty()) {
            System.out.println("SENKI!");
        } else {
            for (String name : waitingListHalf) {
                System.out.print("Nev: " + name + " \n");
            }
        }

        System.out.println("HARMAD TAV VAROLISTA:");
        if (waitingListThird.isEmpty()) {
            System.out.println("SENKI!");
        } else {
            for (String name : waitingListThird) {
                System.out.print("Nev: " + name + " \n");
            }
        }

    }

    static String convertNumtoString(int num) {

        String strVal = Integer.toString(num);
        int lengthOfDigits = strVal.length();

        String leadingZeros = "";
        switch (lengthOfDigits) {

            case 1:
                leadingZeros = "00";
                break;
            case 2:
                leadingZeros = "0";
                break;
            default:
                break;
        }

        return leadingZeros + strVal;
    }

   
    static boolean checkIfNameOccupied (String name){
        return (containerOfNames.contains(name));
    }
    
    
    static void addNametoContainer (String name){
      
        containerOfNames.add(name);
    }
    
    
    
    
}





