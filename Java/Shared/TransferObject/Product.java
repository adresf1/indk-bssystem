package Shared.TransferObject;

import Shared.Util.Certification;
import Shared.Util.MyDate;
import Shared.Util.Stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Product implements Serializable, Stock {

    private String name;
    private int category;
    private String productDescription;
    private MyDate productionDate, expirationDate;

    private int barcode;
    private double price;
    private int quantity;
    private String unitType;
    private ArrayList<Certification> certificates;

    public Product() {

        certificates = new ArrayList<Certification>();
    }

    public Product(String name, int category, String productDescription, MyDate productionDate, MyDate expirationDate,
                   int barcode, double price, int quantity, String unitType) {
        this.name = name;
        this.category = category;
        this.productDescription = productDescription;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.barcode = barcode;
        this.price = price;
        this.quantity = quantity;
        this.unitType = unitType;
        this.certificates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getCategory() {
        return category;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public MyDate getProductionDate() {
        return productionDate;
    }

    public MyDate getExpirationDate() {
        return expirationDate;
    }

    public int getBarcode() {
        return barcode;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setName(String name) {

        //Define a regular expression with non special charters and underscore/dash
        Pattern p = Pattern.compile("[^a-z0-9-_ ]", Pattern.CASE_INSENSITIVE);

        // Creating matcher for expression and our input string
        Matcher m = p.matcher(name);


        if(m.find()){
            throw new IllegalArgumentException("String contains special characters");
        }
        //if no special charters
        this.name = name;
        System.out.println("Name was saved: " + name);
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductionDate(MyDate productionDate) {
        this.productionDate = productionDate;
    }

    public void setExpirationDate(MyDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    @Override
    public double getTotalValue() {
        if(price < 0 || quantity < 0){
            throw new IllegalArgumentException("Price or Quantity are under 0");
        }
        return price * quantity;
    }

    @Override
    public void buyQuantity(int quantity) {
        if(quantity < 0){
            throw new IllegalArgumentException("Quantity are a invalid value");
        }

    }

    @Override
    public Certification getCertificate(String id) {
        for (Certification c : certificates) {
            if (c.getID().equals(id)) {
                return c;
            }
        }
        return null;
        //Skal der returnes null her eller skal metoden skrives om?
        //Det er fint at metoden returnerer null - TB
    }

    @Override
    public ArrayList<Certification> getCertificationByCountry(String country) {
        ArrayList<Certification> cc = new ArrayList<>();
        for (Certification cert : certificates) {
            if (cert.getCountry().equalsIgnoreCase(country)) {
                cc.add(cert);
            }
        }
        return cc;
    }

    @Override
    public ArrayList<Certification> getCertificationByOrganization(String organization) {
        ArrayList<Certification> co = new ArrayList<>();
        for (Certification cert : certificates) {
            if (cert.getOrganization().equalsIgnoreCase(organization)) {
                co.add(cert);
            }
        }
        return co;
    }

    @Override
    public void addCertification(Certification c) {
        certificates.add(c);
    }

    @Override
    public void editCertification(Certification c) {
        for (int i = 0; i < certificates.size(); i++) {
            if (certificates.get(i).getID().equals(c.getID())) {
                certificates.set(i, c);
                return;
            }
        }
        throw new IllegalArgumentException("Certification is not found with the ID: " + c.getID());
    }

    @Override
    public boolean isExpired() {
        MyDate today = MyDate.today();
        return expirationDate.compareTo(today) < 0;
    }

    @Override
    public int daysUntilExpiration() {
        MyDate today = MyDate.today();
        return expirationDate.daysBetween(today);
    }

    @Override
    public boolean isStockLow() {
        int lowStockThreshold = 2;
        return quantity < lowStockThreshold;

    }

    @Override
    public void editExpirationDate(MyDate newDate) {
        this.expirationDate = newDate;
    }
}
