package kata.supermarket.discounts;

import java.math.BigDecimal;
import kata.supermarket.ItemByWeight;

public interface DiscountOperationByWeight {

    BigDecimal appylDiscount(ItemByWeight item);
}
