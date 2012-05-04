import java.util.Date;

public class PriceChange {
  final int changeRate;
  final Date changeDate;

  /**
   * @param changeRate adjustment relative to article's current price
   * @param changeDate Date when adjustment is applied
   */
  public PriceChange(int changeRate, Date changeDate) {
    this.changeRate = changeRate;
    this.changeDate = new Date(changeDate.getYear(), changeDate.getMonth(), changeDate.getDay());
  }
}
