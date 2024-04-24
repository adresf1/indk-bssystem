package Shared.Util;

public class BatchNumber implements Certification{


    public String id, database, description;
    public String originCountry, orginization;

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
        return orginization;
    }

    @Override
    public String getCountry() {
        return originCountry;
    }
}
