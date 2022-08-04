package ru.novikov.natlex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.novikov.natlex.model.ImportExportFile;

public interface ImportExportRepository extends JpaRepository<ImportExportFile, Long> {
}
