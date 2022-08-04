package ru.novikov.natlex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.novikov.natlex.service.implementation.ImportExportService;

@RestController
@RequestMapping("api")
public class ImportExportController {
    @Autowired
    private ImportExportService importExportService;

    @PostMapping(value = "import")
    public long importFile(@RequestParam("file") MultipartFile multipartFile) {
        return importExportService.importFile(multipartFile);
    }

    @GetMapping(value = "export")
    public long exportFile() {
        return importExportService.exportFile();
    }

    @GetMapping("import/{id}")
    public String getImportStatusById(@PathVariable long id) {
        return importExportService.getImportFileStatusById(id);
    }

    @GetMapping("export/{id}")
    public String getExportStatusById(@PathVariable long id) {
        return importExportService.getExportFileStatusById(id);
    }

    @GetMapping(value = "export/{id}/file", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Resource getExportedFile(@PathVariable long id) {
        return importExportService.getExportedFileById(id);
    }
}
