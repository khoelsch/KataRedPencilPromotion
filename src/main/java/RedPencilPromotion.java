import java.util.Date;
import java.util.List;

public class RedPencilPromotion {
  private final static float CHANGE_RATE_INTERVAL_START = 0.95f;
  private final static float CHANGE_RATE_INTERVAL_END = 0.70f;
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
    boolean promotionIsActive = false;

    PriceChange priorPriceChange = null;
    for (PriceChange currentPriceChange : appliedPriceChanges) {
      promotionIsActive = changeRateWithinValueInterval(currentPriceChange);
              //&& priceWasStable(priorPriceChange, currentPriceChange);

      priorPriceChange = currentPriceChange;
    }

    return promotionIsActive;
  }

  private boolean changeRateWithinValueInterval(PriceChange priceChange) {
    boolean fitsLowerBound = Float.compare(priceChange.priceFactor, CHANGE_RATE_INTERVAL_START) <= 0;
    boolean fitsUpperBound = Float.compare(priceChange.priceFactor, CHANGE_RATE_INTERVAL_END) >= 0;

    return fitsLowerBound && fitsUpperBound;
  }

  private boolean fitsPromotionInterval() {
    //Date firstPriceChange = appliedPriceChanges.get(0).changeDate;
    return false;

    //return firstPriceChange.changeDate.compareTo(dateOfEvaluation) <= 0
  }

  /**
   * @return the last Date this "red pencil promotion" will be valid for
   */
  public Date getIntervalEnd() {
    return null;
  }
}

