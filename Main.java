package budget;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
         double total= 0 ;

        while(scanner.hasNext()){
            String line = scanner.nextLine();
            System.out.println(line);
            int i = line.lastIndexOf('$');
            total += Double.parseDouble(line.substring(i+1,line.length()));
        }
        System.out.println("Total: $"+total);
    }
}
