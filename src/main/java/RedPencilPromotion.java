import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RedPencilPromotion {
  public final static float PRICE_FACTOR_INTERVAL_START = 0.95f;
  public final static float PRICE_FACTOR_INTERVAL_END = 0.70f;
  public final static int DAYS_PRICE_MUST_BE_STABLE = 30;
  public final static int DAYS_MAXIMUM_PROMOTION_DURATION = 30;

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
    PriceChange promotionStartPriceChange = null;
    for (PriceChange currentPriceChange : appliedPriceChanges) {
      if (!promotionIsActive) {
        if (changeRateWithinValueInterval(currentPriceChange)
                && priceWasStable(priorPriceChange, currentPriceChange)) {
          promotionIsActive = true;
          promotionStartPriceChange = currentPriceChange;
        }

        priorPriceChange = currentPriceChange;
      }
    }
    return promotionIsActive && promotionHasNotEnded(promotionStartPriceChange.changeDate);
  }

  private boolean changeRateWithinValueInterval(PriceChange priceChange) {
    boolean fitsLowerBound = Float.compare(priceChange.priceFactor, PRICE_FACTOR_INTERVAL_START) <= 0;
    boolean fitsUpperBound = Float.compare(priceChange.priceFactor, PRICE_FACTOR_INTERVAL_END) >= 0;

    return fitsLowerBound && fitsUpperBound;
  }

  private boolean priceWasStable(PriceChange priorPriceChange, PriceChange currentPriceChange) {
    if (priorPriceChange == null) {
      return true;
    }

    return isDateWithinRange(priorPriceChange.changeDate,
            currentPriceChange.changeDate,
            RedPencilPromotion.DAYS_PRICE_MUST_BE_STABLE);
  }

  private boolean isDateWithinRange(final Date dateToCheck, final Date referenceDate, final int daysToGoBack) {
    final Calendar cal = Calendar.getInstance();
    cal.setTime(referenceDate);
    cal.add(Calendar.DAY_OF_MONTH, -1 * daysToGoBack);
    final Date earliestValidChangeDate = cal.getTime();
    return dateToCheck.compareTo(earliestValidChangeDate) <= 0;
  }

  private boolean promotionHasNotEnded(final Date promotionStartPriceChangeDate) {
    return isDateWithinRange(promotionStartPriceChangeDate,
            dateOfEvaluation,
            DAYS_MAXIMUM_PROMOTION_DURATION);
  }
}

