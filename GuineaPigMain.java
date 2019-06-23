import java.util.Scanner;

public class GuineaPigMain {

static Scanner sc = new Scanner(System.in);
static int currentTime = 8;
static int FINAL_HOUR = 22;
static int TYPES_OF_ACTIVITIES = 4;

public static void waitForUserToPressEnter(){
    System.out.println("Nyomj ENTER-t");
    sc.nextLine();
}



    public static void main(String[] args) {
        GuineaPig guineaPig = new GuineaPig("Egon", 500D);
                
        while (currentTime <= FINAL_HOUR){
            
            System.out.println( currentTime++ + " óra . . .");
            
            int randomActivity = (int)(Math.random()*TYPES_OF_ACTIVITIES);

 if ( currentTime ==12)  guineaPig.biteOwner("SÁTÁNKA");           
 
            switch(randomActivity){
                case 0:
                    guineaPig.sleep();
                break;
                case 1:
                    guineaPig.takeFood();
                break;
                case 2:
                    guineaPig.defecate();
                break;

                case 3:
                    guineaPig.runAround();
                break;
                
            }
            
            waitForUserToPressEnter();
        }
        
    }
    
}


