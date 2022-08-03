package ru.novikov.natlex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.novikov.natlex.model.Section;
import ru.novikov.natlex.service.implementation.SectionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {
    @Autowired
    private SectionService sectionService;

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
    public List<Section> getSectionsByGeoClassCode(@RequestParam("code") String code) {
        return sectionService.getSectionsByGeoClassCode(code);
    }
}
