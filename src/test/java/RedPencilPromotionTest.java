import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RedPencilPromotionTest {
  private static final String ASSERT_MSG_SUCCESS_EXPECTED = "Price not reduced, although it should be!";
  private RedPencilPromotion rpp;
  private List<PriceChange> priceChanges;

  @Test
  public void rppActive_singlePriceAdjustment() {
    final Calendar cal = Calendar.getInstance();
    Date now = cal.getTime();
    cal.add(Calendar.DAY_OF_MONTH, 10);
    Date tenDaysLater = cal.getTime();

    PriceChange priceChange = new PriceChange(10, tenDaysLater);
    priceChanges = Arrays.asList(priceChange);

    rpp = new RedPencilPromotion(priceChanges);

    Assert.assertEquals(ASSERT_MSG_SUCCESS_EXPECTED, true, rpp.isActive());
  }
}
