package kata.supermarket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;
import kata.supermarket.discounts.DiscountScheme;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BasketTest {

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, String expectedTotal, Iterable<Item> items) {
        final Basket basket = new Basket();
        items.forEach(basket::add);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
            noItems(),
            aSingleItemPricedPerUnit(),
            multipleItemsPricedPerUnit(),
            multipleItemsPricedPerUnitWithDiscount(),
            multipleItemsPricedPerUnitWithDiscount2(),
            multipleItemsPricedPerUnitWithDiscount3(),
            multipleItemsPricedPerUnitWithDiscount4(),
            multipleItemsPricedPerUnitWithDiscount5(),
            multipleItemsPricedPerUnitWithDiscount6(),
            aSingleItemPricedByWeight(),
            aSingleItemPricedByWeight2(),
            multipleItemsPricedByWeight()
        );
    }

    private static Arguments aSingleItemPricedByWeight() {
        return Arguments.of("a single weighed item", "1.25", Collections.singleton(twoFiftyGramsOfAmericanSweets()));
    }

    private static Arguments aSingleItemPricedByWeight2() {
        return Arguments.of("a single weighed item 2", "1.20", Collections.singleton(twoKilosOfTomato()));
    }

    private static Arguments multipleItemsPricedByWeight() {
        return Arguments.of("multiple weighed items", "1.85",
            Arrays.asList(twoFiftyGramsOfAmericanSweets(), twoHundredGramsOfPickAndMix())
        );
    }

    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "2.04",
            Arrays.asList(aPackOfDigestives(), aPintOfMilk()));
    }

    private static Arguments multipleItemsPricedPerUnitWithDiscount() {
        return Arguments.of("multiple items priced per unit", "3.05",
            Arrays.asList(aPackOfDigestives(), aPackOfMilk()));
    }

    private static Arguments multipleItemsPricedPerUnitWithDiscount2() {
        return Arguments.of("multiple items priced per unit 2", "3.05",
            Arrays.asList(aPackOfDigestives(), aPackOfMilk(), aPackOfMilk()));
    }

    private static Arguments multipleItemsPricedPerUnitWithDiscount3() {
        return Arguments.of("multiple items priced per unit 3", "4.55",
            Arrays.asList(aPackOfDigestives(), aPackOfMilk(), aPackOfMilk(), aPackOfMilk()));
    }

    private static Arguments multipleItemsPricedPerUnitWithDiscount4() {
        return Arguments.of("multiple items priced per unit 4", "3.55",
            Arrays.asList(aPackOfDigestives(), aBread(), aBread()));
    }

    private static Arguments multipleItemsPricedPerUnitWithDiscount5() {
        return Arguments.of("multiple items priced per unit 5", "3.55",
            Arrays.asList(aPackOfDigestives(), aBread(), aBread(), aBread()));
    }

    private static Arguments multipleItemsPricedPerUnitWithDiscount6() {
        return Arguments.of("multiple items priced per unit 6", "4.55",
            Arrays.asList(aPackOfDigestives(), aBread(), aBread(), aBread(), aBread()));
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.49", Collections.singleton(aPintOfMilk()));
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        return new Product(1, new BigDecimal("0.49")).oneOf();
    }

    private static Item aPackOfMilk() {
        return new Product(3, new BigDecimal("1.5"), DiscountScheme.BUY_ONE_GET_ONE_FREE).oneOf();
    }

    private static Item aBread() {
        return new Product(4, new BigDecimal("1"), DiscountScheme.BUY_THREE_ITEMS_FOR_PRICE_OF_TWO).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new Product(2, new BigDecimal("1.55")).oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        return new WeighedProduct(new BigDecimal("4.99"));
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static WeighedProduct aKiloOfPickAndMix() {
        return new WeighedProduct(new BigDecimal("2.99"));
    }

    private static WeighedProduct aKiloOfTomato() {
        return new WeighedProduct(new BigDecimal("1.20"), DiscountScheme.BUY_ONE_KILO_FOR_HALF_PRICE);
    }

    private static Item twoHundredGramsOfPickAndMix() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".2"));
    }

    private static Item twoKilosOfTomato() {
        return aKiloOfTomato().weighing(new BigDecimal("2.0"));
    }
}