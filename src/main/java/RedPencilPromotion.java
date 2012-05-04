import java.util.Date;
import java.util.List;

public class RedPencilPromotion {
  private final static int PROMOTION_INTERVAL_START = 5;
  private final static int PROMOTION_INTERVAL_END = 30;
  private final List<PriceChange> appliedPriceChanges;
  private final Date evalDate;

  public RedPencilPromotion(List<PriceChange> appliedPriceChanges, Date dateRedPencilPromotionIsEvaluated) {
    this.appliedPriceChanges = appliedPriceChanges;
    this.evalDate = new Date(dateRedPencilPromotionIsEvaluated.getYear(),
            dateRedPencilPromotionIsEvaluated.getMonth(),
            dateRedPencilPromotionIsEvaluated.getDay());
  }

  public boolean isActive() {
    if (appliedPriceChanges.size() == 0) {
      return false;
    }

    PriceChange lastPriceChange = appliedPriceChanges.get(appliedPriceChanges.size() - 1);
    return fitsPromotionInterval(lastPriceChange.changeRate);
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

