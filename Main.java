package budget;

import javax.print.DocFlavor;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import static budget.Main.TypePurchase.ALL;

public class Main {
    enum TypePurchase {
        FOOD("Food"),
        CLOTHES("Clothes"),
        ENTERTAIRNMENT("Entertainment"),
        OTHER("Other"),
        ALL("ALL");

        private final String label;

        private TypePurchase(String label){
            this.label = label;
        }
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
                    "5) Save\n" +
                    "6) Load\n"+
                    "7) Analyse (Sort)\n"+
                    "0) Exit");
            int option = Integer.parseInt(scanner.nextLine());
            System.out.println();
            switch (option) {
                case 0:
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
                case 1:
                    System.out.println("Enter income:");
                    int income = Integer.parseInt(scanner.nextLine());
                    budgetManager.addIncome(income);
                    break;
                case 2:
                    typePurchase = 0;
                    while (true) {
                        System.out.println("Choose the type of purchase\n" +
                                "1) Food\n" +
                                "2) Clothes\n" +
                                "3) Entertainment\n" +
                                "4) Other\n" +
                                "5) Back");
                        typePurchase = Integer.parseInt(scanner.nextLine());

                        if (typePurchase == 5) {
                            break;
                        } else {
                            System.out.println();
                        }

                        System.out.println("Enter purchase name:");
                        String name = scanner.nextLine();
                        System.out.println("Enter its price:");
                        double price = Double.parseDouble(scanner.nextLine());
                        budgetManager.addPurchase(name, TypePurchase.values()[typePurchase - 1], price);
                        System.out.println();
                    }
                    break;
                case 3:
                    if (budgetManager.isEmpty()) {
                        System.out.println("Purchase list is empty");

                    } else {
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

                            if (typePurchase == 6) {
                                break;
                            } else {
                                System.out.println();
                            }
                            TypePurchase type = TypePurchase.values()[typePurchase - 1];
                            budgetManager.getPurchases(type)
                                    .stream()
                                    .forEach(System.out::println);
                            System.out.println("Total sum: $" + budgetManager.getTotal(type));
                            System.out.println();

                        }
                    }
                    break;
                case 4:
                    System.out.println("Balance: $" + budgetManager.formatDouble(budgetManager.getBalance()));
                    break;
                case 5:
                    if (budgetManager.save())
                        System.out.println("Purchases were saved!");
                    break;
                case 6:
                    if (budgetManager.load())
                        System.out.println("Purchase were loaded!");
                    break;
                case 7:


                    while(true) {
                        System.out.println("How do you want to sort?\n" +
                                "1) Sort all purchases\n" +
                                "2) Sort by type\n" +
                                "3) Sort certain type\n" +
                                "4) Back");

                        int selectionSort = Integer.parseInt(scanner.nextLine());
                        if(selectionSort == 4){
                            break;
                        }


                        System.out.println();

                            switch (selectionSort){
                                case 1:
                                    if(budgetManager.isEmpty()){
                                        System.out.println("Purchase list is empty!");


                                    }
                                    else {
                                        budgetManager.sortALL().stream()
                                                .forEach(System.out::println);
                                        System.out.println("Total: $" + budgetManager.getTotal(ALL));
                                    }



                                break;
                            case 2:

                                System.out.println("Types:");
                                for (Map.Entry<TypePurchase, Double> entry : budgetManager.sortByType().entrySet()) {
                                    System.out.println(String.format("%s - $%.2f", entry.getKey().label, entry.getValue()));
                                }
                                double total =  budgetManager.sortByType().values().stream()
                                        .reduce(0.,(acc,v) -> acc + v);
                                System.out.println("Total sum: "+total);
                                break;
                            case 3:


                                    System.out.println("Choose the type of purchase\n" +
                                            "1) Food\n" +
                                            "2) Clothes\n" +
                                            "3) Entertainment\n" +
                                            "4) Other");

                                    TypePurchase typeSelected = TypePurchase.values()[Integer.parseInt(scanner.nextLine())-1];
                                    List<Purchase> purchases = budgetManager.sortType(typeSelected);

                                    if (purchases.isEmpty()){
                                        System.out.println();
                                        System.out.println("Purchase list is empty!");
                                    }
                                    else {
                                        total = purchases.stream()
                                                .map(Purchase::getPrice)
                                                .reduce(0., (value, price) -> value + price);
                                        System.out.println();
                                        purchases.stream()
                                                .forEach(System.out::println);
                                        //System.out.println("Total: $" + total);
                                    }



                        }
                        System.out.println();
                    }

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
