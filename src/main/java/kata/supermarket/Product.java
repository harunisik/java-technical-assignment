package kata.supermarket;

import java.math.BigDecimal;

public class Product {

    private final Integer productId;
    private final BigDecimal pricePerUnit;
    private final DiscountScheme discountScheme;

    public Product(final Integer productId, final BigDecimal pricePerUnit) {
        this.productId = productId;
        this.pricePerUnit = pricePerUnit;
        this.discountScheme = DiscountScheme.NONE;
    }

    Product(final Integer productId, final BigDecimal pricePerUnit, final DiscountScheme discountScheme) {
        this.productId = productId;
        this.pricePerUnit = pricePerUnit;
        this.discountScheme = discountScheme;
    }

    BigDecimal pricePerUnit() {
        return pricePerUnit;
    }

    DiscountScheme discountScheme() {
        return discountScheme;
    }

    public Item oneOf() {
        return new ItemByUnit(this);
    }

    public Integer getProductId() {
        return productId;
    }
}
