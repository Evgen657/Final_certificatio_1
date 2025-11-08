package api.helpers;

import api.entities.User;
import api.helpers.config.EnvHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class AuthHelper {

    private final EnvHelper envHelper = EnvHelper.getInstance();

    public AuthHelper() {
        RestAssured.baseURI = envHelper.getBaseUri();
    }

    public String getToken(String username, String password) {
        return given()
                .contentType(ContentType.JSON)
                .body(new User(username, password))
                .when()
                .post("/login")
                .then()
                .statusCode(200)  // здесь можно отловить или изменить по требованиям
                .extract()
                .jsonPath()
                .getString("token");
    }

    public String getAdminToken() {
        return getToken(envHelper.getAdminLogin(), envHelper.getAdminPassword());
    }
}
