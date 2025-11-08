package api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeRequest {

    @JsonProperty("city")
    private String city;

    @JsonProperty("name")
    private String name;

    @JsonProperty("position")
    private String position;

    @JsonProperty("surname")
    private String surname;

    public EmployeeRequest(String city, String name, String position, String surname) {
        this.city = city;
        this.name = name;
        this.position = position;
        this.surname = surname;
    }

    public EmployeeRequest() {
        // Конструктор по умолчанию для Jackson
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
