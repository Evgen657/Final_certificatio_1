package api.helpers;

import api.entities.EmployeeRequest;
import api.entities.EmployeeResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import api.helpers.config.EnvHelper;

import static io.restassured.RestAssured.given;

public class EmployeeHelper {

    private final AuthHelper authHelper;
    private final EnvHelper envHelper = EnvHelper.getInstance();

    public EmployeeHelper() {
        RestAssured.baseURI = envHelper.getBaseUri();
        authHelper = new AuthHelper();
    }

    public EmployeeResponse getEmployeeById(int id) {
        Response response = given()
                .get("/employee/" + id)
                .andReturn();

        if (response.statusCode() == 200) {
            return response.as(EmployeeResponse.class);
        }
        return null;
    }

    public boolean updateEmployee(int id, EmployeeRequest request) {
        String token = authHelper.getAdminToken();

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .put("/employee/" + id)
                .andReturn();

        return response.statusCode() == 200;
    }

    public int createEmployee(EmployeeRequest employee) {
        String token = authHelper.getAdminToken();

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(employee)
                .post("/employee")
                .andReturn();

        if (response.statusCode() == 201) {
            return response.jsonPath().getInt("id");
        } else {
            System.err.println("Failed to create employee: Status=" + response.statusCode() +
                    ", Body=" + response.getBody().asString());
            return -1;
        }
    }

    public boolean deleteEmployee(int id) {
        String token = authHelper.getAdminToken();

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .delete("/employee/" + id)
                .andReturn();

        return response.statusCode() == 200;
    }
}
