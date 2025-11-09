package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import tests.ui.pageobjects.LoginPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static io.qameta.allure.Allure.step;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTests {

    @BeforeEach
    public void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(true)
        );

        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        Configuration.browserSize = "1280x1024";
        Configuration.browserPosition = "1600x0";
        Configuration.headless = true;
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

    @Test
    @Order(1)
    @DisplayName("Успешный вход под standard_user")
    @Story("Авторизация")
    @Description("Проверка успешного входа с валидными данными standard_user")
    @Tags({@Tag("позитивный"), @Tag("авторизация")})
    public void successfulLogin() {
        step("Открыть страницу логина и войти", () ->
                new LoginPage().openPage().login("standard_user", "secret_sauce"));
    }

    @Test
    @Order(2)
    @DisplayName("Вход заблокированного пользователя locked_out_user")
    @Story("Авторизация")
    @Description("Проверка ошибки при входе заблокированного пользователя locked_out_user")
    @Tags({@Tag("негативный"), @Tag("авторизация")})
    public void lockedOutUserLogin() {
        LoginPage loginPage = new LoginPage().openPage();
        step("Попытка входа заблокированным пользователем", () ->
                loginPage.login("locked_out_user", "secret_sauce"));

        step("Проверка сообщения об ошибке", () ->
                loginPage.checkError("Epic sadface: Sorry, this user has been locked out."));
    }
}
