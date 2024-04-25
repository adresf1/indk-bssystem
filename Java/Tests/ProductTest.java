package Tests;

import Shared.TransferObject.Product;
import Shared.Util.MyDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductTest {
    Product testProduct;

    @BeforeEach
    public void setUp() throws IOException {
        testProduct = new Product();

    }
    @Test
    public void test_setCategory_IllegalArgumentExceptions() {
        assertThrows(IllegalArgumentException.class,()->testProduct.setCategory(0));
        assertThrows(IllegalArgumentException.class,()->testProduct.setCategory(-1));

        Assertions.assertDoesNotThrow(()->testProduct.setCategory(1));
    }

    @Test
    public void test_setCategory_value() {
        testProduct.setCategory(5);
        assertEquals(5,testProduct.getCategory());

        assertThrows(IllegalArgumentException.class,()->testProduct.setCategory(-1));

        assertEquals(5,testProduct.getCategory());

    }


    @Test
    public void test_setName_IllegalArgumentExceptions(){
        assertThrows(IllegalArgumentException.class,()->testProduct.setName("!KillerWhale"));
        assertThrows(IllegalArgumentException.class,()->testProduct.setName("$KillerWhale!$"));
        assertThrows(IllegalArgumentException.class,()->testProduct.setName("KillerWhæle"));

        Assertions.assertDoesNotThrow(()->testProduct.setName("KillerWhale"));
        Assertions.assertDoesNotThrow(()->testProduct.setName(""));
    }

    @Test
    public void test_setDescription_IllegalArgumentExceptions(){
        //Description 256 chars long
        assertThrows(IllegalArgumentException.class,()->testProduct.setProductDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse aliquet laoreet nisi in aliquet. Aliquam rutrum vel ante at euismod. Suspendisse maximus viverra est, vel ultricies mauris iaculis quis. Vestibulum porttitor odio eros, in tempus gravida."));
        //Special Charaters
        assertThrows(IllegalArgumentException.class,()->testProduct.setProductDescription("#KillerWhale"));
        assertThrows(IllegalArgumentException.class,()->testProduct.setProductDescription("$KillerWhale"));
        assertThrows(IllegalArgumentException.class,()->testProduct.setProductDescription("KillerWhæle"));

        //
        Assertions.assertDoesNotThrow(()->testProduct.setProductDescription("Survival Strategy"));
        Assertions.assertDoesNotThrow(()->testProduct.setProductDescription(""));
        //Description 254 chars Long
        Assertions.assertDoesNotThrow(()->testProduct.setProductDescription("rem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse aliquet laoreet nisi in aliquet. Aliquam rutrum vel ante at euismod. Suspendisse maximus viverra est, vel ultricies mauris iaculis quis. Vestibulum porttitor odio eros, in tempus gravida."));

    }

    @Test
    public void test_setDescription_value() {
        testProduct.setProductDescription("Happy feet");

        assertEquals("Happy feet",testProduct.getProductDescription());

        testProduct.setProductDescription("PenguinMart' best value choice!");

        assertEquals("PenguinMart' best value choice!",testProduct.getProductDescription());

    }

    @Test
    public void test_getProductionDate_IllegalArgumentExceptions(){
        MyDate today = new MyDate();
        MyDate aWeekFromToday = new MyDate();
        aWeekFromToday.stepForward(7);

        testProduct.setExpirationDate(today);

        // Expiration date before production date
        assertThrows(IllegalArgumentException.class,()->testProduct.setProductionDate(aWeekFromToday));
        //Special Charaters

        testProduct.setExpirationDate(aWeekFromToday);

        //
        Assertions.assertDoesNotThrow(()->testProduct.setProductionDate(today));
    }

    @Test
    public void test_getProductionDate_value() {
        MyDate today = new MyDate();
        MyDate aWeekFromToday = new MyDate();
        aWeekFromToday.stepForward(7);

        MyDate todayAssert = new MyDate();
        MyDate aWeekFromTodayAssert = new MyDate();
        aWeekFromTodayAssert.stepForward(7);

        testProduct.setProductionDate(aWeekFromToday);

        assertEquals(aWeekFromTodayAssert,testProduct.getProductionDate());

        testProduct.setProductionDate(today);

        assertEquals(todayAssert,testProduct.getProductionDate());

    }


    /*

    //TODO: finish testcases for the following methods

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
    * */





    /*
    public void buyQuantity(int quantity);

    public Certification getCertificate(String id);

    public ArrayList<Certification> getCertificationByCountry(String country);

    public ArrayList<Certification> getCertificationByOrganization(String organization);

    public void addCertification(Certification c);

    public void editCertification(Certification c);

    public boolean isExpired();

    public int daysUntilExpiration();

    public boolean isStockLow();

    public void editExpirationDate(MyDate newDate);
*/
}
