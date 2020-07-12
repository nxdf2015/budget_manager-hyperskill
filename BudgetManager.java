package budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetManager {
    private double income ;
    private List<String> purchases;
    private double total;

    public BudgetManager() {
        income = 0;
        purchases = new ArrayList<>();
        total = 0;
    }

    public void addIncome(int income){
        this.income += income;
    }
    public void addPurchase(String name, double price){
       purchases.add(String.format("%s $%f",name,price));
       total += price;
    }

    public List<String> getPurchases(){
       return purchases;
    }

    public double getTotal(){
        return total;
    }

    public double getBalance(){
        return income - total;
    }
}
