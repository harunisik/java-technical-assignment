package kata.supermarket;

import java.math.BigDecimal;
import kata.supermarket.discounts.DiscountScheme;

public class ItemByUnit extends Item {

    private final Product product;

    ItemByUnit(final Product product) {
        this.product = product;
    }

    public BigDecimal price() {
        return product.pricePerUnit();
    }

    @Override
    public DiscountScheme discountScheme() {
        return product.discountScheme();
    }

    public Integer productId() {
        return product.getProductId();
    }

}
