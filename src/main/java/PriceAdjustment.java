import java.util.Date;

public class PriceAdjustment {
  final int adjustmentPercentage;
  final Date adjustmentDate;

  /**
   * @param adjustmentPercentage adjustment relative to article's current price
   * @param adjustmentDate Date when adjustment is applied
   */
  public PriceAdjustment(int adjustmentPercentage, Date adjustmentDate) {
    this.adjustmentPercentage = adjustmentPercentage;
    this.adjustmentDate = adjustmentDate;
  }
}
