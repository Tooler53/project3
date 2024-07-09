package ru.yarilin.springcourse.Project3RestServer.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.yarilin.springcourse.Project3RestServer.dto.sensor.RegistrationDTO;
import ru.yarilin.springcourse.Project3RestServer.exceptions.SensorNotCreatedException;
import ru.yarilin.springcourse.Project3RestServer.models.Sensor;
import ru.yarilin.springcourse.Project3RestServer.services.SensorService;
import ru.yarilin.springcourse.Project3RestServer.util.SensorErrorResponse;
import ru.yarilin.springcourse.Project3RestServer.util.SensorValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensor")
public class SensorsController {

    private final ModelMapper modelMapper;
    private final SensorService sensorService;

    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(ModelMapper modelMapper, SensorService sensorService, SensorValidator sensorValidator) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid RegistrationDTO registrationDTO, BindingResult bindingResult) {
        Sensor sensor = convertToSensor(registrationDTO);
        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            String errorMsg = errors.stream()
                    .map(error -> error.getField() + " - " + error.getDefaultMessage())
                    .collect(Collectors.joining(";"));

            throw new SensorNotCreatedException(errorMsg);
        }
        sensorService.save(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(RegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException ex) {
        SensorErrorResponse response = new SensorErrorResponse(ex.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
