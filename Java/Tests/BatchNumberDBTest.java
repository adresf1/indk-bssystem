package Tests;

import ModelDB.BatchNumberDB;
import Shared.Util.BatchNumber;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BatchNumberDBTest
{

    @BeforeEach public void setUp() throws IOException, SQLException
    {
        //BatchNumberDAOImpl batchDAO = BatchNumberDAOImpl.getInstance();
        BatchNumberDB batchDB = BatchNumberDB.getInstance();

        String[][] batchsData = {
            {"1", "Jysk", "Møbler", "Denmark", "Lars Larsen Group", "1"},
            {"2", "Eliza Chokolade", "Confecture", "Sweden", "Givesco Group",
                "2"},
            {"3", "Salling Group", "Nonfood", "Denmark", "Maersk Invest", "4"},
            {"4", "Arling", "Frost", "Italy", "DSV", "4"}};

        for (String[] batchData : batchsData)
        {
            String batchId = batchData[0];
            //String batchName = batchData[1];
            // Check if the primary key exists in the table
            if (batchDB.DoesPrimaryKeyExitsInTable(batchDB.getWarehouseDB(),
                "batchNumber", batchId))
            {
                // If it exists, update the batch
                BatchNumber batch = new BatchNumber(batchId, batchData[1],
                    batchData[2], batchData[3], batchData[4]);
                batch.setForeignKey(batchData[5]);
                batchDB.update(batch, batchData[5]);
            }
            else
            {
                // If it doesn't exist, create the batch
                BatchNumber batch = new BatchNumber(batchId, batchData[1],
                    batchData[2], batchData[3], batchData[4]);
                batch.setForeignKey(batchData[5]);
                batchDB.createBatchNumber(batch);
            }
        }
        System.out.println("SetUP done");
    }

    @Test void TestIfPrimaryKeyExits() throws SQLException
    {
        BatchNumberDB batchNumberDB = BatchNumberDB.getInstance();

        assertEquals(true, batchNumberDB.DoesPrimaryKeyExitsInTable(
            batchNumberDB.getWarehouseDB(), "batchNumber", "2"));
    }

    @Test void deleteProductTest() throws SQLException
    {
        BatchNumberDB batchNumberDB = BatchNumberDB.getInstance();
        BatchNumber batchNumber = new BatchNumber();
        batchNumber.setId("3");
        batchNumberDB.delete(batchNumber);
        batchNumber.setId("99");
        assertEquals(false, BatchNumberDB.DoesPrimaryKeyExitsInTable(
            batchNumberDB.getWarehouseDB(), "batchNumber", "3"));
        assertThrows(SQLException.class, () -> batchNumberDB.delete(batchNumber));
    }

    @Test void readByNameTest() throws SQLException
    {
        BatchNumberDB batchNumberDB = BatchNumberDB.getInstance();
        List<BatchNumber> batchNumbers = new ArrayList<>();
        List<BatchNumber> batchNumbers1 = new ArrayList<>();
        batchNumbers = batchNumberDB.readByCountry("Denmark");

        batchNumbers1 = batchNumberDB.readByCountry("hejj");

        assertEquals(2, batchNumbers.size());
        assertEquals(0, batchNumbers1.size());

    }

    @Test void updateBatchNumber() throws SQLException
    {
        BatchNumberDB batchDB = BatchNumberDB.getInstance();

        String[][] batchsData = {
            {"1", "Salling Group", "Møbler", "Denmark", "Lars Larsen Group", "1"},
            {"2", "Eliza Chokolade", "Confecture", "Sweden", "Givesco Group",
                "2"},
            {"3", "Salling Group", "Nonfood", "Denmark", "Maersk Invest", "4"},
            {"4", "Arling", "Frost", "Italy", "DSV", "4"}};

        for (String[] batchData : batchsData)
        {
            String batchId = batchData[0];
            //String batchName = batchData[1];
            // Check if the primary key exists in the table
            if (batchDB.DoesPrimaryKeyExitsInTable(batchDB.getWarehouseDB(),
                "batchNumber", batchId))
            {
                // If it exists, update the batch
                BatchNumber batch = new BatchNumber(batchId, batchData[1],
                    batchData[2], batchData[3], batchData[4]);
                batch.setForeignKey(batchData[5]);
                batchDB.update(batch, batchData[5]);
            }

            Assert.assertEquals("Salling Group",
                batchDB.readByID("1").getDatabase());
        }

        //TODO: skriv test UpdateBatchNumber

    }
}
