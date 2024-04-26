package Shared.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Organic implements Certification{
    public String id, database, description;
    public MyDate certificationDate, expirationDate; //CertificationDate er startdatoen

    public String originCountry, orginization;

    public Organic(String id, String database, String description,
        MyDate certificationDate, MyDate expirationDate, String originCountry,
        String orginization)
    {
        this.id = id;
        this.database = database;
        this.description = description;
        this.certificationDate = certificationDate;
        this.expirationDate = expirationDate;
        this.originCountry = originCountry;
        this.orginization = orginization;
    }
    public Organic()
    {

    }

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
        //Define a regular expression with non special charters and underscore/dash
        Pattern p = Pattern.compile("[^a-z0-9-_ ]", Pattern.CASE_INSENSITIVE);

        // Creating matcher for expression and our input string
        Matcher m = p.matcher(description);
        if(description.length() > 255 )
        {
            throw new IllegalArgumentException("description must be less then 255");
        }
        else if (m.find())
        {
            throw new IllegalArgumentException("String contains special characters");
        }
        else
        {
            this.description = description;
        }
    }

    public void setCertificationDate(MyDate certificationDate) {
        this.certificationDate = certificationDate;
    }

    public void setExpirationDate(MyDate startdate,MyDate expirationDate) {
        if(expirationDate.isBefore(startdate))
        {
            throw new IllegalArgumentException("ExpirationDate has to be after startdate");
        }
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
