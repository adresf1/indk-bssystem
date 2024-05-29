package ModelDB;

import Shared.TransferObject.Product;
import Shared.Util.MyDate;
import Shared.Util.Organic;

import java.sql.*;
import java.util.ArrayList;

public class OrganicDB {

    private static OrganicDB instance;

    private String warehouseDB;

    private OrganicDB() {
        try {
            warehouseDB = "testwarehouse";
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized OrganicDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new OrganicDB();
        }
        return instance;
    }
    public String getWarehouseDB()
    {
        return warehouseDB;
    }
    private static Connection getConnection(String database) throws SQLException
    {
        String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=" + database;
        return DriverManager.getConnection(
                url,
                "postgres", "KarlDen12.");
    }
    //TODO: Husk at ændre password her

    public Organic creat(String id, String database, String description,
                         MyDate certificationDate, MyDate expirationDate, String originCountry,
                         String organization, String foreignKey) throws SQLException {
        try (Connection connection = getConnection(warehouseDB)) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + warehouseDB + ".organic(" +
                    "organic_ID, " +
                    "organic_database, " +
                    "organic_description, " +
                    "organic_certificationDate, " +
                    "organic_expirationDate, " +
                    "organic_originCountry, " +
                    "organic_organization, " +
                    "organic_productsID) values " +
                    "(?, ?, ?, ?, ?, ?, ?, ?)"
            );

            statement.setString(1, id);
            statement.setString(2, database);
            statement.setString(3, description);
            statement.setString(4, certificationDate.toString());
            statement.setString(5, expirationDate.toString());
            statement.setString(6, originCountry);
            statement.setString(7, organization);
            statement.setString(8, foreignKey);

            statement.executeUpdate();
            return new Organic(id, database, description, certificationDate, expirationDate, originCountry, organization);
        }
    }
    public void creatOrganic(Organic organic) throws SQLException
    {
        try(Connection connection = getConnection(warehouseDB)){
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " + warehouseDB + ".organic(" +
                    "organic_ID, " +
                    "organic_database, " +
                    "organic_description, " +
                    "organic_certificationDate, " +
                    "organic_expirationDate, " +
                    "organic_originCountry, " +
                    "organic_organization, " +
                    "organic_productsID) values " +
                    "(?, ?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setString(1, organic.getID());
            statement.setString(2, organic.getDatabase());
            statement.setString(3, organic.getDescription());
            statement.setString(4, organic.getCertificationDate().toString());
            statement.setString(5, organic.getExpirationDate().toString());
            statement.setString(6, organic.getOriginCountry());
            statement.setString(7, organic.getOrganization());
            statement.setString(8, organic.getForeignKey());

            statement.executeUpdate();
        }
    }


    public void update(Organic organic, String foreignKey) throws SQLException
    {
        try(Connection connection = getConnection(warehouseDB)){
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE organic SET " +
                            "organic_ID=?, " +
                            "organic_database=?, " +
                            "organic_description=?, " +
                            "organic_certificationDate=?, " +
                            "organic_expirationDate=?, " +
                            "organic_originCountry=?, " +
                            "organic_organization=?, " +
                            "organic_productsID=? WHERE organic_productsID=? AND organic_ID=?");

            statement.setString(1, organic.getId());
            statement.setString(2, organic.getDatabase());
            statement.setString(3, organic.getDescription());
            statement.setString(4, organic.getCertificationDate().toString());
            statement.setString(5, organic.getExpirationDate().toString());
            statement.setString(6, organic.getOriginCountry());
            statement.setString(7, organic.getOrganization());
            statement.setString(8, foreignKey);
            statement.setString(9, foreignKey);
            statement.setString(10, organic.getId());

            statement.executeUpdate();
        }

    }

    public void delete(Organic organic) throws SQLException
    {
        try(Connection connection = getConnection(warehouseDB)){
            if(DoesPrimaryKeyExitsInTable(warehouseDB,"organic",organic.getID()))
            {
                PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM organic WHERE organic_id LIKE ?");
                statement.setString(1, organic.getID());
                statement.executeUpdate();
            }
            else {
                throw new SQLException("Element does not exits");
            }
        }
    }

    public ArrayList<Organic> readByCountry(String searchString)
            throws SQLException
    {
        try(Connection connection = getConnection(warehouseDB)){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM organic WHERE organic_origincountry LIKE ?");
            statement.setString(1,"%"+searchString+"%");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Organic> resultList = new ArrayList<>();
            while(resultSet.next()){
                //Todo disse kald virker ikke rigtige - er Production_ID den rigtige kolonne.
                String ID = resultSet.getString("organic_id");
                String database = resultSet.getString("organic_database");
                String productDescription = resultSet.getString("organic_description");
                String productionDate = resultSet.getString("organic_certificationdate");
                String expirationDate = resultSet.getString("organic_expirationdate");
                String country = resultSet.getString("organic_origincountry");
                String name = resultSet.getString("organic_organization");
                String IDFK = resultSet.getString("organic_productsid");

                Organic obj = new Organic(ID, database, productDescription, MyDate.fromString(productionDate), MyDate.fromString(expirationDate), country, name);
                obj.setForeignKey(IDFK);
                resultList.add(obj);
            }
            return resultList;
        }
    }

    public Organic readByID(String searchString) throws SQLException
    {
        try(Connection connection = getConnection(warehouseDB)){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM organic WHERE organic_id LIKE ?");
            statement.setString(1,"%"+searchString+"%");
            ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                String ID = resultSet.getString("organic_id");
                String database = resultSet.getString("organic_database");
                String productDescription = resultSet.getString("organic_description");
                String productionDate = resultSet.getString("organic_certificationdate");
                String expirationDate = resultSet.getString("organic_expirationdate");
                String country = resultSet.getString("organic_origincountry");
                String name = resultSet.getString("organic_organization");
                String IDFK = resultSet.getString("organic_productsid");

                Organic obj = new Organic(ID, database, productDescription, MyDate.fromString(productionDate), MyDate.fromString(expirationDate), country, name);
                obj.setForeignKey(IDFK);

            return obj;
        }
    }
    public  static boolean  DoesPrimaryKeyExitsInTable(String warehouseDB,String table, String value) throws SQLException
    {
        try(Connection connection = getConnection(warehouseDB)){
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM "+table+" WHERE organic_id = '"+value+"'");
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
