package budget;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static budget.Main.TypePurchase.*;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toList;

public class BudgetManager  implements Serializable{

    private double income ;
    private List<Purchase> purchases;
    private double total;
    private String nameFile = "purchases.txt";

    public BudgetManager() {
        Locale.setDefault(Locale.US);
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



    public String formatDouble(double number){
        return String.format("%.2f",number);
    }

    public double getBalance(){
        total = getTotal(ALL);
        return income - total;
    }


    public boolean isEmpty() {
        return purchases.isEmpty();
    }

    public boolean save() {
       try(PrintWriter writer = new PrintWriter(new FileWriter(new File(nameFile),false))){
           writer.println(income);

           purchases.stream()
             .forEach(purchase -> writer.println(String.format("%s,%s,%.2f",
                     purchase.getName(),
                     purchase.getType(),
                     purchase.getPrice())));

           return true;
       } catch (IOException e) {
           e.printStackTrace();
       }
        return false;
    }

    public boolean load() {

       try(BufferedReader reader= new BufferedReader(new FileReader(new File(nameFile)))){
           income = Double.parseDouble(reader.readLine());
           String data;
           purchases = reader.lines()
                   .map(line -> line.split(","))
                   .map(item -> Purchase.of(item[0], Main.TypePurchase.valueOf(item[1]),Double.parseDouble(item[2])))
                   .collect(Collectors.toList());
           return true;
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
        return false;
    }

    private double round(double n){
        return Math.round(n * 1000) / 1000;
    }

    public double getIncome() {
        return income;
    }

    public List<Purchase> sortALL() {
         Collections.sort(purchases);
         return purchases.stream().filter( p -> p.getPrice() != 0).collect(Collectors.toList());
    }


    public Map<Main.TypePurchase,Double> sortByType() {
        Purchase[] defaultPurchase  = { Purchase.of("",FOOD,0),
                Purchase.of("",CLOTHES,0),
        Purchase.of("",ENTERTAIRNMENT,0),
        Purchase.of("",OTHER,0)};
        purchases.addAll(Arrays.asList(defaultPurchase));
       return
               purchases
                .stream()
                .collect(Collectors.groupingBy(Purchase::getType,summingDouble(Purchase::getPrice)));

    }

    public List<Purchase> sortType(Main.TypePurchase typeSelected) {
       return  purchases.stream()
                .filter( purchase ->  purchase.getType() == typeSelected && purchase.getPrice() != 0)
                .sorted()
                .collect(Collectors.toList());
    }
}


class Purchase implements Serializable,Comparable<Purchase>{
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
        return String.format("%s $%.2f",name,price);
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(@NotNull Purchase purchase) {
        if (price > purchase.getPrice()){
            return -1;
        }
        else if (price == purchase.getPrice()){
            return 0;
        }
        else {
            return 1;
        }
    }
}
