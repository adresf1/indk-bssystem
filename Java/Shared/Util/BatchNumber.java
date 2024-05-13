package Shared.Util;

public class BatchNumber implements Certification{


    public String id, database, description;
    public String originCountry, organization, foreignKey;

    public BatchNumber() {
    }

    public BatchNumber(String id, String database, String description, String originCountry, String organization) {
        this.id = id;
        this.database = database;
        this.description = description;
        this.originCountry = originCountry;
        this.organization = organization;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }



    @Override
    public Certification getCertificate() {
        return this;
    }

    @Override
    public boolean isCertificationExpired() {
        return false;
    }

    @Override
    public String getCertificationInfo() {
        return "Certificate!";
    }

    @Override
    public String getDatabase() {
        return database;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getOrganization() {
        return organization;
    }

    @Override
    public String getCountry() {
        return originCountry;
    }
}
