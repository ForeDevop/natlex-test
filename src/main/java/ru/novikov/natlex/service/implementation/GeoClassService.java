package ru.novikov.natlex.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.novikov.natlex.exception.GeoClassNotFoundException;
import ru.novikov.natlex.model.GeoClass;
import ru.novikov.natlex.repository.GeoClassRepository;
import ru.novikov.natlex.service.IGeoClassService;
import java.util.List;

@Service
public class GeoClassService implements IGeoClassService {
    @Autowired
    private GeoClassRepository geoClassRepository;

    @Override
    public List<GeoClass> getAllGeoClasses() {
        return geoClassRepository.findAll();
    }

    @Override
    public GeoClass saveGeoClass(GeoClass geoClass) {
        geoClassRepository.save(geoClass);
        return geoClass;
    }

    @Override
    public GeoClass getGeoClassById(long id) {
        return geoClassRepository.findById(id)
                .orElseThrow(() -> new GeoClassNotFoundException(id));
    }

    @Override
    public void deleteGeoClassById(long id) {
        GeoClass geoClass = geoClassRepository.findById(id)
                        .orElseThrow(() -> new GeoClassNotFoundException(id));

        geoClassRepository.delete(geoClass);
    }

    @Override
    public void updateGeoClass(long id, GeoClass newGeoClass) throws GeoClassNotFoundException{
        GeoClass oldGeoClass = geoClassRepository.findById(id)
                .orElseThrow(() -> new GeoClassNotFoundException(id));

        oldGeoClass.setName(newGeoClass.getName());
        oldGeoClass.setCode(newGeoClass.getCode());
        geoClassRepository.save(oldGeoClass);
    }
}
