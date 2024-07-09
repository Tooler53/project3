package ru.yarilin.springcourse.Project3RestServer.services;

import org.springframework.stereotype.Service;
import ru.yarilin.springcourse.Project3RestServer.exceptions.SensorNotFoundException;
import ru.yarilin.springcourse.Project3RestServer.models.Measurement;
import ru.yarilin.springcourse.Project3RestServer.repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public void create(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll().stream()/*.limit(50)*/.toList();
    }

    public int countRainingMeasurements() {
        return measurementRepository.countAllByRaining(true);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).orElseThrow(SensorNotFoundException::new));
    }
}
