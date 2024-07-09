package ru.yarilin.springcourse.Project3RestServer.dto.measurements;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import ru.yarilin.springcourse.Project3RestServer.models.Sensor;

public class AddRequestDTO {
    @NotNull
    @DecimalMin(value = "-100.0", message = "Показания должны быть больше -100")
    @DecimalMax(value = "100.0", message = "Показания должны быть меньше 100")
    private Double value;

    @NotNull(message = "Raining должен быть не пустым")
    private boolean raining;

    @NotNull(message = "Sensor должно быть заполнено")
    private Sensor sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
