package budget;

import javax.print.DocFlavor;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BudgetManager budgetManager = new BudgetManager();
        while(true){
            System.out.println("Choose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "0) Exit\n");
            int option = Integer.parseInt(scanner.nextLine());
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
                    System.out.println("Enter purchase name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter its price:");
                    double price = Double.parseDouble(scanner.nextLine());
                    budgetManager.addPurchase(name,price);
                    break;
                case 3:
                    if (budgetManager.getPurchases().isEmpty()){
                        System.out.println("Purchase list is empty");

                    }
                    else{
                        budgetManager.getPurchases()
                                .stream()
                                .forEach(System.out::println);
                        System.out.println("Total sum: $"+budgetManager.getTotal());
                    }
                    break;
                case 4:
                    System.out.println("Balance: $"+budgetManager.getBalance());
                    break;

            }
            System.out.println();
        }
    }

}
