package tests.ui.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;


public class LoginPage {

    private com.codeborne.selenide.SelenideElement usernameInput = Selenide.$("#user-name");
    private com.codeborne.selenide.SelenideElement passwordInput = Selenide.$("#password");
    private com.codeborne.selenide.SelenideElement loginButton = Selenide.$("#login-button");
    private com.codeborne.selenide.SelenideElement errorMessage = Selenide.$("h3[data-test='error']");

    public LoginPage openPage() {
        Selenide.open("https://www.saucedemo.com/");
        return this;
    }

    public ProductsPage login(String username, String password) {
        usernameInput.shouldBe(Condition.visible).setValue(username);
        passwordInput.shouldBe(Condition.visible).setValue(password);
        loginButton.click();
        return new ProductsPage();
    }

    public void checkError(String expectedText) {
        errorMessage.shouldBe(Condition.visible).shouldHave(Condition.text(expectedText));
    }
}
