import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RedPencilPromotionTest {
  private static final String MSG_PRICE_NOT_REDUCED = "Price NOT reduced, although it should be!";
  private static final String MSG_PRICE_REDUCED = "Price IS reduced, although it shouldn't!";
  private RedPencilPromotion rpp;
  private List<PriceChange> priceChanges;

  @Test
  public void isActive_noPriceChanges() {
    priceChanges = Collections.emptyList();

    rpp = new RedPencilPromotion(priceChanges, nowDate());

    Assert.assertEquals(MSG_PRICE_REDUCED, false, rpp.isActive());
  }

  /*
  @Test
  public void isActive_singlePriceChangeWithinInterval() {
    PriceChange priceChange = new PriceChange(20, nDaysBackFromNow(10));
    priceChanges = Arrays.asList(priceChange);

    rpp = new RedPencilPromotion(priceChanges, nowDate());

    Assert.assertEquals(MSG_PRICE_NOT_REDUCED, true, rpp.isActive());
  }
  */

  private static Date nowDate() {
    return Calendar.getInstance().getTime();
  }

  private static Date nDaysBackFromNow(int daysToGoBack) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_MONTH, -1 * daysToGoBack);
    return cal.getTime();
  }

  /*
  @Test
  public void isActive_secondPriceChangeStableLongEnough() {
    PriceChange priceChangeSeventyDaysAgo = new PriceChange(20, nDaysBackFromNow(70));
    PriceChange priceChangeTenDaysAgo = new PriceChange(20, nDaysBackFromNow(10));
    priceChanges = Arrays.asList(priceChangeSeventyDaysAgo, priceChangeTenDaysAgo);

    rpp = new RedPencilPromotion(priceChanges, nowDate());

    Assert.assertEquals(MSG_PRICE_NOT_REDUCED, true, rpp.isActive());
  }
  */

  /*
  @Test
  public void isActive_singlePriceChangeOutsideInterval() {
    Date seventyDaysAgo = nDaysBackFromNow(70);

    PriceChange priceChange = new PriceChange(10, seventyDaysAgo);
    priceChanges = Arrays.asList(priceChange);

    rpp = new RedPencilPromotion(priceChanges, nowDate());

    Assert.assertEquals(MSG_PRICE_REDUCED, false, rpp.isActive());
  }
  */
}