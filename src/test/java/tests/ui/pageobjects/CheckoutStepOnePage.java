package tests.ui.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;


public class CheckoutStepOnePage {
    private com.codeborne.selenide.SelenideElement firstNameInput = Selenide.$("#first-name");
    private com.codeborne.selenide.SelenideElement lastNameInput = Selenide.$("#last-name");
    private com.codeborne.selenide.SelenideElement postalCodeInput = Selenide.$("#postal-code");
    private com.codeborne.selenide.SelenideElement continueButton = Selenide.$("#continue");

    public CheckoutStepOnePage fillForm(String firstName, String lastName, String postalCode) {
        firstNameInput.shouldBe(Condition.visible).setValue(firstName);
        lastNameInput.shouldBe(Condition.visible).setValue(lastName);
        postalCodeInput.shouldBe(Condition.visible).setValue(postalCode);
        return this;
    }

    public CheckoutStepTwoPage clickContinue() {
        continueButton.click();
        return new CheckoutStepTwoPage();
    }
}