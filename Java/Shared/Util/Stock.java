package Shared.Util;

import java.util.ArrayList;

public interface Stock {
    public double getTotalValue();

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
}
