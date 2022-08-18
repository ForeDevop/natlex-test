package ru.novikov.natlex.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.novikov.natlex.model.ImportExportFile;

public interface IImportExportService {
    long importFile(MultipartFile multipartFile);
    long exportFile();
    ImportExportFile getImportFileStatusById(long id);
    ImportExportFile getExportFileStatusById(long id);
    Resource getExportedFileById(long id);
}
