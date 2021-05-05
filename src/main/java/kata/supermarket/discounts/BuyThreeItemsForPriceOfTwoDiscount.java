package kata.supermarket.discounts;

import java.math.BigDecimal;
import kata.supermarket.ItemByUnit;

public class BuyThreeItemsForPriceOfTwoDiscount implements DiscountOperationByUnit {

    public BigDecimal appylDiscount(ItemByUnit item, long count) {
        if (count < 3) return BigDecimal.ZERO;
        return item.price().multiply(new BigDecimal(count / 3));
    }

}
