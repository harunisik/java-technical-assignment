package kata.supermarket.discounts;

import java.math.BigDecimal;
import kata.supermarket.ItemByUnit;

public class BuyOneGetOneFreeDiscount implements DiscountOperationByUnit {

    public BigDecimal appylDiscount(ItemByUnit item, long count) {
        if (count < 2) return BigDecimal.ZERO;
        return item.price().multiply(new BigDecimal(count / 2));
    }

}
