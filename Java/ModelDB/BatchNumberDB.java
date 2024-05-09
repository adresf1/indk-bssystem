package ModelDB;

import Shared.Util.BatchNumber;

import java.sql.*;
import java.util.ArrayList;

public class BatchNumberDB {

    private static BatchNumberDB instance;

    private String warehouseDB;

    private BatchNumberDB() {
        try {
            warehouseDB = "testwarehouse";
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

    public String getWarehouseDB() {
        return warehouseDB;
    }

    private static Connection getConnection(String database) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=" + database;
        return DriverManager.getConnection(
                url,
                "postgres", "Kika12345");
    }

    public BatchNumber creat(String id, String database, String description, String originCountry, String organization, String foreignKey) throws SQLException {
        try (Connection connection = getConnection(warehouseDB)) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + warehouseDB + ".batchNumber(" +
                            "batch_ID, " +
                            "batch_database, " +
                            "batch_description, " +
                            "batch_originCountry, " +
                            "batch_organization, " +
                            "batch_productsID, " +
                            "(?, ?, ?, ?, ?, ?)"
            );

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

    public void createBatchNumber(BatchNumber bn) throws SQLException {
        try (Connection connection = getConnection(warehouseDB)) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + warehouseDB + ".batchNumber(" +
                            "batch_ID, " +
                            "batch_database, " +
                            "batch_description, " +
                            "batch_originCountry, " +
                            "batch_organization, " +
                            "batch_productsID, " +
                            "(?, ?, ?, ?, ?, ?)"
            );
            statement.setString(1, bn.getID());
            statement.setString(2, bn.getDatabase());
            statement.setString(3, bn.getDescription());
            statement.setString(4, bn.getOriginCountry());
            statement.setString(5, bn.getOrganization());
            statement.setString(6, bn.getForeignKey());

            statement.executeUpdate();
        }
    }

    public void update(BatchNumber bn, String foreignKey) throws SQLException {
        try (Connection connection = getConnection(warehouseDB)) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE batchNumber SET " +
                            "batch_ID, " +
                            "batch_database, " +
                            "batch_description, " +
                            "batch_originCountry, " +
                            "batch_organization, " +
                            "batch_productsID, " +
                            "batch_productsID=? WHERE batch_productsID=? AND batch_ID=?");

            statement.setString(1, bn.getId());
            statement.setString(2, bn.getDatabase());
            statement.setString(3, bn.getDescription());
            statement.setString(4, bn.getOriginCountry());
            statement.setString(5, bn.getOrganization());
            statement.setString(6, foreignKey);
            statement.setString(7, foreignKey);
            statement.setString(8, bn.getId());

            statement.executeUpdate();
        }

    }

    public void delete(BatchNumber bn) throws SQLException {
        try (Connection connection = getConnection(warehouseDB)) {
            if (DoesPrimaryKeyExitsInTable(warehouseDB, "batchNumber", bn.getID())) {
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM batchNumber WHERE batch_ID LIKE ?");
                statement.setString(1, bn.getID());
                statement.executeUpdate();
            } else {
                throw new SQLException("Element does not exits");
            }
        }
    }

    public ArrayList<BatchNumber> readByCountry(String searchString)
            throws SQLException
    {
        try(Connection connection = getConnection(warehouseDB)){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM batchNumber WHERE batch_originCountry LIKE ?");
            statement.setString(1,"%"+searchString+"%");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<BatchNumber> resultList = new ArrayList<>();
            while(resultSet.next()){
                String ID = resultSet.getString("batch_ID");
                String database = resultSet.getString("batch_database");
                String batchDescription = resultSet.getString("batch_description");
                String originCountry = resultSet.getString("batch_originCountry");
                String organization = resultSet.getString("batch_organization");
                String IDFK = resultSet.getString("batch_productsID");

                BatchNumber obj = new BatchNumber(ID, database, batchDescription, originCountry,organization);
                obj.setForeignKey(IDFK);
                resultList.add(obj);
            }
            return resultList;
        }
    }

    public BatchNumber readByID(String searchString) throws SQLException
    {
        try(Connection connection = getConnection(warehouseDB)){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM batchNumber WHERE batch_ID LIKE ?");
            statement.setString(1,"%"+searchString+"%");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String ID = resultSet.getString("batch_ID");
            String database = resultSet.getString("batch_database");
            String batchDescription = resultSet.getString("batch_description");
            String originCountry = resultSet.getString("batch_originCountry");
            String organization = resultSet.getString("batch_organization");
            String IDFK = resultSet.getString("batch_productsID");

            BatchNumber obj = new BatchNumber(ID, database, batchDescription, originCountry,organization);
            obj.setForeignKey(IDFK);
            return obj;
        }
    }


    public  static boolean  DoesPrimaryKeyExitsInTable(String warehouseDB,String table, String value) throws SQLException
    {
        try(Connection connection = getConnection(warehouseDB)){
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM "+table+" WHERE batch_ID = '"+value+"'");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("count");
            // statement.executeUpdate();
            if(count>0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}