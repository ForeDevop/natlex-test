package ru.novikov.natlex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.novikov.natlex.model.Section;
import ru.novikov.natlex.service.ISectionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sections")
public class SectionController {
    @Autowired
    private ISectionService sectionService;

    @GetMapping
    public List<Section> getAllSections(){
        return sectionService.getAllSections();
    }

    @GetMapping("/{id}")
    public Section getSectionById(@PathVariable long id) {
        return sectionService.getSectionById(id);
    }

    @PostMapping
    public Section addSection(@RequestBody @Valid Section section) {
        return sectionService.saveSection(section);
    }

    @PutMapping("/{id}")
    public void updateSection(@PathVariable("id") long id, @RequestBody @Valid Section section) {
        sectionService.updateSection(id, section);
    }

    @DeleteMapping("/{id}")
    public void deleteSectionById(@PathVariable long id) {
        sectionService.deleteSectionById(id);
    }

    @GetMapping("/by-code")
    public ResponseEntity<List<Section>> getSectionsByGeoClassCode(@RequestParam("code") String code) {
        List<Section> sections = sectionService.getSectionsByGeoClassCode(code);

        return sections.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(sections);
    }
}
