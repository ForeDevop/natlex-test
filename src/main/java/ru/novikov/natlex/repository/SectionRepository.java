package ru.novikov.natlex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.novikov.natlex.model.Section;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    @Query(value = "SELECT * FROM sections s " +
            "JOIN geoclasses gc ON s.id = gc.section_id " +
            "WHERE gc.code = :code", nativeQuery = true)
    List<Section> findByGeoClassCode(@Param(value = "code") String code);
}
