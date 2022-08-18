package ru.novikov.natlex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.novikov.natlex.model.ImportExportFile;
import ru.novikov.natlex.service.IImportExportService;

@RestController
@RequestMapping("")
public class ImportExportController {
    @Autowired
    private IImportExportService importExportService;

    @PostMapping(value = "import")
    public long importFile(@RequestParam("file") MultipartFile multipartFile) {
        return importExportService.importFile(multipartFile);
    }

    @GetMapping(value = "export")
    public long exportFile() {
        return importExportService.exportFile();
    }

    @GetMapping(value = "import/{id}", produces = "application/json")
    public ImportExportFile getImportStatusById(@PathVariable long id) {
        return importExportService.getImportFileStatusById(id);
    }

    @GetMapping("export/{id}")
    public ImportExportFile getExportStatusById(@PathVariable long id) {
        return importExportService.getExportFileStatusById(id);
    }

    @GetMapping(value = "export/{id}/file", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Resource getExportedFile(@PathVariable long id) {
        return importExportService.getExportedFileById(id);
    }
}
