import java.util.Date;

public class PriceChange {
  final float priceFactor;
  final Date changeDate;

  /**
   * @param priceFactor adjustment relative to article's current price
   * @param changeDate Date when adjustment is applied
   */
  public PriceChange(float priceFactor, Date changeDate) {
    if (Float.compare(priceFactor, 0.0f) <= 0) {
      throw new IllegalArgumentException("'priceFactor' must be greater than zero.");
    }

    this.priceFactor = priceFactor;
    this.changeDate = new Date(changeDate.getYear(), changeDate.getMonth(), changeDate.getDay());
  }
}
