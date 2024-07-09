package ru.yarilin.springcourse.Project3RestServer.dto.sensor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegistrationDTO {
    @NotEmpty(message = "Name should be empty")
    @Size(min = 3, max = 30, message = "Name should not be between 2 and 30 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
