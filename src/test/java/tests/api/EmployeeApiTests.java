package tests.api;

import api.entities.EmployeeRequest;
import api.entities.EmployeeResponse;
import api.helpers.AuthHelper;
import api.helpers.EmployeeHelper;
import api.helpers.EmployeeHelperDB;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic("Employee Management API")
@Feature("CRUD Operations on Employee")
public class EmployeeApiTests {

    private static EmployeeHelper employeeHelper;
    private static EmployeeHelperDB employeeHelperDB;
    private static AuthHelper authHelper;

    @BeforeAll
    public static void setup() throws SQLException, IOException {
        employeeHelper = new EmployeeHelper();
        employeeHelperDB = new EmployeeHelperDB();
        authHelper = new AuthHelper();
    }

    @AfterEach
    public void cleanup() throws SQLException {
        employeeHelperDB.deleteTestEmployees();
    }

    @Test
    @Order(1)
    @Tag("critical")
    @Description("Создание сотрудника и проверка его в базе данных")
    @DisplayName("Создание сотрудника")
    public void createEmployeeTest() throws SQLException {
        EmployeeRequest req = new EmployeeRequest("Moscow", "Evgen", "HR", "Batuev");
        int id = employeeHelper.createEmployee(req);
        assertTrue(id > 0);

        EmployeeResponse empFromDb = employeeHelperDB.getEmployee(id);
        assertNotNull(empFromDb);
        assertEquals(req.getName(), empFromDb.getName());
        assertEquals(req.getSurname(), empFromDb.getSurname());
        assertEquals(req.getPosition(), empFromDb.getPosition());
        assertEquals(req.getCity(), empFromDb.getCity());
    }

    @Test
    @Order(2)
    @DisplayName("Получение сотрудника по ID")
    public void getEmployeeByIdTest() throws SQLException {
        EmployeeRequest req = new EmployeeRequest("Kazan", "Olga", "Finance", "Petrova");
        int id = employeeHelperDB.createEmployee(req);
        assertTrue(id > 0);

        EmployeeResponse resp = employeeHelper.getEmployeeById(id);
        assertNotNull(resp);
        assertEquals(req.getName(), resp.getName());
        assertEquals(req.getSurname(), resp.getSurname());
    }

    @Test
    @Order(3)
    @DisplayName("Обновление сотрудника")
    public void updateEmployeeTest() throws SQLException {
        EmployeeRequest createReq = new EmployeeRequest("Sochi", "Alex", "Marketing", "Volkov");
        int id = employeeHelperDB.createEmployee(createReq);
        assertTrue(id > 0);

        EmployeeRequest updateReq = new EmployeeRequest("Sochi", "Alex", "Sales", "Volkov");
        boolean updated = employeeHelper.updateEmployee(id, updateReq);
        assertTrue(updated);

        EmployeeResponse empUpdated = employeeHelperDB.getEmployee(id);
        assertEquals("Sales", empUpdated.getPosition());
    }

    @Test
    @Order(4)
    @DisplayName("Удаление сотрудника")
    public void deleteEmployeeTest() throws SQLException {
        EmployeeRequest req = new EmployeeRequest("Novosibirsk", "Irina", "IT", "Sidorova");
        int id = employeeHelperDB.createEmployee(req);
        assertTrue(id > 0);

        boolean deleted = employeeHelper.deleteEmployee(id);
        assertTrue(deleted);

        EmployeeResponse fromDb = employeeHelperDB.getEmployee(id);
        assertNull(fromDb);
    }

    @Test
    @Order(5)
    @DisplayName("Негативный тест получения несуществующего сотрудника")
    public void getInvalidEmployeeTest() {
        EmployeeResponse resp = employeeHelper.getEmployeeById(999999);
        assertNull(resp);
    }
}
