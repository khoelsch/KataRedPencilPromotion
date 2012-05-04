import java.util.Date;
import java.util.List;

public class RedPencilPromotion {
  private final static int CHANGE_RATE_INTERVAL_START = 5;
  private final static int CHANGE_RATE_INTERVAL_END = 30;
  private final static int DAYS_PROMOTION_DURATION = 30;
  private final static int DAYS_PRICE_MUST_BE_STABLE = 30;
  private final List<PriceChange> appliedPriceChanges;
  private final Date dateOfEvaluation;

  public RedPencilPromotion(List<PriceChange> appliedPriceChanges, Date dateRedPencilPromotionIsEvaluated) {
    this.appliedPriceChanges = appliedPriceChanges;
    this.dateOfEvaluation = new Date(dateRedPencilPromotionIsEvaluated.getYear(),
            dateRedPencilPromotionIsEvaluated.getMonth(),
            dateRedPencilPromotionIsEvaluated.getDay());
  }

  public boolean isActive() {
    if (appliedPriceChanges.size() == 0) {
      return false;
    }

    PriceChange lastPriceChange = appliedPriceChanges.get(appliedPriceChanges.size() - 1);

    return fitsChangeRateInterval(lastPriceChange.changeRate)
            && fitsPromotionInterval();
  }

  private boolean fitsPromotionInterval() {
    //Date firstPriceChange = appliedPriceChanges.get(0).changeDate;
    return false;

    //return firstPriceChange.changeDate.compareTo(dateOfEvaluation) <= 0
  }

  private boolean fitsChangeRateInterval(int adjustmentPercentage) {
    return adjustmentPercentage >= CHANGE_RATE_INTERVAL_START && adjustmentPercentage <= CHANGE_RATE_INTERVAL_END;
  }

  /**
   * @return the last Date this "red pencil promotion" will be valid for
   */
  public Date getIntervalEnd() {
    return null;
  }
}

