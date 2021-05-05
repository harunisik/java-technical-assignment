package kata.supermarket;

import java.math.BigDecimal;
import kata.supermarket.discounts.DiscountScheme;

public class WeighedProduct {

    private final BigDecimal pricePerKilo;
    private final DiscountScheme discountScheme;

    public WeighedProduct(final BigDecimal pricePerKilo) {
        this.pricePerKilo = pricePerKilo;
        this.discountScheme = DiscountScheme.NONE;
    }

    public WeighedProduct(final BigDecimal pricePerKilo, final DiscountScheme discountScheme) {
        this.pricePerKilo = pricePerKilo;
        this.discountScheme = discountScheme;
    }

    BigDecimal pricePerKilo() {
        return pricePerKilo;
    }

    public Item weighing(final BigDecimal kilos) {
        return new ItemByWeight(this, kilos);
    }

    DiscountScheme discountScheme() {
        return discountScheme;
    }
}
