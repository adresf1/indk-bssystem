package Tests;

import Shared.TransferObject.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductTest {
    Product testProduct;

    @BeforeEach
    public void setUp() throws IOException {
        testProduct = new Product();

    }
    @Test
    public void testGetProduct() {

    }

    @Test
    public void testIllegalArgumentExceptions(){
        assertThrows(IllegalArgumentException.class,()->testProduct.setName("$!KillerWhale!$"));
    }
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
