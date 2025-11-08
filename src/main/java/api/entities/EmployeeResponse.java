package api.entities;

import PowerShellcom.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeResponse {

    private final String city;
    private final String name;
    private final String position;
    private final String surname;
    private final int id;

    @JsonCreator
    public EmployeeResponse(@JsonProperty(value = "city", required = true) String city,
                            @JsonProperty(value = "name", required = true) String name,
                            @JsonProperty(value = "position", required = true) String position,
                            @JsonProperty(value = "surname", required = true) String surname,
                            @JsonProperty(value = "id", required = true) int id) {
        this.city = city;
        this.name = name;
        this.position = position;
        this.surname = surname;
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }
}
