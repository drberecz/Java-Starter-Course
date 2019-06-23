enum EnumSpec {
    HUMAN, GUINEA_PIG
}


class Mammal{
    
    protected String species;
    protected String name;
    protected int numOfLegs;
    protected double weight;

    public Mammal(EnumSpec species, String name, int numOfLegs, double weight) {
        this.species = species.toString();
        this.name = name;
        this.numOfLegs = (species == EnumSpec.GUINEA_PIG) ? 4 : 2;
        this.weight = weight;
    }
    
    public String getName (){
        return name;
    }
    
    public double getWeight (){
        return weight;
    }
    
}


class GuineaPig extends Mammal {

    double weightChangePercent = 5;
    double factorForExerciseWeightLoss= 2;
    
    
    public GuineaPig(String name, double weight) {
        super(EnumSpec.GUINEA_PIG, name, 4, weight);
        System.out.println(this.name + "-t elhoztak az allatkereskedesbol");
    }


    public static double generateRandomCoeff (){
        double randomNum = 0D;
        while (randomNum<0.5){
            randomNum = Math.random();
        }
        return randomNum;
    }
    
    
    public  void printStatus(String message){
        
        System.out.println(this.getName() + "  Mit csinál??? \n" 
                + message
                + "\n\n"
                + this.getName() + " tomege: " + this.getWeight());
    }
    
    
    public void takeFood (){
        this.weight *= 1D + weightChangePercent/100*generateRandomCoeff();
        printStatus("Eszik . . . ");
    }

    public void defecate (){
        this.weight *= 1D - weightChangePercent/100*generateRandomCoeff();
        printStatus("Ürít . . . ");
    }
    public void runAround (){
        this.weight *= 1D - weightChangePercent/100*generateRandomCoeff()*factorForExerciseWeightLoss;
        printStatus("Szaladgál . . . ");
    }

    public void sleep(){
            printStatus("Alszik . . . ");
    }
    
    public void biteOwner (String newName){
        printStatus("Megharapta a gazdát!!! A gazda uj nevet ad neki. . . ");
        this.name = newName;

    }
    
}


