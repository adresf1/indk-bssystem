package Shared.Util;

public class Organic implements Certification{
    public String id, database, description;
    public MyDate certificationDate, expirationDate;

    public String originCountry, orginization;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public MyDate getCertificationDate() {
        return certificationDate;
    }

    public MyDate getExpirationDate() {
        return expirationDate;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public String getOrginization() {
        return orginization;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCertificationDate(MyDate certificationDate) {
        this.certificationDate = certificationDate;
    }

    public void setExpirationDate(MyDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public void setOrginization(String orginization) {
        this.orginization = orginization;
    }

    @Override
    public Certification getCertificate() {
        return this;
    }

    @Override
    public boolean isCertificationExpired() {
        return expirationDate.isBefore(new MyDate());
    }

    @Override
    public String getCertificationInfo() {
        return "Certificate!!!";
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
        return orginization;
    }

    @Override
    public String getCountry() {
        return originCountry;
    }
}
