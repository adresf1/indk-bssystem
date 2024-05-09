package Tests;

import ModelDB.OrganicDB;
import ModelDB.OrganicDB;
import ModelDB.ProductDAOImpl;
import Shared.TransferObject.Product;
import Shared.Util.Organic;
import Shared.Util.MyDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class OrganicDBTest
{
  @BeforeEach public void setUp() throws IOException, SQLException
  {
    //OrganicDAOImpl organicDAO = OrganicDAOImpl.getInstance();
    OrganicDB organicDB = OrganicDB.getInstance();

    String[][] organicsData = {
        {"1", "Fødevarestyrelsen.dk", "Wholefood", "01/01/2020", "02/08/2020",
            "Denmark", "ØkologiMærke", "1"},
        {"2", "Fødevarestyrelsen.dk", "Grocery", "02/01/2019", "23/02/2020",
            "Sweden", "MiljøStyrelsen", "2"},
        {"3", "Fødevarestyrelsen.dk", "Nonfood", "11/01/2018", "27/01/2020",
            "Denmark", "EU", "4"},
        {"4", "Fødevarestyrelsen.dk", "Frost", "11/10/2017", "27/01/2020",
            "Italy", "FødevareStyrelsen", "4"}};

    for (String[] organicData : organicsData)
    {
      String organicId = organicData[0];
      //String organicName = organicData[1];
      // Check if the primary key exists in the table
      if (organicDB.DoesPrimaryKeyExitsInTable(organicDB.getWarehouseDB(),
          "organic", organicId))
      {
        // If it exists, update the organic
        Organic organic = new Organic(organicId, organicData[1], organicData[2],
            MyDate.fromString(organicData[3]), MyDate.fromString(organicData[4]),
            organicData[5], organicData[6]);
        organic.setForeignKey(organicData[7]);
        organicDB.update(organic, organicData[7]);
      }
      else
      {
        // If it doesn't exist, create the organic
        Organic organic = new Organic(organicId, organicData[1], organicData[2],
            MyDate.fromString(organicData[3]), MyDate.fromString(organicData[4]),
            organicData[5], organicData[6]);
        organic.setForeignKey(organicData[7]);
        organicDB.creatOrganic(organic);
      }
    }
    System.out.println("SetUP done");
  }

  @Test void TestIfPrimaryKeyExits() throws SQLException
  {
    OrganicDB organicDB = OrganicDB.getInstance();

    assertEquals(true, organicDB.DoesPrimaryKeyExitsInTable(organicDB.getWarehouseDB(),
        "organic", "2"));
  }

  @Test void deleteProductTest() throws SQLException
  {
    OrganicDB organicDB = OrganicDB.getInstance();
    Organic organic = new Organic();
    organic.setID("3");
    organicDB.delete(organic);
    organic.setID("99");
    assertEquals(false, organicDB.DoesPrimaryKeyExitsInTable(organicDB.getWarehouseDB(),
        "organic", "3"));
    assertThrows(SQLException.class, () -> organicDB.delete(organic));
  }

  @Test void reabyNameTest() throws SQLException
  {
    OrganicDB organicDB = OrganicDB.getInstance();
    List<Organic> organics = new ArrayList<>();
    List<Organic> organics1 = new ArrayList<>();
    organics = organicDB.readByCountry("Denmark");

    organics1 = organicDB.readByCountry("hejj");

    assertEquals(2, organics.size());
    assertEquals(0, organics1.size());

  }

  @Test void UpdateOrganic() throws SQLException
  {
    OrganicDB organicDB = OrganicDB.getInstance();

    String[][] organicsData = {
        {"1", "Folketinget", "Wholefood", "01/01/2020", "02/08/2020",
            "Denmark", "ØkologiMærke", "1"},
        {"2", "Fødevarestyrelsen.dk", "Grocery", "02/01/2019", "23/02/2020",
            "Sweden", "MiljøStyrelsen", "2"},
        {"3", "Fødevarestyrelsen.dk", "Nonfood", "11/01/2018", "27/01/2020",
            "Denmark", "EU", "4"},
        {"4", "Fødevarestyrelsen.dk", "Frost", "11/10/2017", "27/01/2020",
            "Italy", "FødevareStyrelsen", "4"}};

    for (String[] organicData : organicsData)
    {
      String organicId = organicData[0];
      //String organicName = organicData[1];
      // Check if the primary key exists in the table
      if (organicDB.DoesPrimaryKeyExitsInTable(organicDB.getWarehouseDB(),
          "organic", organicId))
      {
        // If it exists, update the organic
        Organic organic = new Organic(organicId, organicData[1], organicData[2],
            MyDate.fromString(organicData[3]), MyDate.fromString(organicData[4]),
            organicData[5], organicData[6]);
        organic.setForeignKey(organicData[7]);
        organicDB.update(organic, organicData[7]);
      }
    }
    assertEquals("Folketinget",organicDB.readByID("1").getDatabase());
  }
}
