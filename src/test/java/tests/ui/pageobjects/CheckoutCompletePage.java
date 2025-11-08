package tests.ui.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

public class CheckoutCompletePage {
    private com.codeborne.selenide.SelenideElement completeHeader = Selenide.$(".complete-header");

    public void checkCompletionMessage(String message) {
        completeHeader.shouldBe(Condition.visible).shouldHave(Condition.text(message));
    }
}