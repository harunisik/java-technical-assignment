package kata.supermarket;

import java.math.BigDecimal;
import kata.supermarket.discounts.DiscountScheme;

public class ItemByWeight extends Item {

    private final WeighedProduct product;
    private final BigDecimal weightInKilos;

    ItemByWeight(final WeighedProduct product, final BigDecimal weightInKilos) {
        this.product = product;
        this.weightInKilos = weightInKilos;
    }

    public BigDecimal price() {
        return product.pricePerKilo().multiply(weightInKilos).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public DiscountScheme discountScheme() {
        return product.discountScheme();
    }

}
