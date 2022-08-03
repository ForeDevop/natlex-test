package ru.novikov.natlex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.novikov.natlex.model.GeoClass;


@Repository
public interface GeoClassRepository extends JpaRepository<GeoClass, Long> {
}
