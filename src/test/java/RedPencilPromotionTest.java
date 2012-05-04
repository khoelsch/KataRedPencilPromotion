import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RedPencilPromotionTest {
  private static final String ASSERT_MSG_SUCCESS = "Price not reduced, although it should be!";
  private static final String ASSERT_MSG_FAILURE = "Price is reduced, although it should not!";
  private RedPencilPromotion rpp;
  private List<PriceChange> priceChanges;
  private Calendar nowCal;

  @Before
  public void setUp() {
    nowCal = Calendar.getInstance();
  }

  @Test
  public void isActive_noPriceChanges() {
    priceChanges = Collections.emptyList();

    rpp = new RedPencilPromotion(priceChanges, nowCal.getTime());

    Assert.assertEquals(ASSERT_MSG_FAILURE, false, rpp.isActive());
  }

  @Test
  public void isActive_singlePriceChangeWithinInterval() {
    Date tenDaysAgo = createDateNDaysBackFrom(nowCal, 10);

    PriceChange priceChange = new PriceChange(10, tenDaysAgo);
    priceChanges = Arrays.asList(priceChange);

    rpp = new RedPencilPromotion(priceChanges, nowCal.getTime());

    Assert.assertEquals(ASSERT_MSG_SUCCESS, true, rpp.isActive());
  }

  private Date createDateNDaysBackFrom(Calendar cal, int daysToGoBack) {
    cal.add(Calendar.DAY_OF_MONTH, -1 * daysToGoBack);
    return cal.getTime();
  }

  @Test
  public void isActive_singlePriceChangeOutsideInterval() {
    Date seventyDaysAgo = createDateNDaysBackFrom(nowCal, 70);

    PriceChange priceChange = new PriceChange(10, seventyDaysAgo);
    priceChanges = Arrays.asList(priceChange);

    rpp = new RedPencilPromotion(priceChanges, nowCal.getTime());

    Assert.assertEquals(ASSERT_MSG_SUCCESS, true, rpp.isActive());
  }
}