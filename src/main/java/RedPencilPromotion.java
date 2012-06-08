import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RedPencilPromotion {
  public final static float PRICE_FACTOR_INTERVAL_START = 0.95f;
  public final static float PRICE_FACTOR_INTERVAL_END = 0.70f;
  public final static int DAYS_PRICE_MUST_BE_STABLE = 30;

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
      promotionIsActive = changeRateWithinValueInterval(currentPriceChange)
              && priceWasStable(priorPriceChange, currentPriceChange);

      priorPriceChange = currentPriceChange;
    }

    return promotionIsActive;
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

    final Calendar cal =  Calendar.getInstance();
    cal.setTime(currentPriceChange.changeDate);
    cal.add(Calendar.DAY_OF_MONTH, -1 * RedPencilPromotion.DAYS_PRICE_MUST_BE_STABLE);
    final Date earliestValidChangeDate = cal.getTime();

    return priorPriceChange.changeDate.compareTo(earliestValidChangeDate) <= 0;
  }
}

