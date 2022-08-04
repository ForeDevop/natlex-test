package ru.novikov.natlex.service.implementation;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.novikov.natlex.enumeration.JobStatus;
import ru.novikov.natlex.model.GeoClass;
import ru.novikov.natlex.model.ImportExportFile;
import ru.novikov.natlex.model.Section;
import ru.novikov.natlex.repository.ImportExportRepository;
import ru.novikov.natlex.repository.SectionRepository;
import ru.novikov.natlex.service.IParseService;
import org.apache.poi.ss.usermodel.Row;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ParseService implements IParseService {
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private ImportExportRepository importExportRepository;

    @Async
    @Override
    public void parseFileToDB(InputStream is, long fileId) {
        ImportExportFile importFile = new ImportExportFile();
        importFile.setStatus(JobStatus.IN_PROGRESS.name());
        importFile.setId(fileId);

        importFile = importExportRepository.save(importFile);

        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rows = sheet.iterator();
            List<Section> sections = new ArrayList<>();

            boolean skipHeader = true;
            while (rows.hasNext()) {
                Row row = rows.next();

                if (skipHeader) {
                    skipHeader = false;
                    row = rows.next();
                }

                Iterator<Cell> cellsInRow = row.iterator();

                Section section = new Section();
                List<GeoClass> geoClasses = new ArrayList<>();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    if (currentCell.getStringCellValue().isEmpty()) {
                        continue;
                    }

                    // Cell with Section name
                    if (cellIdx == 0) {
                        section.setName(currentCell.getStringCellValue());
                        cellIdx++;
                        continue;
                    }

                    // Cell with GeoClass name
                    if (cellIdx % 2 == 1) {
                        GeoClass geoClass = new GeoClass();
                        geoClass.setName(currentCell.getStringCellValue());
                        geoClass.setSection(section);
                        geoClasses.add(geoClass);
                    }

                    // Cell with geoClass code
                    if (cellIdx % 2 == 0) {
                        geoClasses.get(geoClasses.size() - 1)
                                .setCode(currentCell.getStringCellValue());
                    }

                    cellIdx++;
                }

                section.setGeologicalClasses(geoClasses);
                sections.add(section);
            }

            sectionRepository.saveAll(sections);
            importFile.setStatus(JobStatus.DONE.name());
            importExportRepository.save(importFile);

        } catch (IOException e) {
            importFile.setStatus(JobStatus.ERROR.name());
            throw new RuntimeException(e);
        }
    }

    @Async
    @Override
    public void parseDBToFile(List<Section> sections, long fileId) {
        String path = "src/main/resources/files/" + fileId;

        ImportExportFile exportFile = new ImportExportFile();
        exportFile.setStatus(JobStatus.IN_PROGRESS.name());
        exportFile.setId(fileId);

        exportFile = importExportRepository.save(exportFile);

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(path + ".xlsx")) {

            Sheet sheet = workbook.createSheet("Sections");
            sheet.setDefaultColumnWidth(30);

            // Create header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Section name");

            int maxGeoClassesCount = Section.getMaxGeoClassesCount(sections);
            int classNumber = 1;
            for (int i = 1; i < maxGeoClassesCount * 2; i += 2) {
                header.createCell(i).setCellValue("Class " + classNumber + " name");
                header.createCell(i + 1).setCellValue("Class " + classNumber + " code");
                classNumber++;
            }

            // Create section content
            int rowIdx = 1;
            for (Section section : sections) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(section.getName());

                int cellIdx = 1;
                for (GeoClass geoClass : section.getGeologicalClasses()) {
                    row.createCell(cellIdx++).setCellValue(geoClass.getName());
                    row.createCell(cellIdx++).setCellValue(geoClass.getCode());
                }
            }

            workbook.write(fos);

            exportFile.setStatus(JobStatus.DONE.name());
            importExportRepository.save(exportFile);

        } catch (IOException e) {
            exportFile.setStatus(JobStatus.ERROR.name());
            throw new RuntimeException("Fail to export data to Excel file!");
        }
    }
}
