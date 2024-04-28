package ModelDB;

import Shared.Util.BatchNumber;
import Shared.Util.MyDate;
import Shared.Util.Organic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchNumberDB {

    private static BatchNumberDB instance;

    private String warehouseDB;

    private BatchNumberDB() {
        try {
            warehouseDB = "warehouse";
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized BatchNumberDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new BatchNumberDB();
        }
        return instance;
    }

    private static Connection getConnection(String database) throws SQLException
    {
        String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=" + database;
        return DriverManager.getConnection(
                url,
                "postgres", "KarlDen12.");
    }

    public BatchNumber creat(String id, String database, String description, String originCountry, String organization, String foreignKey) throws SQLException {
        try (Connection connection = getConnection(warehouseDB)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + warehouseDB + ".BatchNumber(id, database, description, originCountry, organization, foreignKey) values (?, ?, ?, ?, ?, ?)");

            statement.setString(1, id);
            statement.setString(2, database);
            statement.setString(3, description);
            statement.setString(4, originCountry);
            statement.setString(5, organization);
            statement.setString(6, foreignKey);


            statement.executeUpdate();
            return new BatchNumber(id, database, description, originCountry, organization);
        }
    }

    public void update(BatchNumber batchNumber, String foreignKey) throws SQLException
    {
        try(Connection connection = getConnection(warehouseDB)){
            PreparedStatement statement = connection.prepareStatement("UPDATE organic SET id=?, database=?, description=?, certificationDate=?, expirationDate=?, originCountry=?, organization=?, foreignKey=? where foreignKey=?");

            statement.setString(1, batchNumber.getId());
            statement.setString(2, batchNumber.getDatabase());
            statement.setString(3, batchNumber.getDescription());
            statement.setString(6, batchNumber.getOriginCountry());
            statement.setString(7, batchNumber.getOrganization());
            statement.setString(8, foreignKey);

            statement.executeUpdate();
        }

    }

    public void delete(BatchNumber batchNumber, String foreignKey) throws SQLException
    {
        try(Connection connection = getConnection(warehouseDB)){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM batchNumber WHERE id LIKE ? AND foreignKey ?");
            statement.setString(1, batchNumber.getID());
            statement.setString(2, foreignKey);
            statement.executeUpdate();
        }
    }

}
