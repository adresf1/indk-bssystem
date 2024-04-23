package Shared.TransferObject;

import Shared.Util.Certification;
import Shared.Util.MyDate;

import java.io.Serializable;
import java.util.ArrayList;


public class Product implements Serializable {

    private String name;
    private int category;
    private String productDescription;
    private MyDate productionDate, expirationDate;

    private int barcode;
    private double price;
    private int quantity;
    private String unitYpe;
    private ArrayList<Certification> certificates;

    public Product() {
        certificates = new ArrayList<Certification>();
    }

}
