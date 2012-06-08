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
  private static final int ACTIVE_PROMOTION_DAYS_TO_GO_BACK = RedPencilPromotion.DAYS_PRICE_MUST_BE_STABLE + 2;
  private static final float ACTIVE_PROMOTION_PRICE_FACTOR = RedPencilPromotion.PRICE_FACTOR_INTERVAL_START;

  private RedPencilPromotion rpp;
  private List<PriceChange> priceChanges;

  @Test
  public void notActive_noPriceChanges() {
    priceChanges = Collections.emptyList();

    rpp = new RedPencilPromotion(priceChanges, nowDate());

    assertPromotionNotActive();
  }

  @Test
  public void isActive_singlePriceChangeWithinPriceFactorInterval() {
    PriceChange priceChange = new PriceChange(0.8f, nDaysBackFromNow(ACTIVE_PROMOTION_DAYS_TO_GO_BACK));
    priceChanges = Arrays.asList(priceChange);

    rpp = new RedPencilPromotion(priceChanges, nowDate());

    assertPromotionIsActive();
  }

  @Test
  public void isActive_singlePriceChangeOnPriceFactorIntervalStart() {
    final float priceFactor = RedPencilPromotion.PRICE_FACTOR_INTERVAL_START;
    final Date changeDate = nDaysBackFromNow(ACTIVE_PROMOTION_DAYS_TO_GO_BACK);

    PriceChange priceChange = new PriceChange(priceFactor, changeDate);
    priceChanges = Arrays.asList(priceChange);

    rpp = new RedPencilPromotion(priceChanges, nowDate());

    assertPromotionIsActive();
  }

  @Test
  public void isActive_singlePriceChangeOnPriceFactorIntervalEnd() {
    final float priceFactor = RedPencilPromotion.PRICE_FACTOR_INTERVAL_END;
    final Date changeDate = nDaysBackFromNow(ACTIVE_PROMOTION_DAYS_TO_GO_BACK);

    PriceChange priceChange = new PriceChange(priceFactor, changeDate);
    priceChanges = Arrays.asList(priceChange);

    rpp = new RedPencilPromotion(priceChanges, nowDate());

    assertPromotionIsActive();
  }

  @Test
  public void notActive_singlePriceChangeBeforePriceFactorIntervalStart() {
    final float priceFactor = RedPencilPromotion.PRICE_FACTOR_INTERVAL_START + 0.1f;
    final Date changeDate = nDaysBackFromNow(ACTIVE_PROMOTION_DAYS_TO_GO_BACK);

    PriceChange priceChange = new PriceChange(priceFactor, changeDate);
    priceChanges = Arrays.asList(priceChange);

    rpp = new RedPencilPromotion(priceChanges, nowDate());

    assertPromotionNotActive();
  }

  @Test
  public void isActive_singlePriceChangeAfterPriceFactorIntervalEnd() {
    final float priceFactor = RedPencilPromotion.PRICE_FACTOR_INTERVAL_END - 0.1f;
    final Date changeDate = nDaysBackFromNow(ACTIVE_PROMOTION_DAYS_TO_GO_BACK);

    PriceChange priceChange = new PriceChange(priceFactor, changeDate);
    priceChanges = Arrays.asList(priceChange);

    rpp = new RedPencilPromotion(priceChanges, nowDate());

    assertPromotionNotActive();
  }

  @Test
  public void notActive_priceNotStable() {
    final float priceFactor1 = ACTIVE_PROMOTION_PRICE_FACTOR;
    PriceChange pc1 = new PriceChange(priceFactor1, nDaysBackFromNow(3));

    final float priceFactor2 = ACTIVE_PROMOTION_PRICE_FACTOR;
    PriceChange pc2 = new PriceChange(priceFactor2, nowDate());

    priceChanges = Arrays.asList(pc1, pc2);
    rpp = new RedPencilPromotion(priceChanges, nowDate());

    assertPromotionNotActive();
  }

  @Test
  public void isActive_priceIsStable() {
    final float priceFactor1 = ACTIVE_PROMOTION_PRICE_FACTOR;
    PriceChange pc1 = new PriceChange(priceFactor1, nDaysBackFromNow(ACTIVE_PROMOTION_DAYS_TO_GO_BACK));

    final float priceFactor2 = ACTIVE_PROMOTION_PRICE_FACTOR;
    PriceChange pc2 = new PriceChange(priceFactor2, nowDate());

    priceChanges = Arrays.asList(pc1, pc2);
    rpp = new RedPencilPromotion(priceChanges, nowDate());

    assertPromotionIsActive();
  }

  @Test
  public void notActive_promotionEndedAfterMaximumDays() {
    final float priceFactor1 = ACTIVE_PROMOTION_PRICE_FACTOR;
    PriceChange pc1 = new PriceChange(priceFactor1, nDaysBackFromNow(40));

    priceChanges = Arrays.asList(pc1);
    rpp = new RedPencilPromotion(priceChanges, nowDate());

    assertPromotionNotActive();
  }

  // --------------
  // Helper methods
  // --------------

  private void assertPromotionNotActive() {
    Assert.assertEquals(MSG_PRICE_REDUCED, false, rpp.isActive());
  }

  private void assertPromotionIsActive() {
    Assert.assertEquals(MSG_PRICE_NOT_REDUCED, true, rpp.isActive());
  }

  private static Date nowDate() {
    return Calendar.getInstance().getTime();
  }

  private static Date nDaysBackFromNow(int daysToGoBack) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_MONTH, -1 * daysToGoBack);
    return cal.getTime();
  }
}