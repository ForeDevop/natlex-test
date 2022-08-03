package ru.novikov.natlex.service;

import ru.novikov.natlex.model.GeoClass;
import ru.novikov.natlex.model.Section;

import java.util.List;

public interface IGeoClassService {
     List<GeoClass> getAllGeoClasses();
     GeoClass getGeoClassById(long id);
     GeoClass saveGeoClass(GeoClass geoClass);
     void updateGeoClass(long id, GeoClass newGeoClass);
     void deleteGeoClassById(long id);
}
