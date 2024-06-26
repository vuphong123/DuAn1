package udpm.fpt.Utitlity;public class DiscountCalculator {
    public Integer calculateDiscountedPrice(int originalPrice, double discountPercentage) {
        double discountPercentageDecimal = discountPercentage / 100.0;
        double discountedPrice = originalPrice - (originalPrice * discountPercentageDecimal);
        return (int) discountedPrice;
    }
}
