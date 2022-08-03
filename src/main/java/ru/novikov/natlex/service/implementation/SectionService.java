package ru.novikov.natlex.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.novikov.natlex.exception.SectionNotFoundException;
import ru.novikov.natlex.repository.SectionRepository;
import ru.novikov.natlex.model.Section;
import ru.novikov.natlex.service.ISectionService;
import java.util.List;

@Service
public class SectionService implements ISectionService {
    @Autowired
    private SectionRepository sectionRepository;

    @Override
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    @Override
    public Section saveSection(Section section) {
        sectionRepository.save(section);
        return section;
    }

    @Override
    public Section getSectionById(long id) throws SectionNotFoundException{
        return sectionRepository.findById(id)
                .orElseThrow(() -> new SectionNotFoundException(id));
    }

    @Override
    public void deleteSectionById(long id) throws SectionNotFoundException {
        Section section = sectionRepository.findById(id)
                        .orElseThrow(() -> new SectionNotFoundException(id));

        sectionRepository.delete(section);
    }

    @Override
    public void updateSection(long id, Section newSection) throws SectionNotFoundException{
        Section oldSection = sectionRepository.findById(id)
                .orElseThrow(() -> new SectionNotFoundException(id));

        oldSection.setName(newSection.getName());
        sectionRepository.save(oldSection);
    }

    @Override
    public List<Section> getSectionsByGeoClassCode(String code) {
        return sectionRepository.findByGeoClassCode(code);
    }
}
