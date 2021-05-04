package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Basket {
    private final List<Item> items;

    public Basket() {
        this.items = new ArrayList<>();
    }

    public void add(final Item item) {
        this.items.add(item);
    }

    List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal total() {
        return new TotalCalculator().calculate();
    }

    private class TotalCalculator {
        private final List<Item> items;

        TotalCalculator() {
            this.items = items();
        }

        private BigDecimal subtotal() {
            return items.stream().map(Item::price)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        /**
         * TODO: This could be a good place to apply the results of
         *  the discount calculations.
         *  It is not likely to be the best place to do those calculations.
         *  Think about how Basket could interact with something
         *  which provides that functionality.
         */
        private BigDecimal discounts() {
            return new DiscountCalculator().calculate();
        }

        private BigDecimal calculate() {
            return subtotal().subtract(discounts());
        }
    }

    private class DiscountCalculator {
        private final List<Item> items;

        DiscountCalculator() {
            this.items = items();
        }

        private BigDecimal calculate() {

            BigDecimal totalDiscount = BigDecimal.ZERO;

            for (Item item : items) {

                DiscountScheme discountScheme = DiscountScheme.NONE;
                Boolean discountApplied = false;

                if (item instanceof ItemByUnit) {
                    discountScheme = ((ItemByUnit)item).discountScheme();
                    discountApplied =  ((ItemByUnit)item).getDiscountApplied();
                }
                else if(item instanceof ItemByWeight) {
                    discountScheme = ((ItemByWeight)item).discountScheme();
                    discountApplied =  ((ItemByWeight)item).getDiscountApplied();
                }

                if (discountApplied) {
                    continue;
                }

                switch (discountScheme) {
                    case NONE:
                        break;
                    case BUY_ONE_GET_ONE_FREE:
                        long count = items.stream()
                            .filter(item1 -> {
                                ItemByUnit itemByUnit = (ItemByUnit) item1;
                                if(itemByUnit.productId().equals(((ItemByUnit) item).productId())) {
                                    itemByUnit.setDiscountApplied(true);
                                    return true;
                                }
                                return false;
                            })
                            .count();
                        totalDiscount = totalDiscount.add(buyOneGetOneFree(item.price(), count));
                        break;
                    case BUY_ONE_KILO_FOR_HALF_PRICE:
                        ItemByWeight itemByWeight = (ItemByWeight) item;
                        itemByWeight.setDiscountApplied(true);
                        totalDiscount = totalDiscount.add(buyOneKiloForHalfPrice(item.price()));
                        break;
                }
            }

            return totalDiscount;
        }

        private BigDecimal buyOneGetOneFree(BigDecimal pricePerUnit, long count) {
            if (count == 0 || count == 1) return BigDecimal.ZERO;
            return pricePerUnit.multiply(new BigDecimal(count / 2));
        }

        private BigDecimal buyOneKiloForHalfPrice(BigDecimal price) {
            if (BigDecimal.ZERO.equals(price)) return BigDecimal.ZERO;
            return price.divide(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }
}
