public class AverageSpread {

    
    static double calculateSpread(double[] arr, double average) {
        double spreadOfArr;
        double sum = 0D;
        if (arr.length == 0) {
            System.out.println("PROBLEM!!! empty data field");
        }

        for (int i = 0; i < arr.length; i++) {
            double val = arr[i];
            sum += Math.abs(average - val);
        }

        spreadOfArr = sum / (double)arr.length;
        return spreadOfArr;
    }

    
    
    static double calculateAverage(double[] arr) {
        double averageOfArr;
        double sum = 0D;
        if (arr.length == 0) {
            System.out.println("PROBLEM!!! empty data field");
        }
                
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        averageOfArr = sum / (double)arr.length;
        return averageOfArr;
    }
    
    

    public static void main(String[] args) {

        double[] arr = {1.0, 1.0, 3.0, 3.0, 5.0, 5.0};

        System.out.println("Elements of Array:");
        for (Double x : arr){
            System.out.print(x + "  ");
        }
        
        
        double average = calculateAverage(arr);
        System.out.println("\n\naverage: " + average);

        double spread = calculateSpread(arr, average);
        System.out.println("mean spread: " + spread);        
    }

}

