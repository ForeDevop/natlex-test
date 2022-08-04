package ru.novikov.natlex.service;

import ru.novikov.natlex.model.Section;
import java.io.InputStream;
import java.util.List;

public interface IParseService {
    void parseFileToDB(InputStream is, long fileId);
    void parseDBToFile(List<Section> sections, long fileId);
}
