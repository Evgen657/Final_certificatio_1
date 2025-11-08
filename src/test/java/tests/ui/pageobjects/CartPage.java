package tests.ui.pageobjects;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;


public class CartPage {
    private ElementsCollection cartItems = Selenide.$$(".cart_item");
    private com.codeborne.selenide.SelenideElement checkoutButton = Selenide.$("#checkout");

    public int getCartItemsCount() {
        return cartItems.size();
    }

    public CartPage waitCartItemsCount(int expectedCount) {
        cartItems.shouldHave(CollectionCondition.size(expectedCount));
        return this;
    }

    public CheckoutStepOnePage clickCheckout() {
        checkoutButton.click();
        return new CheckoutStepOnePage();
    }
}
