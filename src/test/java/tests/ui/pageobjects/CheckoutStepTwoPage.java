package tests.ui.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;


public class CheckoutStepTwoPage {
    private com.codeborne.selenide.SelenideElement finishButton = Selenide.$("#finish");
    private com.codeborne.selenide.SelenideElement totalLabel = Selenide.$(".summary_total_label");

    public String getTotal() {
        return totalLabel.shouldBe(Condition.visible).getText(); // пример: "Total: $58.29"
    }

    public CheckoutCompletePage clickFinish() {
        finishButton.shouldBe(Condition.visible).click();
        return new CheckoutCompletePage();
    }
}
