import java.util.Date;
import java.util.List;

public class RedPencilPromotion {
  private final static int PROMOTION_INTERVAL_START = 5;
  private final static int PROMOTION_INTERVAL_END = 30;
  private final List<PriceAdjustment> appliedPriceAdjustments;

  public RedPencilPromotion(List<PriceAdjustment> appliedPriceAdjustments) {
    if (appliedPriceAdjustments == null) {
      throw new IllegalStateException("Argument 'appliedPriceAdjustments' my not be null.");
    }

    this.appliedPriceAdjustments = appliedPriceAdjustments;
  }

  public boolean isActive() {
    if (appliedPriceAdjustments.size() == 0) {
      return false;
    }

    PriceAdjustment lastPriceAdjustment = appliedPriceAdjustments.get(appliedPriceAdjustments.size() - 1);
    return fitsPromotionInterval(lastPriceAdjustment.adjustmentPercentage);
  }

  private boolean fitsPromotionInterval(int adjustmentPercentage) {
    return adjustmentPercentage >= PROMOTION_INTERVAL_START && adjustmentPercentage <= PROMOTION_INTERVAL_END;
  }

  /**
   * @return the last Date this "red pencil promotion" will be valid for
   */
  public Date getIntervalEnd() {
    return null;
  }
}

