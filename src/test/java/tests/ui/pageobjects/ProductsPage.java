package tests.ui.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

public class ProductsPage {

    private ElementsCollection products = Selenide.$$(".inventory_item");
    private static final String CART_BADGE_CSS = ".shopping_cart_badge";
    private SelenideElement cartLink = Selenide.$(".shopping_cart_link");

    /**
     * Добавляет товар в корзину по его наименованию.
     * После клика ждёт смены текста кнопки с "Add to cart" на "Remove".
     */
    public ProductsPage addProductToCart(String productName) {
        SelenideElement product = products.findBy(Condition.text(productName));
        if (product != null) {
            SelenideElement button = product.$("button");
            button.shouldHave(Condition.text("Add to cart")).click();
            // Явное ожидание смены текста кнопки на "Remove" до 5 секунд
            button.shouldHave(Condition.text("Remove"), Duration.ofSeconds(5));
        }
        return this;
    }

    /**
     * Возвращает элемент значка корзины (счётчика товаров),
     * либо null если значок отсутствует (корзина пуста).
     */
    public SelenideElement getCartBadge() {
        SelenideElement badge = Selenide.$(CART_BADGE_CSS);
        return badge.exists() ? badge : null;
    }

    /**
     * Возвращает количество товаров в корзине, основываясь на тексте значка.
     * Если значок отсутствует или содержит некорректное значение — возвращает 0.
     */
    public int getCartItemsCount() {
        SelenideElement badge = getCartBadge();
        if (badge != null && badge.is(Condition.visible)) {
            try {
                return Integer.parseInt(badge.getText());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Переходит в корзину, кликая по иконке корзины.
     * Возвращает страницу корзины.
     */
    public CartPage goToCart() {
        cartLink.click();
        return new CartPage();
    }

    /**
     * Явно ждёт, что значок корзины появится и отобразит заданное количество товаров.
     */
    public void waitCartBadgeCount(int expectedCount) {
        SelenideElement badge = Selenide.$(CART_BADGE_CSS);
        badge.shouldBe(Condition.visible);
        badge.shouldHave(Condition.text(String.valueOf(expectedCount)));
    }
}

