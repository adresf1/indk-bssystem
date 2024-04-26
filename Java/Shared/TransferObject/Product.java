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

    private String ID;
    private int category;
    private String productDescription;
    private MyDate productionDate, expirationDate;

    private int barcode;
    private double price;
    private double quantity;

    private double lowStock;
    private String unitType;
    private ArrayList<Certification> certificates;

    public Product() {

        certificates = new ArrayList<Certification>();
    }

    public Product(String name, String ID, int category, String productDescription, MyDate productionDate, MyDate expirationDate,
                   int barcode, double price, double quantity, double lowStock, String unitType) {
        this.name = name;
        this.ID = ID;
        this.category = category;
        this.productDescription = productDescription;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.barcode = barcode;
        this.price = price;
        this.quantity = quantity;
        this.lowStock = lowStock;
        this.unitType = unitType;
        this.certificates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public double getLowStock() {
        return lowStock;
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

    public double getQuantity() {
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
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public void setCategory(int category) {
        if(category <= 0){
            throw new IllegalArgumentException("Category cannot be under 0");
        }
        this.category = category;
    }

    public void setProductDescription(String productDescription) {

        if(productDescription.length()>255){
            throw new IllegalArgumentException("Description cannot be over 255 chars");
        }

        //Define a regular expression with non special charters and underscore/dash
        Pattern p = Pattern.compile("[^a-z0-9-_ ,.'!]", Pattern.CASE_INSENSITIVE);
        // Creating matcher for expression and our input string
        Matcher m = p.matcher(productDescription);
        if(m.find()){
            throw new IllegalArgumentException("String contains special characters");
        }

        this.productDescription = productDescription;
    }

    public void setProductionDate(MyDate productionDate) {
        if(expirationDate != null && expirationDate.isBefore(productionDate)){
            throw new IllegalArgumentException("Expiration date  is before production date");
        }
        this.productionDate = productionDate;

    }

    public void setExpirationDate(MyDate expirationDate) {
        if(expirationDate != null && expirationDate.isBefore(productionDate)){
            throw new RuntimeException("Expiration date is before production date");
        }
        this.expirationDate = expirationDate;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public void setPrice(double price) {
        if(price < 0){
            throw new RuntimeException("Price cannot be under 0.00");
        }
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if(quantity < 0){
            throw new RuntimeException("Quantity cannot be under 0");
        }
        this.quantity = quantity;
    }

    public void setLowStock(int lowStock) {
        if(lowStock < 0 ){
            throw new RuntimeException("lowStock cannot be under 0");
        }

        this.lowStock = lowStock;
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
        this.quantity -= quantity;
    }
    @Override
    public void returnQuantity(int quantity){
        if(quantity < 0){
            throw new IllegalArgumentException("Quantity are a invalid value");
        }
        this.quantity += quantity;
    }

    @Override
    public Certification getCertificate(String id) {

        //Define a regular expression with non special charters
        Pattern p = Pattern.compile("[^a-z0-9-_ ]", Pattern.CASE_INSENSITIVE);
        // Creating matcher for expression and our input string
        Matcher m = p.matcher(id);
        if(m.find()){
            throw new IllegalArgumentException("String contains special characters");
        }

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

        //Define a regular expression with non special charters
        Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        // Creating matcher for expression and our input string
        Matcher m = p.matcher(country);
        if(m.find()){
            throw new IllegalArgumentException("String contains special characters");
        }

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

        //Define a regular expression with non special charters and underscore/dash
        Pattern p = Pattern.compile("[^a-z0-9-_ ]", Pattern.CASE_INSENSITIVE);
        // Creating matcher for expression and our input string
        Matcher m = p.matcher(organization);
        if(m.find()){
            throw new IllegalArgumentException("String contains special characters");
        }

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

        //Todo: Certification class should override equals method
        if(certificates.contains(c)){
            throw new IllegalArgumentException("Certification already exist");
        }

        certificates.add(c);
    }

    @Override
    public void editCertification(Certification c) {

        //Todo: method could be shortened with an equal method on the Certification class
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
        //Todo: Test this i don't think this is correct - use isbefore()
        return expirationDate.compareTo(today) < 0;
    }

    @Override
    public int daysUntilExpiration() {
        MyDate today = MyDate.today();
        return expirationDate.daysBetween(today);
    }

    @Override
    public boolean isStockLow() {;
        return quantity < lowStock;
    }

    @Override
    public void editExpirationDate(MyDate newDate) {
        this.expirationDate = newDate;
    }
}
