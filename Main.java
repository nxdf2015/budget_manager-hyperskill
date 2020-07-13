package budget;

import javax.print.DocFlavor;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    enum TypePurchase {
        FOOD,CLOTHES,ENTERTAIRNMENT,OTHER,ALL;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BudgetManager budgetManager = new BudgetManager();
        int typePurchase = 0 ;
        while(true){
            System.out.println("Choose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "0) Exit");
            int option = Integer.parseInt(scanner.nextLine());
            System.out.println();
            switch (option){
                case 0:
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
                case 1:
                    System.out.println("Enter income:");
                    int income = Integer.parseInt(scanner.nextLine());
                    budgetManager.addIncome(income);
                    break;
                case 2 :
                    typePurchase=0;
                    while(true) {
                        System.out.println("Choose the type of purchase\n" +
                                "1) Food\n" +
                                "2) Clothes\n" +
                                "3) Entertainment\n" +
                                "4) Other\n" +
                                "5) Back");
                        typePurchase = Integer.parseInt(scanner.nextLine());

                        if(typePurchase==5){
                            break;
                        }
                        else {
                            System.out.println();
                        }

                        System.out.println("Enter purchase name:");
                        String name = scanner.nextLine();
                        System.out.println("Enter its price:");
                        double price = Double.parseDouble(scanner.nextLine());
                        budgetManager.addPurchase(name,TypePurchase.values()[typePurchase-1], price);
                        System.out.println();
                    }
                    break;
                case 3:
                    if (budgetManager.isEmpty()){
                        System.out.println("Purchase list is empty");

                    }
                    else {
                        typePurchase = 0;
                        while (true) {
                            System.out.println("Choose the type of purchases\n" +
                                    "1) Food\n" +
                                    "2) Clothes\n" +
                                    "3) Entertainment\n" +
                                    "4) Other\n" +
                                    "5) All\n" +
                                    "6) Back");
                            typePurchase = Integer.parseInt(scanner.nextLine());

                            if (typePurchase == 6){
                                break;
                            }
                            else {
                                System.out.println();
                            }
                            TypePurchase type = TypePurchase.values()[typePurchase-1];
                            budgetManager.getPurchases(type)
                                    .stream()
                                    .forEach(System.out::println);
                            System.out.println("Total sum: $" + budgetManager.getTotal(type));
                            System.out.println();

                        }
                    }
                    break;
                case 4:
                    System.out.println("Balance: $"+budgetManager.getBalance());
                    break;

            }
            System.out.println();
        }
    }
//        Scanner scanner = new Scanner(System.in);
//         double total= 0 ;
//
//        while(scanner.hasNext()){
//            String line = scanner.nextLine();
//            System.out.println(line);
//            int i = line.lastIndexOf('$');
//            total += Double.parseDouble(line.substring(i+1,line.length()));
//        }
//        System.out.println("Total: $"+total);
//    }
}
