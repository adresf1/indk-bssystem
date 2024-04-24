package Shared.TransferObject;

import Shared.Util.Certification;
import Shared.Util.MyDate;
import Shared.Util.Stock;

import java.io.Serializable;
import java.util.ArrayList;


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

    @Override
    public double getTotalValue() {
        return price* quantity;
    }

    @Override
    public void buyQuantity(int quantity) {

    }

    @Override
    public Certification getCertificate(String id) {
        for (Certification c : certificates) {
            if (c.getID().equals(id)) {
                return c;
            }
        }
        return null; //Skal der returnes null her eller skal metoden skrives om?
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
