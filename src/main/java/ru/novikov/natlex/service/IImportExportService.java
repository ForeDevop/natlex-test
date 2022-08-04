package ru.novikov.natlex.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IImportExportService {
    long importFile(MultipartFile multipartFile);
    long exportFile() throws Exception;
    String getImportFileStatusById(long id);
    String getExportFileStatusById(long id);
    Resource getExportedFileById(long id);
}
