package kata.supermarket.discounts;

import java.math.BigDecimal;
import kata.supermarket.ItemByWeight;

public class BuyOneKiloForHalfPriceDiscount implements DiscountOperationByWeight {

    public BigDecimal appylDiscount(ItemByWeight item) {
        BigDecimal price = item.price();
        if (BigDecimal.ZERO.equals(price)) {
            return BigDecimal.ZERO;
        }
        return price.divide(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
