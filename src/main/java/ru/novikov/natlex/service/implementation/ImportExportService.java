package ru.novikov.natlex.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.novikov.natlex.enumeration.JobStatus;
import ru.novikov.natlex.exception.FileNotFoundException;
import ru.novikov.natlex.model.ImportExportFile;
import ru.novikov.natlex.repository.ImportExportRepository;
import ru.novikov.natlex.service.IImportExportService;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ImportExportService implements IImportExportService {
    @Autowired
    private ImportExportRepository importExportRepository;
    @Autowired
    private ParseService parseService;
    @Autowired
    private SectionService sectionService;

    AtomicLong counter = new AtomicLong();

    @Override
    public long importFile(MultipartFile multipartFile) {
        long fileId = counter.incrementAndGet();

        try {
            parseService.parseFileToDB(multipartFile.getInputStream(), fileId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileId;
    }

    @Override
    public long exportFile() {
        long fileId = counter.incrementAndGet();
        parseService.parseDBToFile(sectionService.getAllSections(), fileId);

        return fileId;
    }

    @Override
    public String getImportFileStatusById(long id) {
        ImportExportFile importFile = importExportRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id));

        return String.valueOf(importFile.getStatus());
    }

    @Override
    public String getExportFileStatusById(long id) {
        ImportExportFile exportFile = importExportRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id));

        return exportFile.getStatus();
    }

    @Override
    public Resource getExportedFileById(long id) {
        ImportExportFile exportedFile = importExportRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id));

        String pathToFile = "src/main/resources/files/" + id + ".xlsx";
        if(exportedFile.getStatus().equals(JobStatus.DONE.name())) {
            return new FileSystemResource(pathToFile);
        } else {
            throw new RuntimeException("File is being processing!");
        }
    }
}
