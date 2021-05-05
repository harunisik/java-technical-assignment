package kata.supermarket;

import java.math.BigDecimal;
import kata.supermarket.discounts.DiscountScheme;

public abstract class Item {

    private Boolean discountApplied = Boolean.FALSE;

    abstract BigDecimal price();

    DiscountScheme discountScheme() {
        return DiscountScheme.NONE;
    }

    public Boolean isDiscountApplied() {
        return discountApplied;
    }

    public void setDiscountApplied(Boolean discountApplied) {
        this.discountApplied = discountApplied;
    }
}
