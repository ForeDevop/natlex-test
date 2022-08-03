package ru.novikov.natlex.service;

import ru.novikov.natlex.model.Section;

import java.util.List;

public interface ISectionService {
     List<Section> getAllSections();
     Section getSectionById(long id);
     Section saveSection(Section section);
     void updateSection(long id, Section newSection);
     void deleteSectionById(long id);
     List<Section> getSectionsByGeoClassCode(String code);
}
