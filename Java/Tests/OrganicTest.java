package Tests;

import Shared.TransferObject.Product;
import Shared.Util.MyDate;
import Shared.Util.Organic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class OrganicTest
{
  private Organic organic;
  @BeforeEach void startup()
  {
    organic = new Organic();

  }

  @Test void testIfDescriptionIsUnder()
  {
      assertThrows(IllegalArgumentException.class, () -> organic.setDescription("\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum feugiat, leo id posuere tempus, tortor justo faucibus nisl, non viverra purus justo nec nulla. Donec lacinia turpis nec est condimentum, non molestie eros sagittis. Quisque eget elit ac quam tristique malesuada id vitae odio. Suspendisse fringilla lectus vel vestibulum auctor. Sed eu nisl mauris. Proin a purus in diam maximus accumsan nec at justo. Integer et eros vitae nisi luctus malesuada nec vel nulla. Morbi vitae erat faucibus, vehicula odio nec, faucibus lorem. Duis et dapibus lacus. Curabitur vitae turpis vitae turpis faucibus varius. In eu condimentum mauris, nec consectetur sem. Fusce ac turpis ligula. Etiam efficitur bibendum fringilla. Aenean non mauris vel mi malesuada blandit vel ac elit."),"Should throw exception if characters is more than 255");
  }
  @Test void testifDescriptionContainsSpecialCharacters()
  {
    assertThrows(IllegalArgumentException.class,()->organic.setDescription("$!KillerWhale!$"));
  }
  @Test void testifCertificationIsExpired()
  {
    MyDate experationdate = new MyDate(12,28,2022);
    Organic organic1 = new Organic();
    organic1.setExpirationDate(new MyDate(12,8,2020),experationdate);

    assertTrue(organic1.isCertificationExpired(),"Should not give an issue because startdate is before experationdate");

  }
  @Test void TestGetters()
  {
    Organic organic = new Organic();
    MyDate certificationDate = new MyDate(12, 8, 2020);
    MyDate expirationDate = new MyDate(12, 28, 2021);

    organic.setCertificationDate(certificationDate);
    organic.setExpirationDate(certificationDate,expirationDate);
    organic.setDescription("Organic Product");
    organic.setOriginCountry("Brazil");
    organic.setOrginization("Tep Foods Organization");

    assertEquals(certificationDate,organic.getCertificationDate(),"Should match the same date");
    assertEquals(expirationDate, organic.getExpirationDate(),"Should match the same date");
    assertEquals("Organic Product", organic.getDescription(), "Should match the same description");
    assertEquals("Brazil",organic.getOriginCountry(),"Should match the same country");
    assertEquals("Tep Foods Organization", organic.getOrganization(), "Should match the same organization");
  }



}
