package kata.supermarket.discounts;

import java.math.BigDecimal;
import kata.supermarket.ItemByUnit;

public interface DiscountOperationByUnit {

    BigDecimal appylDiscount(ItemByUnit item, long count);
}
