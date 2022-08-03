package ru.novikov.natlex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.novikov.natlex.model.GeoClass;
import ru.novikov.natlex.service.implementation.GeoClassService;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/geoclasses")
public class GeoClassController {
    @Autowired
    private GeoClassService geoClassService;

    @GetMapping
    public List<GeoClass> getAllGeoClass(){
        return geoClassService.getAllGeoClasses();
    }

    @GetMapping("/{id}")
    public GeoClass getGeoClassById(@PathVariable long id) {
        return geoClassService.getGeoClassById(id);
    }

    @PostMapping
    public GeoClass addGeoClass(@RequestBody @Valid GeoClass geoClass) {
        return geoClassService.saveGeoClass(geoClass);
    }

    @PutMapping("/{id}")
    public void updateGeoClass(@PathVariable("id") long id, @RequestBody @Valid GeoClass geoClass) {
        geoClassService.updateGeoClass(id, geoClass);
    }

    @DeleteMapping("/{id}")
    public void deleteGeoClassById(@PathVariable long id) {
        geoClassService.deleteGeoClassById(id);
    }
}
