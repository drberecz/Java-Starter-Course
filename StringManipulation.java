public class StringManipulation {


    public static void main(String[] args) {
        
        String szoveg1 = "alma";   
        System.out.println("Műveletek Szovegekkel");
        
        System.out.println("Szöveg1: " + szoveg1);
        System.out.println("Szöveg1: hossza: " + szoveg1.length());
        System.out.println("Szöveg1 2. karaktere: " + szoveg1.charAt(1));
        System.out.println("Szöveg1: empty?? " + (szoveg1.isEmpty() ? "igen" : "nem"));
        System.out.println("Szöveg1 a beture vegzodik-e: " + (szoveg1.endsWith("a") ? "igen" : "nem"));
        System.out.println("Szöveg1 fa-ra vegzodik-e: " + (szoveg1.endsWith("fa") ? "igen" : "nem"));
        System.out.println("Szöveg1-ben van m betu?:  " + (szoveg1.indexOf("m") >0 ? "igen" : "nem"));
        System.out.println("Szöveg1-ben hanyadik az elso m betu?:  " + (szoveg1.indexOf("m")));
        System.out.println("Szöveg1-ben hanyadik az elso p betu?:  " + (szoveg1.indexOf("p")));

        System.out.println("Szöveg1 nagybetusen: " + szoveg1.toUpperCase());
        System.out.println("Szöveg1 1. karakteretol kezdve: " + szoveg1.substring(1));
    }

    
}
