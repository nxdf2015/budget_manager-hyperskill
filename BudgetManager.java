package budget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static budget.Main.TypePurchase.ALL;
import static java.util.stream.Collectors.toList;

public class BudgetManager {

    private double income ;
    private List<Purchase> purchases;
    private double total;

    public BudgetManager() {
        income = 0;
        purchases = new ArrayList<>();
        total = 0;
    }

    public void addIncome(int income){
        this.income += income;
    }

    public void addPurchase(String name, Main.TypePurchase type, double price){
       purchases.add(Purchase.of(name,type,price));
       total += price;
    }

    public List<Purchase> getPurchases(Main.TypePurchase type){
        if (type == ALL){
            return purchases;
        }
        else {
            return purchases.stream()
                    .filter(purchase -> purchase.getType() == type)
                    .collect(toList());
        }
    }


    public double getTotal(Main.TypePurchase type){
        List<Purchase> data;
        if (type == ALL) {

            data = purchases;
        }
        else {
           data = getPurchases(type);
        }

        return data.stream()
                .map(Purchase::getPrice)
                .reduce(0., (total, price) -> total + price);


    }

    public double getBalance(){
        total = getTotal(ALL);
        return income - total;
    }


    public boolean isEmpty() {
        return purchases.isEmpty();
    }
}


class Purchase {
    private String name;
    private double price;
    private Main.TypePurchase type;

    public Purchase(String name, Main.TypePurchase type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public static Purchase of(String name, Main.TypePurchase type, double price){
        return new Purchase(name,type,price);
    }

    public Main.TypePurchase getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s $%s",name,price);
    }
}
