package kata.supermarket;

import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kata.supermarket.discounts.BuyOneGetOneFreeDiscount;
import kata.supermarket.discounts.BuyOneKiloForHalfPriceDiscount;
import kata.supermarket.discounts.BuyThreeItemsForPriceOfTwoDiscount;
import kata.supermarket.discounts.DiscountOperationByUnit;
import kata.supermarket.discounts.DiscountOperationByWeight;

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
         * TODO: This could be a good place to apply the results of the discount calculations. It is not likely to be
         * the best place to do those calculations. Think about how Basket could interact with something which provides
         * that functionality.
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

                if (item.isDiscountApplied()) {
                    continue;
                }

                if (item instanceof ItemByUnit) {
                    DiscountOperationByUnit discountOperationByUnit = null;

                    switch (item.discountScheme()) {
                        case BUY_ONE_GET_ONE_FREE:
                            discountOperationByUnit = new BuyOneGetOneFreeDiscount();
                            break;
                        case BUY_THREE_ITEMS_FOR_PRICE_OF_TWO:
                            discountOperationByUnit = new BuyThreeItemsForPriceOfTwoDiscount();
                            break;
                        default:
                            break;
                    }

                    if (nonNull(discountOperationByUnit)) {
                        long count = findCountAndMarkDiscountApplied((ItemByUnit) item);
                        BigDecimal discount = discountOperationByUnit.appylDiscount((ItemByUnit) item, count);
                        totalDiscount = totalDiscount.add(discount);
                    }

                } else if (item instanceof ItemByWeight) {

                    DiscountOperationByWeight discountOperationByWeight = null;

                    switch (item.discountScheme()) {
                        case BUY_ONE_KILO_FOR_HALF_PRICE:
                            discountOperationByWeight = new BuyOneKiloForHalfPriceDiscount();
                            break;
                        default:
                            break;
                    }

                    if (nonNull(discountOperationByWeight)) {
                        ItemByWeight itemByWeight = (ItemByWeight) item;
                        BigDecimal discount = discountOperationByWeight.appylDiscount(itemByWeight);
                        totalDiscount = totalDiscount.add(discount);
                        itemByWeight.setDiscountApplied(true);
                    }
                }
            }

            return totalDiscount;
        }

        private long findCountAndMarkDiscountApplied(ItemByUnit itemByUnit) {
            return items.stream()
                .filter(item -> item instanceof ItemByUnit)
                .filter(item -> {
                    if (((ItemByUnit) item).productId().equals(itemByUnit.productId())) {
                        item.setDiscountApplied(true);
                        return true;
                    }
                    return false;
                })
                .count();
        }

    }
}
