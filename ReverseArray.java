import java.util.Arrays;
import java.util.Collections;


public class ReverseArray {

//    static int[] testArr = { 2,3,4,5,6,7,8,9};
    static Integer[] testArr = { 2,3,4,5,6,7,8,9,4,3,2,1,0};
    
    
    static void reverseArray () {
        
        int pointerLeft = 0;
        int pointerRight = testArr.length-1;
     
        while (pointerLeft<pointerRight) {
            int tmpVal = testArr[pointerRight];
            testArr[pointerRight] = testArr[pointerLeft];
            testArr[pointerLeft] = tmpVal;
        pointerLeft++;
        pointerRight--;
        }
        
        
    }

    
    public static void main(String[] args) {
        
        System.out.println("Before: ");
     for ( Integer x : testArr){
         System.out.print( x + " ");
     }   
     reverseArray();
        System.out.println("\nAfter: ");
     for ( Integer x : testArr){
         System.out.print( x + " ");
     } 
